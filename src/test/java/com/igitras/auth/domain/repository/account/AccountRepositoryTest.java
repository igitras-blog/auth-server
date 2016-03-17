package com.igitras.auth.domain.repository.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.igitras.auth.AuthServerApplication;
import com.igitras.auth.domain.entity.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by mason on 3/16/16.
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
public class AccountRepositoryTest {
    public static final String DATABASE_ACCOUNT = "classpath:datasets/account/accounts.xml";
    public static final String DATABASE_AUTHORITY = "classpath:datasets/account/authorities.xml";
    public static final String DATABASE_ACCOUNT_AUTHORITY = "classpath:datasets/account/account_authories.xml";

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindAllByActivatedIsFalseAndCreatedDateBefore() throws Exception {
        List<Account> tobeDeleted
                = accountRepository.findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime.now());
        assertEquals(0, tobeDeleted.size());
    }

    @Test
    public void testFindOneByEmail() throws Exception {
        Optional<Account> oneByEmail = accountRepository.findOneByEmail("mason@igitras.com");
        assertTrue(oneByEmail.isPresent());
        assertEquals("masonmei", oneByEmail.get().getLogin());
    }

    @Test
    public void testFindOneByLogin() throws Exception {
        Optional<Account> masonmei = accountRepository.findOneByLogin("masonmei");
        assertTrue(masonmei.isPresent());
        assertEquals("mason@igitras.com", masonmei.get().getEmail());
    }

    @Test
    public void testFindOneById() throws Exception {
        Optional<Account> masonmei = accountRepository.findOneById(1L);
        assertTrue(masonmei.isPresent());
        masonmei = accountRepository.findOneById(2L);
        assertFalse(masonmei.isPresent());
    }

    @Test
    public void testDelete() throws Exception {
        accountRepository.delete(1L);
        Optional<Account> masonmei = accountRepository.findOneByLogin("masonmei");
        assertFalse(masonmei.isPresent());
    }
}