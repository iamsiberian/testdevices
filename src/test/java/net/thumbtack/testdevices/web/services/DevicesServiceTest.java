package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DevicesServiceTest {
    private DeviceDao deviceDao;
    private DevicesServiceImpl devicesServiceImpl;
    private DeviceRequest request;
    private DeviceResponse response;
    private Device device;

    @Before
    public void setup() {
        deviceDao = mock(DeviceDao.class);
        devicesServiceImpl = spy(new DevicesServiceImpl(deviceDao));
        request = new DeviceRequest(
                DeviceType.PHONE,
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra"
        );
        device = new Device(
                1L,
                DeviceType.PHONE,
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra"
        );
    }

    @Test
    public void testAddDevice() {
        when(deviceDao.insert(any(Device.class))).thenReturn(device);

        devicesServiceImpl.addDevice(request);

        verify(deviceDao, times(1)).insert(any(Device.class));
    }

    @Test
    public void testGetDeviceResponseFromDevice() {
        DeviceResponse expectedDeviceResponse = new DeviceResponse(
                1L,
                DeviceType.PHONE,
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra"
        );
        Assert.assertEquals(expectedDeviceResponse, devicesServiceImpl.getDeviceResponseFromDevice(device));
    }

    @Test
    public void testGetDeviceFromDeviceRequest() {
        Device expectedDevice = new Device(
                DeviceType.PHONE,
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra"
        );
        Assert.assertEquals(expectedDevice, devicesServiceImpl.getDeviceFromDeviceRequest(request));
    }
}
