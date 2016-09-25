package my.ssh.generator.exporter;

/*
 * Created on 2004-12-01
 *
 */

import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * @author max
 */
public class POJOExporter extends GenericExporter {

    private static final String POJO_JAVACLASS_FTL = "pojo/Pojo.ftl";

    public POJOExporter(Configuration cfg, File outputdir) {
        super(cfg, outputdir);
        init();
    }

    public POJOExporter() {
        init();
    }

    protected void init() {
        setTemplateName(POJO_JAVACLASS_FTL);
        setFilePattern("{package-name}/{class-name}.java");
    }

    public String getName() {
        return "hbm2java";
    }

    protected void setupContext() {
        //TODO: this safe guard should be in the root templates instead for each variable they depend on.
        if (!getProperties().containsKey("ejb3")) {
            getProperties().put("ejb3", "false");
        }
        if (!getProperties().containsKey("jdk5")) {
            getProperties().put("jdk5", "false");
        }
        super.setupContext();
    }
}
