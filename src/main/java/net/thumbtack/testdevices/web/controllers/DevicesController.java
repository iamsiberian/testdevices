package net.thumbtack.testdevices.web.controllers;

import net.thumbtack.testdevices.dto.request.DeviceRequest;
import net.thumbtack.testdevices.web.services.DevicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api"})
public class DevicesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevicesController.class);
    private DevicesService devicesService;

    public DevicesController(final DevicesService devicesService) {
        this.devicesService = devicesService;
    }

    @PostMapping(path = "/devices", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addDevice(final @Valid @RequestBody DeviceRequest request) {
        LOGGER.debug("DevicesController addDevice {}", request);
        return
                ResponseEntity
                        .ok()
                        .body(devicesService.addDevice(request));
    }
}
