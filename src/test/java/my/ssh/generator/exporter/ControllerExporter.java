package my.ssh.generator.exporter;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2x.GenericExporter;

import java.io.File;

/**
 * Created by zzy on 16/3/6.
 */
public class ControllerExporter extends GenericExporter {
    private static final String POJO_JAVACLASS_FTL = "ftl/Controller.ftl";

    public ControllerExporter(Configuration cfg, File outputdir) {
        super(cfg, outputdir);
        init();
    }

    protected void init() {
        setTemplateName(POJO_JAVACLASS_FTL);
        setFilePattern("{package-name}/{class-name}Controller.java");
    }

    public ControllerExporter() {
        init();
    }

    public String getName() {
        return "hbm2controller";
    }

}
