package my.ssh.biz.ssh.sys.controller;

import com.lowagie.text.rtf.RtfWriter2;
import my.ssh.biz.common.controller.ServletContextResource;
import my.ssh.biz.common.controller.SimpleController;
import my.ssh.biz.common.service.BaseService;
import my.ssh.biz.ssh.sys.entity.Test;
import my.ssh.biz.ssh.sys.service.TestService;
import my.ssh.util.ITextWordBean;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

/**
 * 控制器：测试表
 */
@Controller
@RequestMapping("/tests")
public class TestController extends SimpleController<Test> {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private TestService testService;

    @Override
    public BaseService getService() {
        return testService;
    }

    @RequestMapping("/download")
    public void download() throws Exception {

        String baseUrl = ServletContextResource.getRequest().getRequestURL().substring(0,ServletContextResource.getRequest().getRequestURL().indexOf("/api"))+"/";
        String htmlUrl = baseUrl + "api/tests/data";
        String fileName = "导出文件"+ Calendar.getInstance().getTime().getTime()+".doc";

        //请求导出词条内容的页面
        HttpClient client = HttpClients.createDefault();
        logger.debug("访问词条内容页面："+htmlUrl);
        HttpPost request = new HttpPost(htmlUrl);

        //转发请求头信息
        Enumeration<String> headerNames = ServletContextResource.getRequest().getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            request.setHeader(headerName,ServletContextResource.getRequest().getHeader(headerName));
        }

        //转发请求参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Enumeration<String> parameterNames = ServletContextResource.getRequest().getParameterNames();
        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            nvps.add(new BasicNameValuePair(parameterName, ServletContextResource.getRequest().getParameter(parameterName)));
        }
        request.setEntity(new UrlEncodedFormEntity(nvps));

        //获取返回内容
        String htmlContent = EntityUtils.toString(client.execute(request).getEntity());

        //设置下载信息
        ServletContextResource.getResponse().setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(),"iso-8859-1"));
        ServletContextResource.getResponse().setContentType("application/msword");

        ITextWordBean ITextWordBean = new ITextWordBean();
        RtfWriter2.getInstance(ITextWordBean.getDocument(),ServletContextResource.getResponse().getOutputStream());
        String filePath = System.getProperty("java.io.tmpdir") + "/" + Calendar.getInstance().getTime().getTime()+".doc";
        ITextWordBean.openDocument(filePath);
        ITextWordBean.insertHtmlContext(htmlContent);
        ITextWordBean.closeDocument();
        new File(filePath).delete();
    }

    @RequestMapping("/data")
    public String data(String name, Model model) throws IOException {
        model.addAttribute("name",name);
        return "test/test";
    }
}
