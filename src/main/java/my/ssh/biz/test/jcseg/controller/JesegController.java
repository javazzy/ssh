package my.ssh.biz.test.jcseg.controller;

import my.ssh.util.JcsegUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/jcseg")
public class JesegController {

    private final Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/analyzer")
    @ResponseBody
    public Object analyzer(String content, String...lexicon) throws Exception {
       return JcsegUtils.addHref(content,lexicon);
    }

    @RequestMapping(value = "/extract")
    @ResponseBody
    public Object extract(String content) throws Exception {
        return JcsegUtils.extract(content);
    }

}
