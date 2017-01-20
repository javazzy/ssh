package my.ssh.test.biz.util;

import my.ssh.util.JcsegUtils;
import org.junit.Test;
import org.lionsoul.jcseg.tokenizer.core.IWord;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/30.
 */
public class JcsegUtilsTest {

    @Test
    public void testJcsegAnalyzer(){
        try {
            List<IWord> words = JcsegUtils.analyzer("米平方米立方米米/秒千米/小时赫兹微米小时马赫兆赫兹公升/分千克微秒".replace("/","／"), "/lexicons/extract");
            for (IWord w: words) {
                System.out.println(JcsegUtils.valueOf(w.getValue()));
            }
            System.out.println("／".length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJcsegExtract(){
        try {
            Map<String,String> words = JcsegUtils.extract("我买了一个轮船，总重量约194吨!".replace("/","／"));
            for (Map.Entry e: words.entrySet()) {
                System.out.println(e.getKey()+":"+e.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
