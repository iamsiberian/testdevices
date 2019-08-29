package net.thumbtack.testdevices.web.controllers;

import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.web.security.JwtTokenService;
import net.thumbtack.testdevices.web.services.DevicesService;
import net.thumbtack.testdevices.web.services.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api"})
public class DevicesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevicesController.class);
    private DevicesService devicesService;
    private EventsService eventsService;
    private JwtTokenService jwtTokenService;

    public DevicesController(
            final DevicesService devicesService,
            final EventsService eventsService,
            final JwtTokenService jwtTokenService
    ) {
        this.devicesService = devicesService;
        this.eventsService = eventsService;
        this.jwtTokenService = jwtTokenService;
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @PostMapping(path = "/devices", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addDevice(final @Valid @RequestBody DeviceRequest request) {
        LOGGER.debug("DevicesController addDevice {}", request);
        return
                ResponseEntity
                        .ok()
                        .body(devicesService.addDevice(request));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR')")
    @DeleteMapping(path = "/devices/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteDevice(final @PathVariable("id") long deviceId) {
        LOGGER.debug("DevicesController deleteDevice {}", deviceId);
        devicesService.deleteDevice(deviceId);
        return
                ResponseEntity
                        .ok()
                        .build();
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'USER')")
    @GetMapping(path = "/devices/{id}/return", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity returnDevice(final @RequestHeader("Authorization") String authHeader, final @PathVariable("id") long deviceId) {
        LOGGER.debug("DevicesController returnDevice {}, from requestHeader: {}", deviceId, authHeader);
        long userId = jwtTokenService.getUserIdFromAuthHeader(authHeader);
        return
                ResponseEntity
                        .ok()
                        .body(eventsService.returnDevice(userId, deviceId));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'USER')")
    @GetMapping(path = "/devices/{id}/take", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity takeDevice(final @RequestHeader("Authorization") String authHeader, final @PathVariable("id") long deviceId) {
        LOGGER.debug("DevicesController takeDevice {}", deviceId);
        long userId = jwtTokenService.getUserIdFromAuthHeader(authHeader);
        return
                ResponseEntity
                        .ok()
                        .body(eventsService.takeDevice(userId, deviceId));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'USER')")
    @GetMapping(path = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllDevices(final @RequestParam(value = "search", defaultValue = "", required = false) String search) {
        LOGGER.debug("DevicesController getAllDevices");
        return
                ResponseEntity
                        .ok()
                        .body(devicesService.getDevicesWithLastUserWhoTakenDevice(search));
    }

    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'USER')")
    @GetMapping(path = "/devices/my", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyDevices(final @RequestHeader("Authorization") String authHeader) {
        LOGGER.debug("DevicesController getMyDevices, from requestHeader: {}", authHeader);
        long userId = jwtTokenService.getUserIdFromAuthHeader(authHeader);
        return
                ResponseEntity
                        .ok()
                        .body(devicesService.getMyDevices(userId));
    }
}
