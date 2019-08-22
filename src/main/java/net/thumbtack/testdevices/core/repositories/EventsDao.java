package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.models.Event;

public interface EventsDao {
    Event insert(Event event);

    void deleteAll();

    Event getLastEventByDeviceIdAndUserId(long deviceId, Long userId);
}