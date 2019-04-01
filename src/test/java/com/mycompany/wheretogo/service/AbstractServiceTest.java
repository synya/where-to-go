package com.mycompany.wheretogo.service;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import static com.mycompany.wheretogo.util.ValidationUtil.getRootCause;
import static org.hamcrest.CoreMatchers.instanceOf;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populate-db.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest {
    protected static final Integer ENTITY_NOT_FOUND_ID = 1;

    public <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertThat(getRootCause(e), instanceOf(exceptionClass));
        }
    }
}
