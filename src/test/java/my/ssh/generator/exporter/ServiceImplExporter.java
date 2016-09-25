package my.ssh.generator.exporter;

import org.hibernate.cfg.Configuration;

import java.io.File;

/**
 * Created by zzy on 16/3/6.
 */
public class ServiceImplExporter extends GenericExporter {
    private static final String POJO_JAVACLASS_FTL = "ftl/ServiceImpl.ftl";

    public ServiceImplExporter(Configuration cfg, File outputdir) {
        super(cfg, outputdir);
        init();
    }

    protected void init() {
        setTemplateName(POJO_JAVACLASS_FTL);
        setFilePattern("{package-name}/{class-name}ServiceImpl.java");
    }

    public ServiceImplExporter() {
        init();
    }

    public String getName() {
        return "hbm2serviceImpl";
    }

}
