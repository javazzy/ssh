package my.ssh.biz.ssh.nlp.controller;

import my.ssh.biz.common.controller.SimpleController;
import my.ssh.biz.common.service.BaseService;
import my.ssh.util.JcsegUtils;
import my.ssh.util.WebUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.berkeley.Pair;
import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.plot.BarnesHutTsne;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
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
@RequestMapping("/tsnes")
public class TsneController extends SimpleController{

    private static Logger log = LoggerFactory.getLogger(TsneController.class);
    private static BarnesHutTsne tsne = null;

    private File uploadfile() throws Exception {
        String filePath = WebUtils.getServletContext().getRealPath("") + "upload\\tsne\\"+ DateFormatUtils.format(Calendar.getInstance(),"yyyyMMddHHmmssS")+".txt";
        File dataFile = new File(filePath);
        File parentFile = dataFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        if (WebUtils.getRequest() instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest request = (MultipartHttpServletRequest) WebUtils.getRequest();
            Map<String, MultipartFile> fileMap = request.getFileMap();

            for (MultipartFile file : fileMap.values()) {
                FileUtils.copyInputStreamToFile(file.getInputStream(),dataFile);
            }
        }

        return dataFile;
    }

    /**
     * 训练
     * @return
     */
    @RequestMapping("/fit")
    @ResponseBody
    public Object fit() throws Exception {
        int iterations = 100;
        Nd4j.dtype = DataBuffer.Type.DOUBLE;
        Nd4j.factory().setDType(DataBuffer.Type.DOUBLE);
        List<String> cacheList = new ArrayList<>();

        log.info("Load & Vectorize data....");
        Pair<InMemoryLookupTable,VocabCache> vectors = WordVectorSerializer.loadTxt(uploadfile());
        VocabCache cache = vectors.getSecond();
        INDArray weights = vectors.getFirst().getSyn0();

        for(int i = 0; i < cache.numWords(); i++)
            cacheList.add(cache.wordAtIndex(i));

        log.info("Build model....");
        tsne = new BarnesHutTsne.Builder()
                .setMaxIter(iterations).theta(0.5)
                .normalize(false)
                .learningRate(500)
                .useAdaGrad(false)
                .usePca(false)
                .build();

        log.info("Store TSNE Coordinates for Plotting....");
        String outputFile = WebUtils.getServletContext().getRealPath("") + "upload\\tsne\\weights.csv";
        (new File(outputFile)).getParentFile().mkdirs();
        tsne.plot(weights,2,cacheList,outputFile);
        return success("降维完成！");
    }

    /**
     * 获取词向量文件
     * @return
     */
    @RequestMapping("/weights")
    @ResponseBody
    public Object weights() throws IOException {
        String outputFilePath = WebUtils.getServletContext().getRealPath("") + "upload\\tsne\\weights.csv";
        File outputFile = new File(outputFilePath);
        if(outputFile.exists()) {
            WebUtils.getResponse().setContentType("text/plain");
            WebUtils.getResponse().setHeader("Content-Disposition", "attachment; filename=weights.csv");

            log.info("Writing word vectors to text file....");

            InputStream is = new FileInputStream(outputFile);
            IOUtils.copy(is,WebUtils.getResponse().getOutputStream());
            is.close();
            return success();
        }else{
            return error("没有词向量文件");
        }
    }

    @Override
    public BaseService getService() {
        return null;
    }
}
