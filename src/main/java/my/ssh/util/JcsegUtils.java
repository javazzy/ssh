package my.ssh.util;

import org.apache.commons.lang3.StringUtils;
import org.lionsoul.jcseg.tokenizer.core.*;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/30.
 */
public class JcsegUtils {

    private static Map<String,ADictionary> dicMap = new HashMap<String,ADictionary>();

    /**
     * 根据词库目录生成词库对象
     * @param lexiconPaths
     * @return
     * @throws Exception
     */
    public static ADictionary getDictionary(String...lexiconPaths) throws Exception {
        //词库路径有效性检查
        if(null == lexiconPaths || lexiconPaths.length==0){
            return null;
        }else{
            for(int i = 0; i< lexiconPaths.length; i++){
                if(StringUtils.isBlank(lexiconPaths[i])){
                    throw new Exception("词库路径不能为空");
                } else {
                    String absoluteLexiconPath = PathUtils.getClassesDir() + lexiconPaths[i];
                    boolean existsFlag = false;
                    if(new File(absoluteLexiconPath).exists()){
                        existsFlag = true;
                    }else{
                        absoluteLexiconPath = System.getProperty("user.dir")+"/src/main/resources/"+lexiconPaths[i];
                    }
                    if(!existsFlag && !new File(absoluteLexiconPath).exists()){
                        throw new Exception("词库路径无效："+lexiconPaths[i]);
                    }
                    lexiconPaths[i] = absoluteLexiconPath;
                }
            }
        }

        if(!dicMap.containsKey(String.join(",",lexiconPaths))){
            JcsegTaskConfig config = new JcsegTaskConfig(true);
            config.setLexiconPath(lexiconPaths);
            //创建默认单例词库实现，并且按照config配置加载词库
            dicMap.put(String.join(",",lexiconPaths),DictionaryFactory.createDefaultDictionary(config,true));
        }
        return dicMap.get(String.join(",",lexiconPaths));
    }

    /**
     * 分析出文本内容中的所有词条
     * @param source
     * @param lexiconPaths
     * @return
     * @throws Exception
     */
    public static List<IWord> analyzer(String source,String...lexiconPaths) throws Exception {
        ADictionary dic = getDictionary(lexiconPaths);
        List<IWord> words = new ArrayList<>();
        if(null == dic){
            return words;
        }

        //依据给定的ADictionary和JcsegTaskConfig来创建ISegment
        ISegment segment = SegmentFactory.createJcseg(JcsegTaskConfig.DETECT_MODE, new Object[]{dic.getConfig(), dic});

        //设置要被分词的文本。备注：以下代码可以反复调用，seg为非线程安全
        segment.reset(new StringReader(source));

        //获取分词结果
        IWord word;
        while ((word = segment.next()) != null) {
            words.add(word);
        }
        return words;
    }

    /**
     * 对文本内容加入词条超链接
     * @param content
     * @param lexicon
     * @return
     * @throws Exception
     */
    public static String addHref(String content, String...lexicon) throws Exception {

        List<IWord> words = JcsegUtils.analyzer(content,lexicon);
        List<Integer> indexs = new ArrayList<>();
        int i = 0;
        for (IWord word: words) {
            i = content.indexOf(valueOf(word.getValue()),i);
            indexs.add(i);
            i += word.getValue().length();
        }

        StringBuilder sb = new StringBuilder(content);
        for (int in = indexs.size()-1;in >=0; in--) {
            int end  = indexs.get(in) + words.get(in).getValue().length();
            IWord word = words.get(in);
            String toReplaceWord = valueOf(word.getValue());
            if(null != word.getSyn() && StringUtils.isNoneBlank(word.getSyn()[0])){
                toReplaceWord = valueOf(word.getSyn()[0]);
            }
            sb.replace(indexs.get(in),end,"<a href=\"#"+toReplaceWord+"\">"+valueOf(word.getValue())+"</a>");
        }
        return sb.toString();
    }

    /**
     * 分析出文本内容中的属性值
     * @param content
     * @return
     * @throws Exception
     */
    public static Map<String,String> extract(String content) throws Exception {

        String lexicon = "/lexicons/extract";

        content =  content.replace("/","／");

        List<IWord> words = JcsegUtils.analyzer(content,lexicon);
        if(null == words || words.size() == 0) return null;

        Map<String,String> attrMap = new HashMap();
        int currentIndex = 0;
        for (int i = 0;i< words.size(); i++) {
            IWord word = words.get(i);

            currentIndex = word.getPosition() + word.getValue().length();

            if("n".equals(word.getPartSpeech()[0]) && (i+1)<words.size() && "q".equals(words.get(i+1).getPartSpeech()[0])){
                String attrName = word.getValue();
                int unitIndex = words.get(i+1).getPosition();
                String attrValue = content.substring(currentIndex,unitIndex);
//                if(attrValue.length() < 20 && !attrValue.matches(".*\\s.*") ) {
                    attrMap.put(valueOf(attrName), valueOf(attrValue));
//                }
            }

        }
        return attrMap;
    }

    /**
     * 删除单个词库中的一个词
     * @param word
     */
    public static void removeWord(String word){
        for (ADictionary dic : dicMap.values()) {
            dic.remove(IWord.T_CJK_WORD, word);
        }
    }

    /**
     * 删除所有词库中的一个词
     * @param word
     * @param lexiconPaths
     */
    public static void removeWord(String word,String...lexiconPaths){
        String paths = String.join(",",lexiconPaths);
        if(dicMap.containsKey(paths)) {
            dicMap.get(paths).remove(IWord.T_CJK_WORD, word);
        }
    }

    /**
     * 将文本中的“／”转换为“/”
     * @param value
     * @return
     */
    public static String valueOf(String value){
        if(StringUtils.isNoneBlank(value)){
            return value.replace("／","/");
        }
        return null;
    }

    /**
     * 对文本内容加入词条超链接
     * @param content
     * @return
     * @throws Exception
     */
    public static List<String> getAllWords(String content) throws Exception {
        ADictionary dic = getDictionary("/lexicons/lexicon");

        //依据给定的ADictionary和JcsegTaskConfig来创建ISegment
        ISegment segment = SegmentFactory.createJcseg(JcsegTaskConfig.COMPLEX_MODE, new Object[]{dic.getConfig(), dic});

        //设置要被分词的文本。备注：以下代码可以反复调用，seg为非线程安全
        segment.reset(new StringReader(content));

        List<String> wordList = new ArrayList<>();
        //获取分词结果
        IWord word;
        while ((word = segment.next()) != null) {
            wordList.add(word.getValue());
        }
        return wordList;
    }

}
