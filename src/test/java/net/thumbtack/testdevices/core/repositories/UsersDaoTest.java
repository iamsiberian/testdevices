package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.ActionType;
import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.core.models.Event;
import net.thumbtack.testdevices.core.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
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
    @Autowired
    private EventsDao eventsDao;
    @Autowired
    private DeviceDao deviceDao;

    private Device phone;
    private Device tabletPC;
    private Event takeEvent;

    private User user;
    private Authority userAuthority;
    private List<Long> userAuthorityId;

    private User administrator;
    private Authority administratorAuthority;
    private List<Long> administratorAuthorityId;


    @Before
    public void setup() {
        eventsDao.deleteAll();
        deviceDao.deleteAll();
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

        phone = new Device(
                DeviceType.PHONE,
                "owner",
                "model",
                "osType",
                "description"
        );
        tabletPC = new Device(
                DeviceType.TABLET_PC,
                "owner",
                "model",
                "osType",
                "description"
        );
        takeEvent = new Event(
                ActionType.TAKE,
                LocalDateTime.now()
        );
    }

    @After
    public void clean() {
        eventsDao.deleteAll();
        deviceDao.deleteAll();
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

    @Test
    public void testGetLastUserWhoTakenDeviceByDeviceId() {
        Device phoneAfterAdd = deviceDao.insert(phone);
        Assert.assertNotNull(phoneAfterAdd.getId());
        Device tabletPCAfterAdd = deviceDao.insert(tabletPC);
        Assert.assertNotNull(tabletPCAfterAdd.getId());

        User userAfterAdd = usersDao.insert(userAuthorityId, user);
        Assert.assertNotNull(userAfterAdd.getId());
        Set<Authority> userAfterAddAuthorities = userAfterAdd.getAuthorities();
        Assert.assertEquals(1, userAfterAddAuthorities.size());
        Assert.assertTrue(userAfterAddAuthorities.contains(userAuthority));

        takeEvent.setUserId(userAfterAdd.getId());
        takeEvent.setDeviceId(phoneAfterAdd.getId());

        Event eventAfterAdd = eventsDao.insert(takeEvent);

        Assert.assertNotNull(eventAfterAdd.getId());

        User lastUserWhoTakenDevice = usersDao.getLastUserWhoTakenDeviceByDeviceId(phoneAfterAdd.getId());
        Assert.assertEquals(userAfterAdd, lastUserWhoTakenDevice);
    }
}
