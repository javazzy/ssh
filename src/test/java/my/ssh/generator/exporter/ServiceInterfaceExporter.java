package my.ssh.generator.exporter;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2x.GenericExporter;

import java.io.File;

/**
 * Created by zzy on 16/3/6.
 */
public class ServiceInterfaceExporter extends GenericExporter {
    private static final String POJO_JAVACLASS_FTL = "ftl/Service.ftl";

    public ServiceInterfaceExporter(Configuration cfg, File outputdir) {
        super(cfg, outputdir);
        init();
    }

    protected void init() {
        setTemplateName(POJO_JAVACLASS_FTL);
        setFilePattern("{package-name}/{class-name}Service.java");
    }

    public ServiceInterfaceExporter() {
        init();
    }

    public String getName() {
        return "hbm2service";
    }

}
