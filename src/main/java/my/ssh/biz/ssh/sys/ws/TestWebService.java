package my.ssh.biz.ssh.sys.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @className:TestService.java
 * @Desc:服务接口： 测试表
 * @author:lizhuang
 * @createTime:2012-12-21 上午12:57:18
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TestWebService {

    @WebMethod
    @WebResult(name="result")
    String hello(@WebParam(name = "name") String name);

}
