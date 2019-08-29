package net.thumbtack.testdevices.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.thumbtack.testdevices.core.models.ActionType;
import net.thumbtack.testdevices.core.models.Authority;
import net.thumbtack.testdevices.core.models.AuthorityType;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.dto.response.DeviceResponse;
import net.thumbtack.testdevices.dto.response.DeviceWithLastUserResponse;
import net.thumbtack.testdevices.dto.response.EventResponse;
import net.thumbtack.testdevices.dto.response.UserResponse;
import net.thumbtack.testdevices.web.services.DevicesService;
import net.thumbtack.testdevices.web.services.EventsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DevicesController.class)
public class DevicesControllerTest extends BaseControllerTest {
    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;
    private DeviceRequest deviceRequest;
    private DeviceResponse deviceResponse;
    private EventResponse returnEventResponse;
    private EventResponse takeEventResponse;
    private List<DeviceWithLastUserResponse> deviceWithLastUserResponseList;
    private UserResponse userResponse;
    private Authority authority;
    private DeviceWithLastUserResponse deviceWithLastUserResponse;
    private DeviceWithLastUserResponse deviceWithLastUserResponse2;
    private List<DeviceResponse> deviceResponseList;

    @MockBean
    private DevicesService devicesService;

    @MockBean
    private EventsService eventsService;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
        deviceRequest = new DeviceRequest(
                DeviceType.PHONE.getDeviceType(),
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra"
        );
        deviceResponse = new DeviceResponse(
                1L,
                DeviceType.PHONE,
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra"
        );
        deviceResponseList = new ArrayList<>();
        deviceResponseList.add(deviceResponse);
        returnEventResponse = new EventResponse(
                1L,
                1L,
                1L,
                ActionType.RETURN,
                LocalDateTime.now()
        );
        takeEventResponse = new EventResponse(
                1L,
                1L,
                1L,
                ActionType.TAKE,
                LocalDateTime.now()
        );

        authority = new Authority(
                1L,
                AuthorityType.USER
        );
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);

        userResponse = new UserResponse(
                2L,
                "Vasiliy",
                "Pupkin",
                "+79923456789",
                "vasiliy.pupkin@mail.com",
                authorities
        );

        deviceWithLastUserResponseList = new ArrayList<>();
        deviceWithLastUserResponse = new DeviceWithLastUserResponse(
                1L,
                DeviceType.PHONE,
                "Apple",
                "iPhone 1337",
                "iOS",
                "abracadabra",
                null
        );
        deviceWithLastUserResponseList.add(deviceWithLastUserResponse);
        deviceWithLastUserResponse2 = new DeviceWithLastUserResponse(
                1L,
                DeviceType.TABLET_PC,
                "Huawei",
                "Df123",
                "Android",
                "abracadabra",
                userResponse
        );
        deviceWithLastUserResponseList.add(deviceWithLastUserResponse2);
    }

    @Test
    public void testAddPhone_WithBadAuthority() throws Exception {
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);

        mvc.perform(post("/api/devices")
                            .header("Authorization", getUserAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isForbidden())
        ;
    }

    @Test
    public void testAddPhone() throws Exception {
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(deviceResponse.getType().getDeviceType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(deviceResponse.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(deviceResponse.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.osType").value(deviceResponse.getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(deviceResponse.getDescription()))
        ;
    }

    @Test
    public void testAddPhoneWithOutDescription() throws Exception {
        DeviceRequest deviceRequest = new DeviceRequest(
                DeviceType.PHONE.getDeviceType(),
                "Apple",
                "iPhone 1337",
                "iOS"
        );
        deviceResponse.setDescription(null);

        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(deviceResponse.getType().getDeviceType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(deviceResponse.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(deviceResponse.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.osType").value(deviceResponse.getOsType()))
        ;
    }

    @Test
    public void testAddBadNullDevice() throws Exception {
        deviceRequest.setType(null);
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("TYPE_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("type"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("type not found in DeviceType enum"))
        ;
    }

    @Test
    public void testAddBadTestDevice() throws Exception {
        deviceRequest.setType("asdqwe");
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("TYPE_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("type"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("type not found in DeviceType enum"))
        ;
    }

    @Test
    public void addDevice_withNullOwner() throws Exception {
        deviceRequest.setOwner(null);

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("OWNER_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("owner"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("owner can not be blank or null"))
        ;
    }

    @Test
    public void addDevice_withEmptyOwner() throws Exception {
        deviceRequest.setOwner("");

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("OWNER_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("owner"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].errorCode").value("OWNER_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].field").value("owner"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].message").isString())
        ;
    }

    @Test
    public void addDevice_withMinusSighInOwner() throws Exception {
        deviceRequest.setOwner("Asd-qwe");
        deviceRequest.setOwner("Asd-qwe");
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);
        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(deviceResponse.getType().getDeviceType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(deviceResponse.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(deviceResponse.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.osType").value(deviceResponse.getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(deviceResponse.getDescription()))
        ;
    }

    @Test
    public void addDevice_withSymbolsInOwner() throws Exception {
        deviceRequest.setOwner("Name!@#$%^&*()+=-_;:\"',.?/");
        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("OWNER_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("owner"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("owner may contain Russian and English letters, whitespaces and minus sign"))
        ;
    }

    @Test
    public void addDevice_withNullModel() throws Exception {
        deviceRequest.setModel(null);

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("MODEL_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("model can not be blank or null"))
        ;
    }

    @Test
    public void addDevice_withEmptyModel() throws Exception {
        deviceRequest.setModel("");

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("MODEL_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].errorCode").value("MODEL_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].field").value("model"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].message").isString())
        ;
    }

    @Test
    public void addDevice_withMinusSighInModel() throws Exception {
        deviceRequest.setModel("Asd-qwe");
        deviceRequest.setModel("Asd-qwe");
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);
        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(deviceResponse.getType().getDeviceType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(deviceResponse.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(deviceResponse.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.osType").value(deviceResponse.getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(deviceResponse.getDescription()))
        ;
    }

    @Test
    public void addDevice_withSymbolsInModel() throws Exception {
        deviceRequest.setModel("Name!@#$%^&*()+=-_;:\"',.?/");
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);
        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(deviceResponse.getType().getDeviceType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(deviceResponse.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(deviceResponse.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.osType").value(deviceResponse.getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(deviceResponse.getDescription()))
        ;
    }

    @Test
    public void addDevice_withNullOsType() throws Exception {
        deviceRequest.setOsType(null);

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("OSTYPE_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("osType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").value("osType can not be blank or null"))
        ;
    }

    @Test
    public void addDevice_withEmptyOsType() throws Exception {
        deviceRequest.setOsType("");

        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].errorCode").value("OSTYPE_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].field").value("osType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[0].message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].errorCode").value("OSTYPE_IS_INVALID"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].field").value("osType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorList[1].message").isString())
        ;
    }

    @Test
    public void addDevice_withMinusSighInOsType() throws Exception {
        deviceRequest.setOsType("Asd-qwe");
        deviceRequest.setOsType("Asd-qwe");
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);
        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(deviceResponse.getType().getDeviceType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(deviceResponse.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(deviceResponse.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.osType").value(deviceResponse.getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(deviceResponse.getDescription()))
        ;
    }

    @Test
    public void addDevice_withSymbolsInOsType() throws Exception {
        deviceRequest.setOsType("Name!@#$%^&*()+=-_;:\"',.?/");
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);
        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(deviceResponse.getType().getDeviceType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(deviceResponse.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(deviceResponse.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.osType").value(deviceResponse.getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(deviceResponse.getDescription()))
        ;
    }

    @Test
    public void addDevice_withSymbolsInDescription() throws Exception {
        deviceRequest.setDescription("Name!@#$%^&*()+=-_;:\"',.?/");
        given(devicesService.addDevice(any(DeviceRequest.class))).willReturn(deviceResponse);
        mvc.perform(post("/api/devices")
                            .header("Authorization", getAdminAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(deviceRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value(deviceResponse.getType().getDeviceType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value(deviceResponse.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(deviceResponse.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.osType").value(deviceResponse.getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(deviceResponse.getDescription()))
        ;
    }

    @Test
    public void deleteDevice_byId() throws Exception {
        doNothing().when(devicesService).deleteDevice(1L);
        mvc.perform(delete("/api/devices/{id}", "1")
                            .header("Authorization", getAdminAuthToken()))
                .andExpect(status().isOk())
        ;
        verify(devicesService, times(1)).deleteDevice(1L);
    }

    @Test
    public void testReturnDevice() throws Exception {
        given(eventsService.returnDevice(anyLong(), anyLong())).willReturn(returnEventResponse);

        mvc.perform(get("/api/devices/{id}/return", "1")
                            .header("Authorization", getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(returnEventResponse.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deviceId").value(returnEventResponse.getDeviceId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actionType").value(returnEventResponse.getActionType().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").isString())
        ;

        verify(eventsService, times(1)).returnDevice(1L, 1L);
    }

    @Test
    public void testTakeDevice() throws Exception {
        given(eventsService.takeDevice(anyLong(), anyLong())).willReturn(takeEventResponse);

        mvc.perform(get("/api/devices/{id}/take", "1")
                            .header("Authorization", getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(takeEventResponse.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deviceId").value(takeEventResponse.getDeviceId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.actionType").value(takeEventResponse.getActionType().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").isString())
        ;

        verify(eventsService, times(1)).takeDevice(1L, 1L);
    }

    @Test
    public void testGetAllDevices() throws Exception {
        given(devicesService.getDevicesWithLastUserWhoTakenDevice("")).willReturn(deviceWithLastUserResponseList);

        mvc.perform(get("/api/devices")
                            .param("search", "")
                            .header("Authorization", getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(deviceWithLastUserResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type").value(deviceWithLastUserResponse.getType().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].owner").value(deviceWithLastUserResponse.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].model").value(deviceWithLastUserResponse.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].osType").value(deviceWithLastUserResponse.getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value(deviceWithLastUserResponse.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(deviceWithLastUserResponse2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].type").value(deviceWithLastUserResponse2.getType().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].owner").value(deviceWithLastUserResponse2.getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].model").value(deviceWithLastUserResponse2.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].osType").value(deviceWithLastUserResponse2.getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].description").value(deviceWithLastUserResponse2.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userResponse.id").value(deviceWithLastUserResponse2.getUserResponse().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userResponse.firstName").value(deviceWithLastUserResponse2.getUserResponse().getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userResponse.lastName").value(deviceWithLastUserResponse2.getUserResponse().getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userResponse.phone").value(deviceWithLastUserResponse2.getUserResponse().getPhone()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userResponse.email").value(deviceWithLastUserResponse2.getUserResponse().getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userResponse.authorities[0].authorityType").value(AuthorityType.USER.getAuthorityType()))
        ;

        verify(devicesService, times(1)).getDevicesWithLastUserWhoTakenDevice(eq(""));
    }

    @Test
    public void testGetMyDevices() throws Exception {
        given(devicesService.getMyDevices(anyLong())).willReturn(deviceResponseList);

        mvc.perform(get("/api/devices/my")
                            .header("Authorization", getUserAuthToken()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(deviceResponseList.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type").value(deviceResponseList.get(0).getType().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].owner").value(deviceResponseList.get(0).getOwner()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].model").value(deviceResponseList.get(0).getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].osType").value(deviceResponseList.get(0).getOsType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].description").value(deviceResponseList.get(0).getDescription()))
        ;

        verify(devicesService, times(1)).getMyDevices(1L);
    }
}
