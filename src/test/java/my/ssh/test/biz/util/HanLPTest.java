package my.ssh.test.biz.util;

//import com.hankcs.lucene.HanLPAnalyzer;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
//import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
//import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
//import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by root on 2016/12/2 0002.
 */
public class HanLPTest {

    @Test
    public void testHanLP() throws IOException {
//        String text = "中华人民共和国很辽阔";
//        for (int i = 0; i < text.length(); ++i)
//        {
//            System.out.print(text.charAt(i) + "" + i + " ");
//        }
//        System.out.println();
//        Analyzer analyzer = new HanLPAnalyzer();
//        TokenStream tokenStream = analyzer.tokenStream("field", text);
//        tokenStream.reset();
//        while (tokenStream.incrementToken())
//        {
//            CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
//            // 偏移量
//            OffsetAttribute offsetAtt = tokenStream.getAttribute(OffsetAttribute.class);
//            // 距离
//            PositionIncrementAttribute positionAttr = tokenStream.getAttribute(PositionIncrementAttribute.class);
//            // 词性
//            TypeAttribute typeAttr = tokenStream.getAttribute(TypeAttribute.class);
//            System.out.printf("[%d:%d %d] %s/%s\n", offsetAtt.startOffset(), offsetAtt.endOffset(), positionAttr.getPositionIncrement(), attribute, typeAttr.type());
//        }
    }

}
