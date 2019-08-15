package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceDaoTest {
    @Autowired
    private DeviceDao deviceDao;

    private Device phone;
    private Device tabletPC;

    @Before
    public void setup() {
        deviceDao.deleteAll();
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
    }

    @After
    public void clean() {
        deviceDao.deleteAll();
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
}
