package net.thumbtack.testdevices.web.converters;

import net.thumbtack.testdevices.core.models.Device;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeviceDtoToModelConverterTest {
    private DeviceRequest request;
    private Device device;
    private DeviceDtoToModelConverter deviceDtoToModelConverter;

    @Before
    public void setup() {
        deviceDtoToModelConverter = new DeviceDtoToModelConverter();
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
    public void testGetDeviceResponseFromDevice() {
        DeviceResponse expectedDeviceResponse = new DeviceResponse(
                1L,
                DeviceType.PHONE,
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra"
        );
        Assert.assertEquals(expectedDeviceResponse, deviceDtoToModelConverter.getDeviceResponseFromDevice(device));
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
        Assert.assertEquals(expectedDevice, deviceDtoToModelConverter.getDeviceFromDeviceRequest(request));
    }
}
