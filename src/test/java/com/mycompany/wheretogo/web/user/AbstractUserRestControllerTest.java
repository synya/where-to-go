package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.service.UserService;
import com.mycompany.wheretogo.web.AbstractRestControllerTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractUserRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    protected UserService userService;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
