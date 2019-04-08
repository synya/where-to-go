package com.mycompany.wheretogo.web.user;

import com.mycompany.wheretogo.service.UserService;
import com.mycompany.wheretogo.util.JpaUtil;
import com.mycompany.wheretogo.web.AbstractRestControllerTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

public abstract class AbstractUserRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    protected UserService userService;

    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
