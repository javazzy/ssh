package my.ssh.test.biz.util;

import my.ssh.util.PDFUtils;
import org.junit.Test;

import java.io.FileNotFoundException;

public class Pdf2htmlEXUtilTest {

    @Test
    public void testPdf2html(){
        try {
            System.out.println(PDFUtils.pdf2html("E:\\a\\a.pdf"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPdf2word(){
        try {
            PDFUtils.pdf2word("E:\\a\\a.pdf","E:\\a\\a.doc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
