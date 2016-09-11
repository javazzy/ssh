package my.ssh.generator;

import my.ssh.generator.exporter.*;
import org.hibernate.cfg.JDBCMetaDataConfiguration;
import org.hibernate.cfg.reveng.ReverseEngineeringSettings;
import org.hibernate.cfg.reveng.SchemaSelection;
import org.hibernate.cfg.reveng.TableIdentifier;
import org.hibernate.tool.hbm2x.POJOExporter;
import org.hibernate.tool.hbmlint.detector.TableSelectorStrategy;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

public class EntityGenerate {

    File outputDirFile = null;

    private JDBCMetaDataConfiguration cfg = null;

    @Test
    public void generatorAll() throws Exception{
        entityGenerator();
        daoGenerator();
        daoImplGenerator();
//        serviceGenerator();
//        serviceImplGenerator();
//        controllerGenerator();
    }

    @Test
    public void entityGenerator() throws Exception{
        init("entity");

        POJOExporter exporter = new POJOExporter(cfg, outputDirFile);
        // ejb3注解
        exporter.getProperties().setProperty("ejb3", "true");
        // jdk5语法(主要是集合类的泛型处理)
        exporter.getProperties().setProperty("jdk5", "true");
        // 启动
        exporter.start();
    }
    @Test
    public void daoGenerator() throws Exception{
        init("dao");

        DaoInterfaceExporter exporter = new DaoInterfaceExporter(cfg, outputDirFile);
        exporter.start();
    }
    @Test
    public void daoImplGenerator() throws Exception{
        init("dao.impl");
        DaoImplExporter exporter = new DaoImplExporter(cfg, outputDirFile);
        // ejb3注解
        exporter.getProperties().setProperty("ejb3", "true");
        // jdk5语法(主要是集合类的泛型处理)
        exporter.getProperties().setProperty("jdk5", "true");
        exporter.start();
    }

    public void serviceGenerator() throws Exception {
        init("service");
        ServiceInterfaceExporter exporter = new ServiceInterfaceExporter(cfg, outputDirFile);
        exporter.start();
    }
    public void serviceImplGenerator() throws Exception {
        init("service.impl");
        ServiceImplExporter exporter = new ServiceImplExporter(cfg, outputDirFile);
        exporter.start();
    }
    public void controllerGenerator() throws Exception {
        init("controller");
        ControllerExporter exporter = new ControllerExporter(cfg, outputDirFile);
        exporter.start();
    }


    public void init(String subPackageName) throws Exception{
        //读取jdbc-default.properties配置文件
        Properties p = new Properties();
        p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc/jdbc-default.properties"));

        String schema = p.getProperty("generator.schema");
        String catalog = schema;
        String table = p.getProperty("generator.table");
        String outputDir = p.getProperty("generator.outputDir");
        String packageName = p.getProperty("generator.packageName");

        //读取hibernate.cfg.xml配置文件
        cfg = new JDBCMetaDataConfiguration();
        cfg.configure();

        //配置数据库表
        TableSelectorStrategy strategy = new TableSelectorStrategy(cfg.getReverseEngineeringStrategy());
        strategy.addSchemaSelection(new SchemaSelection(catalog,schema,table));
        strategy.tableToIdentifierPropertyName(new TableIdentifier("id"));

        //获取配置实体包名
        ReverseEngineeringSettings settings = new ReverseEngineeringSettings(strategy);
        settings.setDefaultPackageName(packageName + "." + subPackageName);//实体包路径
        settings.setDetectOneToOne(true);//一对一关联
        settings.setDetectManyToMany(true);//多对多关联
        settings.setCreateManyToOneForForeignKey(true);//多对一关联
        settings.setCreateCollectionForForeignKey(true);//一对多关联
        settings.setDetectOptimisticLock(true);//乐观锁

        strategy.setSettings(settings);
        cfg.setReverseEngineeringStrategy(strategy);
        cfg.readFromJDBC();

        if(null == outputDirFile)
            outputDirFile = new File(System.getProperty("user.dir") + outputDir);
    }
}
