package my.ssh.test.common;

import my.ssh.initializer.config.SpringContextConfig;
import my.ssh.initializer.config.SpringMvcConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * Controller层测试支持，需要继承该类，调用post方法进行调用
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringContextConfig.class, SpringMvcConfig.class})
public class ControllerTestSupport {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Controller公共调用入口
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String post(String url, Map<String, String> params) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url);
        request.accept(MediaType.APPLICATION_JSON);
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                request.param(entry.getKey(), entry.getValue());
            }
        }
        ResultActions ra = this.mockMvc.perform(request);
        MvcResult mr = ra.andReturn();
        return mr.getResponse().getContentAsString();
    }

    /**
     * Controller公共调用入口
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String get(String url) throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(url);
        request.accept(MediaType.APPLICATION_JSON);
        ResultActions ra = this.mockMvc.perform(request);
        MvcResult mr = ra.andReturn();
        return mr.getResponse().getContentAsString();
    }
}
