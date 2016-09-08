package my.ssh.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by admin on 2016/8/26.
 */
public class PDFUtils {

    public static String pdf2html(String pdfPath) throws FileNotFoundException{
        File pdfFile = new File(pdfPath);
        if(StringUtils.isBlank(pdfPath) || !new File(pdfPath).exists()){
            throw new FileNotFoundException("无效的PDF文件路径："+pdfPath);
        }

        String htmlFolder = pdfFile.getParent();

        final String[] pdf2htmlEX = new String[1];
        RuntimeUtils.exec("where pdf2htmlEX", new RuntimeUtils.DefaultMessage(false) {
            @Override
            public void write(String msg) {
                if(StringUtils.isNoneBlank(msg))
                    pdf2htmlEX[0] = msg;
            }

        });

        if(StringUtils.isBlank(pdf2htmlEX[0])){
            throw new FileNotFoundException("没有找到可执行文件：pdf2htmlEX.exe");
        }

        StringBuilder cmd = new StringBuilder(pdf2htmlEX[0]);
        cmd.append(" ").append(pdfPath).append(" --dest-dir ").append(htmlFolder);

        RuntimeUtils.exec(cmd.toString());
        return htmlFolder+"\\"+pdfFile.getName().substring(0,pdfFile.getName().lastIndexOf("."))+".html";
    }

    @Deprecated
    public static void pdf2word(String pdfPath,String wordPath) throws Exception {
        String htmlPath = pdf2html(pdfPath);
        JacobWordBean wb = new JacobWordBean(false);
        wb.createNewDocument();
        wb.insertFile(htmlPath);
        wb.save(wordPath);
        wb.closeDocument();
        wb.close();
    }

}
