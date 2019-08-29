package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.core.models.DeviceWithLastUser;
import net.thumbtack.testdevices.core.repositories.DeviceDao;
import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;
import net.thumbtack.testdevices.dto.response.DeviceWithLastUserResponse;
import net.thumbtack.testdevices.web.converters.DeviceDtoToModelConverter;
import net.thumbtack.testdevices.web.converters.DeviceWithLastUserDtoToModelConverter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class DevicesServiceTest {
    private DeviceDao deviceDao;
    private DevicesServiceImpl devicesServiceImpl;
    private DeviceRequest request;
    private Device device;
    private DeviceDtoToModelConverter deviceDtoToModelConverter;
    private DeviceWithLastUserDtoToModelConverter deviceWithLastUserDtoToModelConverter;
    private List<DeviceWithLastUser> deviceWithLastUserList;
    private List<DeviceWithLastUserResponse> deviceWithLastUserResponseList;
    private List<Device> deviceList;
    private List<DeviceResponse> deviceResponseList;

    @Before
    public void setup() {
        deviceList = mock(List.class);
        deviceResponseList = mock(List.class);
        deviceWithLastUserList = mock(List.class);
        deviceWithLastUserResponseList = mock(List.class);
        deviceDao = mock(DeviceDao.class);
        deviceDtoToModelConverter = mock(DeviceDtoToModelConverter.class);
        deviceWithLastUserDtoToModelConverter = mock(DeviceWithLastUserDtoToModelConverter.class);
        devicesServiceImpl = spy(new DevicesServiceImpl(deviceDao, deviceDtoToModelConverter, deviceWithLastUserDtoToModelConverter));
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

    @Test
    public void testGetDevicesWithLastUserWhoTakenDevice() {
        when(deviceDao.getDevicesWithLastUserWhoTakenDevice("%search%")).thenReturn(deviceWithLastUserList);
        when(
                deviceWithLastUserDtoToModelConverter.getDeviceWithLastUserResponseListFromDeviceWithLastUserList(deviceWithLastUserList)
        ).thenReturn(deviceWithLastUserResponseList);

        devicesServiceImpl.getDevicesWithLastUserWhoTakenDevice("search");

        verify(deviceDao, times(1)).getDevicesWithLastUserWhoTakenDevice(eq("%search%"));
        verify(
                deviceWithLastUserDtoToModelConverter, times(1)
        ).getDeviceWithLastUserResponseListFromDeviceWithLastUserList(deviceWithLastUserList);
    }

    @Test
    public void testGetAllDevices() {
        when(deviceDao.getAll()).thenReturn(deviceList);
        when(
                deviceDtoToModelConverter.getDeviceListResponseFromDeviceList(deviceList)
        ).thenReturn(deviceResponseList);

        devicesServiceImpl.getAll();

        verify(deviceDao, times(1)).getAll();
        verify(
                deviceDtoToModelConverter, times(1)
        ).getDeviceListResponseFromDeviceList(deviceList);
    }

    @Test
    public void testGetMyDevices() {
        when(deviceDao.getMyDevices(1L)).thenReturn(deviceList);
        when(
                deviceDtoToModelConverter.getDeviceListResponseFromDeviceList(deviceList)
        ).thenReturn(deviceResponseList);

        devicesServiceImpl.getMyDevices(1L);

        verify(deviceDao, times(1)).getMyDevices(eq(1L));
        verify(
                deviceDtoToModelConverter, times(1)
        ).getDeviceListResponseFromDeviceList(deviceList);
    }
}
