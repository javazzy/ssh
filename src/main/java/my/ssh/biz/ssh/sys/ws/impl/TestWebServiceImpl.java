package my.ssh.biz.ssh.sys.ws.impl;

import my.ssh.biz.ssh.sys.ws.TestWebService;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

/**
 *  服务实现： 测试表
 */
@Component
@WebService(endpointInterface = "my.ssh.biz.ssh.sys.ws.TestWebService")
public class TestWebServiceImpl implements TestWebService{
    @Override
    public String hello(String name) {
        return "hello "+name;
    }
}
