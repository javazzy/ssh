package my.ssh.biz.ssh.sys.ws.impl;

import my.ssh.biz.ssh.sys.service.TestService;
import my.ssh.biz.ssh.sys.ws.TestWebService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;

/**
 *  服务实现： 测试表
 */
@Component
@WebService(endpointInterface = "my.ssh.biz.ssh.sys.ws.TestWebService")
public class TestWebServiceImpl implements TestWebService{

    @Resource
    private TestService testService;

    @Override
    public String hello(String name) {
        return "hello "+name;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }
}
