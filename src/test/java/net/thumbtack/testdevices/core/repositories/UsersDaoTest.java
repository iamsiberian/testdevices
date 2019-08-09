package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.core.models.User;
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
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersDaoTest {
    @Autowired
    private AuthoritiesDao authoritiesDao;
    @Autowired
    private UsersDao usersDao;

    private User user;
    private Authority userAuthority;
    private List<Long> userAuthorityId;

    private User administrator;
    private Authority administratorAuthority;
    private List<Long> administratorAuthorityId;


    @Before
    public void setup() {
        usersDao.deleteAll();
        authoritiesDao.deleteAll();

        userAuthority = authoritiesDao.insert(new Authority(AuthorityType.USER));
        userAuthorityId = new ArrayList<>();
        userAuthorityId.add(userAuthority.getId());
        user = new User(
                "John",
                "Doe",
                "+79123456789",
                "john.doe@mail.com",
                "12345"
        );

        administratorAuthority = authoritiesDao.insert(new Authority(AuthorityType.ADMINISTRATOR));
        administratorAuthorityId = new ArrayList<>();
        administratorAuthorityId.add(userAuthority.getId());
        administratorAuthorityId.add(administratorAuthority.getId());
        administrator = new User(
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                "123456"
        );
    }

    @After
    public void clean() {
        usersDao.deleteAll();
        authoritiesDao.deleteAll();
    }

    @Test
    public void testInsertUserWithAuthorityIdsSet() {
        User userAfterAdd = usersDao.insert(userAuthorityId, user);
        Assert.assertNotNull(userAfterAdd.getId());
        Set<Authority> userAfterAddAuthorities = userAfterAdd.getAuthorities();
        Assert.assertEquals(1, userAfterAddAuthorities.size());
        Assert.assertTrue(userAfterAddAuthorities.contains(userAuthority));
    }

    @Test
    public void testInsertUserWithOneAuthorityId() {
        User userAfterAdd = usersDao.insert(userAuthority.getId(), user);
        Assert.assertNotNull(userAfterAdd.getId());
        Set<Authority> userAfterAddAuthorities = userAfterAdd.getAuthorities();
        Assert.assertEquals(1, userAfterAddAuthorities.size());
        Assert.assertTrue(userAfterAddAuthorities.contains(userAuthority));
    }

    @Test
    public void testInsertAdministrator() {
        User administratorAfterAdd = usersDao.insert(administratorAuthorityId, administrator);
        Assert.assertNotNull(administratorAfterAdd.getId());
        Set<Authority> administratorAfterAddAuthorities = administratorAfterAdd.getAuthorities();
        Assert.assertEquals(2, administratorAfterAddAuthorities.size());
        Assert.assertTrue(administratorAfterAddAuthorities.contains(userAuthority));
        Assert.assertTrue(administratorAfterAddAuthorities.contains(administratorAuthority));
    }

    @Test
    public void testGetUserById() {
        User userAfterAdd = usersDao.insert(userAuthorityId, user);
        Assert.assertNotNull(userAfterAdd.getId());
        Set<Authority> userAuthoritiesAfterAdd = userAfterAdd.getAuthorities();
        Assert.assertTrue(userAuthoritiesAfterAdd.contains(userAuthority));
        Assert.assertEquals(userAfterAdd, usersDao.getById(userAfterAdd.getId()));

        User administratorAfterAdd = usersDao.insert(administratorAuthorityId, administrator);
        Set<Authority> administratorAuthoritiesAfterAdd = administratorAfterAdd.getAuthorities();
        Assert.assertEquals(2, administratorAuthoritiesAfterAdd.size());
        Assert.assertTrue(administratorAuthoritiesAfterAdd.contains(userAuthority));
        Assert.assertTrue(administratorAuthoritiesAfterAdd.contains(administratorAuthority));
        Assert.assertEquals(administratorAfterAdd, usersDao.getById(administratorAfterAdd.getId()));
    }

    @Test
    public void testGetByEMail() {
        User userAfterAdd = usersDao.insert(userAuthorityId, user);
        Assert.assertNotNull(userAfterAdd.getId());
        Set<Authority> userAuthoritiesAfterAdd = userAfterAdd.getAuthorities();
        Assert.assertTrue(userAuthoritiesAfterAdd.contains(userAuthority));
        Assert.assertEquals(userAfterAdd, usersDao.findByLogin(userAfterAdd.getEmail()));
    }

    @Test
    public void testDeleteAll() {
        User userAfterAdd = usersDao.insert(userAuthorityId, user);
        Assert.assertNotNull(userAfterAdd.getId());
        Set<Authority> userAfterAddAuthorities = userAfterAdd.getAuthorities();
        Assert.assertTrue(userAfterAddAuthorities.contains(userAuthority));
        Assert.assertEquals(userAfterAdd, usersDao.getById(userAfterAdd.getId()));

        User administratorAfterAdd = usersDao.insert(administratorAuthorityId, administrator);
        Assert.assertNotNull(administratorAfterAdd.getId());
        Set<Authority> administratorAfterAddAuthorities = administratorAfterAdd.getAuthorities();
        Assert.assertEquals(2, administratorAfterAddAuthorities.size());
        Assert.assertTrue(administratorAfterAddAuthorities.contains(userAuthority));
        Assert.assertTrue(administratorAfterAddAuthorities.contains(administratorAuthority));
        Assert.assertEquals(administratorAfterAdd, usersDao.getById(administratorAfterAdd.getId()));

        usersDao.deleteAll();
        Assert.assertNull(usersDao.getById(userAfterAdd.getId()));
        Assert.assertNull(usersDao.getById(administratorAfterAdd.getId()));
    }
}
