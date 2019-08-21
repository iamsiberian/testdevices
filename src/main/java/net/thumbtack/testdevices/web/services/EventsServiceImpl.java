package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.ActionType;
import net.thumbtack.testdevices.core.models.Event;
import net.thumbtack.testdevices.core.repositories.EventsDao;
import net.thumbtack.testdevices.dto.response.EventResponse;
import net.thumbtack.testdevices.web.converters.EventDtoToModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class EventsServiceImpl implements EventsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsServiceImpl.class);
    private EventsDao eventsDao;
    private EventDtoToModelConverter eventDtoToModelConverter;

    public EventsServiceImpl(
            final EventsDao eventsDao,
            final EventDtoToModelConverter eventDtoToModelConverter
    ) {
        this.eventsDao = eventsDao;
        this.eventDtoToModelConverter = eventDtoToModelConverter;
    }

    @Override
    public EventResponse returnDevice(final long userId, final long deviceId) {
        LOGGER.debug("DevicesServiceImpl returnDevice: {}, by user id : {}", deviceId, userId);
        Event event = eventsDao.insert(eventDtoToModelConverter.getEventFromDeviceIdUserIdActionType(userId, deviceId, ActionType.RETURN, LocalDateTime.now()));
        return eventDtoToModelConverter.getEventResponseFromEvent(event);
    }

    @Override
    public EventResponse takeDevice(final long userId, final long deviceId) {
        LOGGER.debug("DevicesServiceImpl takeDevice: {}, by user id : {}", deviceId, userId);
        Event event = eventsDao.insert(eventDtoToModelConverter.getEventFromDeviceIdUserIdActionType(userId, deviceId, ActionType.TAKE, LocalDateTime.now()));
        return eventDtoToModelConverter.getEventResponseFromEvent(event);
    }
}
