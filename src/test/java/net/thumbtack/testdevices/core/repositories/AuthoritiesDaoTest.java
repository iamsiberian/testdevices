package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthoritiesDaoTest {
    @Autowired
    private AuthoritiesDao authoritiesDao;
    private Authority userAuthority;
    private Authority administratorAuthority;

    @Before
    public void setup() {
        authoritiesDao.deleteAll();
        userAuthority = new Authority(AuthorityType.USER);
        administratorAuthority = new Authority(AuthorityType.ADMINISTRATOR);
    }

    @After
    public void clean() {
        authoritiesDao.deleteAll();
    }

    @Test
    public void testInsertOneAuthority() {
        Assert.assertNull(userAuthority.getId());
        authoritiesDao.insert(userAuthority);
        Assert.assertNotNull(userAuthority.getId());
        List<Authority> authoritiesList = authoritiesDao.getAll();
        Assert.assertFalse(authoritiesList.isEmpty());
        Assert.assertEquals(1, authoritiesList.size());
        Assert.assertTrue(authoritiesList.contains(userAuthority));
    }

    @Test
    public void testInsertTwoAuthority() {
        Assert.assertNull(userAuthority.getId());
        Assert.assertNull(administratorAuthority.getId());
        authoritiesDao.insert(userAuthority);
        authoritiesDao.insert(administratorAuthority);
        Assert.assertNotNull(userAuthority.getId());
        Assert.assertNotNull(administratorAuthority.getId());
        List<Authority> authoritiesList = authoritiesDao.getAll();
        Assert.assertFalse(authoritiesList.isEmpty());
        Assert.assertEquals(2, authoritiesList.size());
        Assert.assertTrue(authoritiesList.contains(userAuthority));
        Assert.assertTrue(authoritiesList.contains(administratorAuthority));
    }

    @Test
    public void testInsertAndGetAuthoritiesList() {
        List<Authority> authoritiesList = new ArrayList<>();
        authoritiesList.add(userAuthority);
        authoritiesList.add(administratorAuthority);
        authoritiesDao.insert(authoritiesList);
        Assert.assertEquals(2, authoritiesDao.getAll().size());
    }

    @Test
    public void testGetByName() {
        Assert.assertNull(userAuthority.getId());
        authoritiesDao.insert(userAuthority);
        Assert.assertNotNull(userAuthority.getId());
        Authority actualAuthority = authoritiesDao.getByName(AuthorityType.USER.getAuthorityType());
        Assert.assertEquals(userAuthority, actualAuthority);
    }

    @Test
    public void testDeleteAllAuthorities() {
        List<Authority> authoritiesList = new ArrayList<>();
        authoritiesList.add(userAuthority);
        authoritiesList.add(administratorAuthority);
        authoritiesDao.insert(authoritiesList);
        Assert.assertEquals(2, authoritiesDao.getAll().size());
        authoritiesDao.deleteAll();
        Assert.assertTrue(authoritiesDao.getAll().isEmpty());
    }
}
