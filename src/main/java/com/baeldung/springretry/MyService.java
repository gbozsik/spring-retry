package com.baeldung.springretry;

import java.sql.SQLException;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface MyService {

    @Retryable
    void retryService() throws SQLException;

//    @Retryable(value = { SQLException.class }, maxAttempts = 2, backoff = @Backoff(delay = 2000))
    String retryServiceWithRecovery(String sql) throws Throwable;

    @Recover
    String recover(SQLException e, String sql);

    String defaultRecover(String sql);

    void templateRetryService();
}
