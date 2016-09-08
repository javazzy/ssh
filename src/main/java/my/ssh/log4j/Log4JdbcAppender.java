package my.ssh.log4j;

import org.apache.log4j.Category;
import org.apache.log4j.Priority;
import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by 争洋 on 2015/5/23 0023.
 */
public class Log4JdbcAppender extends JDBCAppender {
    @Override
    protected String getLogStatement(LoggingEvent event) {
        String fqnOfCategoryClass = event.fqnOfCategoryClass;
        Category logger = event.getLogger();
        Priority level = event.getLevel();
        Object message = event.getMessage();
        Throwable throwable = null;
        MyLoggingEvent bEvent = new MyLoggingEvent(fqnOfCategoryClass, logger, level, message, throwable);
        return super.getLogStatement(bEvent);
    }
}
