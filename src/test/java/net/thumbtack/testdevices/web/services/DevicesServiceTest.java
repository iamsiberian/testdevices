package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.web.converters.DeviceDtoToModelConverter;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class DevicesServiceTest {
    private DeviceDao deviceDao;
    private DevicesServiceImpl devicesServiceImpl;
    private DeviceRequest request;
    private Device device;
    private DeviceDtoToModelConverter deviceDtoToModelConverter;

    @Before
    public void setup() {
        deviceDao = mock(DeviceDao.class);
        deviceDtoToModelConverter = mock(DeviceDtoToModelConverter.class);
        devicesServiceImpl = spy(new DevicesServiceImpl(deviceDao, deviceDtoToModelConverter));
        request = new DeviceRequest(
                DeviceType.PHONE.getDeviceType(),
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
        when(deviceDtoToModelConverter.getDeviceFromDeviceRequest(request)).thenReturn(device);

        devicesServiceImpl.addDevice(request);

        verify(deviceDao, times(1)).insert(any(Device.class));
        verify(deviceDtoToModelConverter, times(1)).getDeviceFromDeviceRequest(eq(request));
    }

    @Test
    public void testDeleteDevice() {
        devicesServiceImpl.deleteDevice(1L);
        verify(deviceDao, times(1)).deleteById(eq(1L));
    }
}
