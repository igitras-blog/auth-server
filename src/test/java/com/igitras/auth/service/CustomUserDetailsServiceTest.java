package com.igitras.auth.service;

import static org.junit.Assert.*;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.igitras.auth.AuthServerApplication;
import com.igitras.auth.domain.repository.account.AccountRepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by mason on 3/17/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AuthServerApplication.class)
@TestExecutionListeners({
                                DependencyInjectionTestExecutionListener.class,
                                DirtiesContextTestExecutionListener.class,
                                TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class
                        })
@DatabaseSetup({
                       AccountRepositoryTest.DATABASE_ACCOUNT, AccountRepositoryTest.DATABASE_AUTHORITY,
                       AccountRepositoryTest.DATABASE_ACCOUNT_AUTHORITY
               })
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL,
                  value = {
                          AccountRepositoryTest.DATABASE_ACCOUNT, AccountRepositoryTest.DATABASE_AUTHORITY,
                          AccountRepositoryTest.DATABASE_ACCOUNT_AUTHORITY
                  })
@DirtiesContext
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLoadUserByUsername() throws Exception {
        UserDetails masonmei = customUserDetailsService.loadUserByUsername("masonmei");
        assertNotNull(masonmei);
    }
}