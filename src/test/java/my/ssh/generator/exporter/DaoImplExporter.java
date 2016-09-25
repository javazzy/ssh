package my.ssh.generator.exporter;

import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by zzy on 16/3/6.
 */
public class DaoImplExporter extends GenericExporter {
    private static final String POJO_JAVACLASS_FTL = "ftl/DaoImpl.ftl";

    public DaoImplExporter(Configuration cfg, File outputdir) {
        super(cfg, outputdir);
        init();
    }

    protected void init() {
        setTemplateName(POJO_JAVACLASS_FTL);
        setFilePattern("{package-name}/{class-name}DaoImpl.java");
    }

    public DaoImplExporter() {
        init();
    }

    public String getName() {
        return "hbm2daoImpl";
    }

//    protected void setupContext() {
//        //TODO: this safe guard should be in the root templates instead for each variable they depend on.
//        if(!getProperties().containsKey("ejb3")) {
//            getProperties().put("ejb3", "false");
//        }
//        if(!getProperties().containsKey("jdk5")) {
//            getProperties().put("jdk5", "false");
//        }
//        super.setupContext();
//    }
}
