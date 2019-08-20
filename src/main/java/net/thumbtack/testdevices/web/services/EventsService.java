package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.dto.response.EventResponse;

public interface EventsService {
    EventResponse returnDevice(long userId, long deviceId);

    EventResponse giveDevice(long userId, long deviceId);
}
