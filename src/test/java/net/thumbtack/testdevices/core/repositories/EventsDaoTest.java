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
public class EventsDaoTest {
    @Autowired
    private EventsDao eventsDao;
    @Autowired
    private AuthoritiesDao authoritiesDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private DeviceDao deviceDao;

    private User user;
    private Authority userAuthority;
    private List<Long> userAuthorityId;

    private Event event;
    private Device phone;

    @Before
    public void setup() {
        eventsDao.deleteAll();
        usersDao.deleteAll();
        authoritiesDao.deleteAll();
        deviceDao.deleteAll();

        userAuthority = authoritiesDao.insert(new Authority(AuthorityType.USER));
        userAuthorityId = new ArrayList<>();
        userAuthorityId.add(userAuthority.getId());
        user = new User(
                "John",
                "Doe",
                "+79123456789",
                "john.doe@mail.com",
                "passwd"
        );

        event = new Event(
                ActionType.TAKE,
                LocalDateTime.now()
        );

        phone = new Device(
                DeviceType.PHONE,
                "owner",
                "model",
                "osType",
                "description"
        );
    }

    @After
    public void clean() {
        eventsDao.deleteAll();
        usersDao.deleteAll();
        authoritiesDao.deleteAll();
        deviceDao.deleteAll();
    }

    @Test
    public void testInsertEvent() {
        User userAfterAdd = usersDao.insert(userAuthorityId, user);
        Assert.assertNotNull(userAfterAdd.getId());
        Set<Authority> userAfterAddAuthorities = userAfterAdd.getAuthorities();
        Assert.assertEquals(1, userAfterAddAuthorities.size());
        Assert.assertTrue(userAfterAddAuthorities.contains(userAuthority));

        Device phoneAfterAdd = deviceDao.insert(phone);
        Assert.assertNotNull(phoneAfterAdd.getId());

        event.setUserId(userAfterAdd.getId());
        event.setDeviceId(phoneAfterAdd.getId());

        Event eventAfterAdd = eventsDao.insert(event);

        Assert.assertNotNull(eventAfterAdd.getId());
    }

    @Test
    public void testDeleteAll() {
        User userAfterAdd = usersDao.insert(userAuthorityId, user);
        Assert.assertNotNull(userAfterAdd.getId());
        Set<Authority> userAfterAddAuthorities = userAfterAdd.getAuthorities();
        Assert.assertEquals(1, userAfterAddAuthorities.size());
        Assert.assertTrue(userAfterAddAuthorities.contains(userAuthority));

        Device phoneAfterAdd = deviceDao.insert(phone);
        Assert.assertNotNull(phoneAfterAdd.getId());

        event.setUserId(userAfterAdd.getId());
        event.setDeviceId(phoneAfterAdd.getId());
        Event eventAfterAdd = eventsDao.insert(event);
        Assert.assertNotNull(eventAfterAdd.getId());

        Event secondEvent = new Event(
                ActionType.RETURN,
                LocalDateTime.now()
        );
        secondEvent.setUserId(userAfterAdd.getId());
        secondEvent.setDeviceId(phoneAfterAdd.getId());
        Event secondEventAfterAdd = eventsDao.insert(secondEvent);
        Assert.assertNotNull(secondEventAfterAdd.getId());

        eventsDao.deleteAll();

        Event lastEventByDeviceIdAndUserId = eventsDao.getLastEventByDeviceIdAndUserId(phoneAfterAdd.getId(), userAfterAdd.getId());
        Assert.assertNull(lastEventByDeviceIdAndUserId);
    }

    @Test
    public void testGetLastEventByDeviceIdAndUserId() {
        User userAfterAdd = usersDao.insert(userAuthorityId, user);
        Assert.assertNotNull(userAfterAdd.getId());
        Set<Authority> userAfterAddAuthorities = userAfterAdd.getAuthorities();
        Assert.assertEquals(1, userAfterAddAuthorities.size());
        Assert.assertTrue(userAfterAddAuthorities.contains(userAuthority));

        Device phoneAfterAdd = deviceDao.insert(phone);
        Assert.assertNotNull(phoneAfterAdd.getId());

        event.setUserId(userAfterAdd.getId());
        event.setDeviceId(phoneAfterAdd.getId());
        Event eventAfterAdd = eventsDao.insert(event);
        Assert.assertNotNull(eventAfterAdd.getId());

        Event secondEvent = new Event(
                ActionType.RETURN,
                LocalDateTime.now()
        );
        secondEvent.setUserId(userAfterAdd.getId());
        secondEvent.setDeviceId(phoneAfterAdd.getId());
        Event secondEventAfterAdd = eventsDao.insert(secondEvent);
        Assert.assertNotNull(secondEventAfterAdd.getId());

        Event lastEventByDeviceIdAndUserId = eventsDao.getLastEventByDeviceIdAndUserId(phoneAfterAdd.getId(), userAfterAdd.getId());
        Assert.assertEquals(secondEvent, lastEventByDeviceIdAndUserId);
    }
}
