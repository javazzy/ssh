package my.ssh.biz.ssh.nlp.controller;

import my.ssh.biz.common.controller.SimpleController;
import my.ssh.biz.common.service.BaseService;
import my.ssh.util.JcsegUtils;
import my.ssh.util.WebUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.*;

/**
 * 控制器：自然语言分析
 */
@Controller
@RequestMapping("/word2Vecs")
public class Word2vecController extends SimpleController{

    private static Logger log = LoggerFactory.getLogger(Word2vecController.class);
    private static Word2Vec word2Vec = null;

    private String file() throws Exception {
        String filePath = WebUtils.getServletContext().getRealPath("") + "/upload/word2vec/"+ DateFormatUtils.format(Calendar.getInstance(),"yyyyMMddHHmmssS")+".txt";
        File dataFile = new File(filePath);
        File parentFile = dataFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        if (WebUtils.getRequest() instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest request = (MultipartHttpServletRequest) WebUtils.getRequest();
            Iterator<String> fileNames = request.getFileNames();
            while(fileNames.hasNext()) {
                List<MultipartFile> files = request.getFiles(fileNames.next());
                for (MultipartFile file: files) {
                    List<String> lines = new ArrayList<>();
                    BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(),codeString(file.getInputStream())));
                    while(br.ready()){
                        List<String> words = JcsegUtils.getAllWords(br.readLine());
                        lines.add(StringUtils.join(words," "));
                    }
                    br.close();
                    FileUtils.writeLines(dataFile,lines,true);
                }
            }
        }

        return filePath;
    }

    /**
     * 训练
     * @return
     */
    @RequestMapping("/fit")
    @ResponseBody
    public Object fit() throws Exception {
        log.info("Load & Vectorize Sentences....");
        // Strip white space before and after for each line
        SentenceIterator iterator = new BasicLineIterator(file());
        try {
            // Split on white spaces in the line to get words
            TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
            tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

            String modelPath = WebUtils.getServletContext().getRealPath("") + "/model/word2vec.txt";
            File modelFile = new File(modelPath);
            if (!modelFile.exists()) {

                log.info("Building model....");
                // manual creation of VocabCache and WeightLookupTable usually isn't necessary
                // but in this case we'll need them
                InMemoryLookupCache cache = new InMemoryLookupCache();
                WeightLookupTable<VocabWord> table = new InMemoryLookupTable.Builder<VocabWord>()
                        .vectorLength(100)
                        .useAdaGrad(false)
                        .cache(cache)
                        .lr(0.025f).build();

                log.info("Building model....");
                word2Vec = new Word2Vec.Builder()
                        .minWordFrequency(5)
                        .iterations(1)
                        .epochs(1)
                        .layerSize(100)
                        .seed(42)
                        .windowSize(5)
                        .iterate(iterator)
                        .tokenizerFactory(tokenizerFactory)
                        .lookupTable(table)
                        .vocabCache(cache)
                        .build();
            } else {
                word2Vec = WordVectorSerializer.loadFullModel(modelPath);
                word2Vec.setTokenizerFactory(tokenizerFactory);
                word2Vec.setSentenceIter(iterator);
            }

            log.info("开始训练模型....");
            word2Vec.fit();
            log.info("训练完成！");
            return success("训练完成！");
        } catch (Exception e){
            log.error(e.getMessage(),e);
            return error(e.getMessage());
        } finally {
            iterator.finish();
        }
    }

    /**
     * 保存训练模型
     * @return
     */
    @RequestMapping("/saveModel")
    public void saveModel() throws IOException {
        String modelPath = WebUtils.getServletContext().getRealPath("")+"/model/word2vec.txt";

        File modelParentFile = new File(modelPath).getParentFile();
        if(!modelParentFile.exists()){
            modelParentFile.mkdirs();
        }

        log.info("保存训练模型");
        WordVectorSerializer.writeFullModel(word2Vec, modelPath);
    }

    /**
     * 获取相关度最近的词
     * @return
     */
    @RequestMapping("/nearest")
    @ResponseBody
    public Object nearest(String word,Integer length) throws IOException {
        log.info("Closest Words:");
        Collection<String> lst = word2Vec.wordsNearest(word, length);
        return lst;
    }

    /**
     * 获取词向量文件
     * @return
     */
    @RequestMapping("/vectors")
    @ResponseBody
    public Object vectors() throws IOException {
        if(null != word2Vec) {
            WebUtils.getResponse().setContentType("text/plain");
            WebUtils.getResponse().setHeader("Content-Disposition", "attachment; filename=vectors.txt");

            log.info("Writing word vectors to text file....");
            // Write word vectors
            WordVectorSerializer.writeWordVectors(word2Vec, WebUtils.getResponse().getOutputStream());
            return "success";
        }else{
            return error("没有词向量文件");
        }
    }

    @Override
    public BaseService getService() {
        return null;
    }

    /**
     * 判断文件的编码格式
     * @param bin
     * @return 文件编码格式
     * @throws Exception
     */
    public static String codeString(InputStream bin) throws Exception{
        int p = (bin.read() << 8) + bin.read();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }
}
