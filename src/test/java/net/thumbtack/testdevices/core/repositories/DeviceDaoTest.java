package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.ActionType;
import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.core.models.DeviceWithLastUser;
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
public class DeviceDaoTest {
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private AuthoritiesDao authoritiesDao;
    @Autowired
    private EventsDao eventsDao;

    private Device phone;
    private Device tabletPC;
    private Event takeEvent;
    private User user;
    private Authority userAuthority;
    private List<Long> userAuthorityId;

    @Before
    public void setup() {
        eventsDao.deleteAll();
        deviceDao.deleteAll();
        usersDao.deleteAll();
        authoritiesDao.deleteAll();

        phone = new Device(
                DeviceType.PHONE,
                "owner",
                "phone",
                "osType",
                "description"
        );
        tabletPC = new Device(
                DeviceType.TABLET_PC,
                "owner",
                "tabletPc",
                "osType",
                "description"
        );
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
    public void testInsertDevice() {
        Device phoneAfterAdd = deviceDao.insert(phone);
        Assert.assertNotNull(phoneAfterAdd.getId());
    }

    @Test
    public void testGetAll() {
        Device phoneAfterAdd = deviceDao.insert(phone);
        Assert.assertNotNull(phoneAfterAdd.getId());
        Device tabletPCAfterAdd = deviceDao.insert(tabletPC);
        Assert.assertNotNull(tabletPCAfterAdd.getId());
        List<Device> deviceList = deviceDao.getAll();
        Assert.assertEquals(2, deviceList.size());
        Assert.assertTrue(deviceList.contains(phoneAfterAdd));
        Assert.assertTrue(deviceList.contains(tabletPCAfterAdd));
    }

    @Test
    public void testGetById() {
        Device phoneAfterAdd = deviceDao.insert(phone);
        Assert.assertNotNull(phoneAfterAdd.getId());
        Assert.assertEquals(phoneAfterAdd, deviceDao.getById(phoneAfterAdd.getId()));
    }

    @Test
    public void testDeleteById() {
        Device phoneAfterAdd = deviceDao.insert(phone);
        Assert.assertNotNull(phoneAfterAdd.getId());
        Assert.assertEquals(phoneAfterAdd, deviceDao.getById(phoneAfterAdd.getId()));
        deviceDao.deleteById(phoneAfterAdd.getId());
        Assert.assertNull(deviceDao.getById(phoneAfterAdd.getId()));
    }

    @Test
    public void testDeleteAll() {
        Device phoneAfterAdd = deviceDao.insert(phone);
        Assert.assertNotNull(phoneAfterAdd.getId());
        Device tabletPCAfterAdd = deviceDao.insert(tabletPC);
        Assert.assertNotNull(tabletPCAfterAdd.getId());
        List<Device> deviceList = deviceDao.getAll();
        Assert.assertEquals(2, deviceList.size());
        Assert.assertTrue(deviceList.contains(phoneAfterAdd));
        Assert.assertTrue(deviceList.contains(tabletPCAfterAdd));
        deviceDao.deleteAll();
        Assert.assertTrue(deviceDao.getAll().isEmpty());
    }

    @Test
    public void testGetDevicesWithLastUserWhoTakenDevice_WithNullSearch() {
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

        User user = usersDao.getLastUserWhoTakenDeviceByDeviceId(phoneAfterAdd.getId());
        Assert.assertNotNull(user);

        List<DeviceWithLastUser> deviceWithLastUserList = deviceDao.getDevicesWithLastUserWhoTakenDevice(null);
        User user0 = deviceWithLastUserList.get(0).getUser();
        Assert.assertEquals(userAfterAdd, user0);
        User user1 = deviceWithLastUserList.get(1).getUser();
        Assert.assertNull(user1);
    }

    @Test
    public void testGetDevicesWithLastUserWhoTakenDevice_WithGoodSearchWithExistsUser() {
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

        User user = usersDao.getLastUserWhoTakenDeviceByDeviceId(phoneAfterAdd.getId());
        Assert.assertNotNull(user);

        List<DeviceWithLastUser> deviceWithLastUserList = deviceDao.getDevicesWithLastUserWhoTakenDevice("%phone%");
        Assert.assertEquals(1, deviceWithLastUserList.size());
        User user0 = deviceWithLastUserList.get(0).getUser();
        Assert.assertEquals(userAfterAdd, user0);
    }

    @Test
    public void testGetDevicesWithLastUserWhoTakenDevice_WithGoodSearchWithNullUser() {
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

        User user = usersDao.getLastUserWhoTakenDeviceByDeviceId(phoneAfterAdd.getId());
        Assert.assertNotNull(user);

        List<DeviceWithLastUser> deviceWithLastUserList = deviceDao.getDevicesWithLastUserWhoTakenDevice("%tabletpc%");
        Assert.assertEquals(1, deviceWithLastUserList.size());
        User user0 = deviceWithLastUserList.get(0).getUser();
        Assert.assertNull(user0);
    }

    @Test
    public void testGetDevicesWithLastUserWhoTakenDevice_WithBadSearch() {
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

        User user = usersDao.getLastUserWhoTakenDeviceByDeviceId(phoneAfterAdd.getId());
        Assert.assertNotNull(user);

        List<DeviceWithLastUser> deviceWithLastUserList = deviceDao.getDevicesWithLastUserWhoTakenDevice("asdadadadadadad");
        Assert.assertEquals(0, deviceWithLastUserList.size());
    }
}
