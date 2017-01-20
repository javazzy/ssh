package my.ssh.test.biz.util;

import my.ssh.util.JacobWordBean;
import org.junit.Test;

/**
 * Created by admin on 2016/8/23.
 */
public class JacobWordBeanTest {

    @Test
    public void testReplaceContext(){
        //要打开的word
        String filePath = "C:\\Users\\admin\\Desktop\\JacobWordBean.docx";
        //要另存为的word
        String saveFilePath = "C:\\Users\\admin\\Desktop\\WordBean1.doc";
        JacobWordBean wb = null;
        try {
            wb = new JacobWordBean(false);
            wb.openDocument(filePath);
            /**
             * replaceText:该方法替换一次
             * replaceAllText：该方法替换文档内所有搜索到的内容
             */
            wb.replaceText("${name}","张争洋");

            /**
             * 注意：
             * 1.另存为文件路径不能为打开文件路径
             * 2.另存为文件后缀只能为doc，不能为docx
             */
            wb.save(saveFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != wb) {
                wb.closeDocument();
                wb.close();
            }
        }
    }

}
