package com.baeldung.springretry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLException;

@Service
public class MyServiceImpl implements MyService {

    private static final Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);


    private RetryTemplate retryTemplate;

    @Autowired
    public MyServiceImpl(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    @Override
    public void retryService() throws SQLException {
        logger.info("throw RuntimeException in method retryService()");
        throw new SQLException();
    }
    private String sql;
    @Override
    public String retryServiceWithRecovery(String sql) throws Throwable {
        this.sql = sql;
        if (StringUtils.isEmpty(sql)) {
//            logger.info("throw SQLException in method retryServiceWithRecovery()");
//            throw new SQLException();
//            retryTemplate.execute(retryContext -> {
//                System.out.println("retryTemplate in retryServiceRecovery");
////                return retryContext;
//                throw new SQLException();
//            });
            String foo = retryTemplate.execute((RetryCallback<String, SQLException>) context -> {
                        // business logic here
                        System.out.println("retryTemplate in retryServiceRecovery");
                        throw new SQLException();
//                        return null;
                    },
                    context -> {
                        // recover logic here
                        System.out.println("recovery in retryServiceRecovery");
//                        throw new SQLException();
                        this.sql = "hali";
                        return null;
                    });
        }
        return this.sql;
    }

    @Override
    public String recover(SQLException e, String sql) {
        logger.info("In recover method");
        System.out.println(("In recover method"));
        System.out.println("sql in recover: " + sql);
        sql = "hali";
        return sql;
    }

    @Override
    public String defaultRecover(String sql) {
        return sql;
    }

    @Override
    public void templateRetryService() {
        logger.info("throw RuntimeException in method templateRetryService()");
        System.out.println("IN templateRetryService");
//        throw new RuntimeException();
    }
}
