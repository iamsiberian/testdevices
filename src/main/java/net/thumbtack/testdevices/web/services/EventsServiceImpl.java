package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.ActionType;
import net.thumbtack.testdevices.core.models.Event;
import net.thumbtack.testdevices.core.repositories.EventsDao;
import net.thumbtack.testdevices.dto.response.EventResponse;
import net.thumbtack.testdevices.exceptions.ErrorCode;
import net.thumbtack.testdevices.exceptions.TestDevicesException;
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
        Event event = eventsDao.getLastEventByDeviceIdAndUserId(deviceId, userId);
        if (event == null) {
            throw new TestDevicesException(ErrorCode.DEVICE_IS_ALREADY_TAKEN_BUT_NOT_BY_YOU);
        } else if (event.getActionType().equals(ActionType.RETURN)) {
            throw new TestDevicesException(ErrorCode.DEVICE_IS_ALREADY_RETURN_BY_YOU);
        }
        return
                eventDtoToModelConverter.getEventResponseFromEvent(
                        eventsDao.insert(new Event(
                                userId, deviceId, ActionType.RETURN, LocalDateTime.now()
                        ))
                );
    }

    @Override
    public EventResponse takeDevice(final long userId, final long deviceId) {
        LOGGER.debug("DevicesServiceImpl takeDevice: {}, by user id : {}", deviceId, userId);
        Event event = eventsDao.getLastEventByDeviceIdAndUserId(deviceId, null);
        if (event != null && event.getActionType().equals(ActionType.TAKE) && event.getUserId().equals(userId)) {
            throw new TestDevicesException(ErrorCode.DEVICE_IS_ALREADY_TAKEN_BY_YOU);
        } else if (event != null && event.getActionType().equals(ActionType.TAKE) && !event.getUserId().equals(userId)) {
            throw new TestDevicesException(ErrorCode.DEVICE_IS_ALREADY_TAKEN_BUT_NOT_BY_YOU);
        }
        return
                eventDtoToModelConverter.getEventResponseFromEvent(
                        eventsDao.insert(new Event(
                                userId, deviceId, ActionType.TAKE, LocalDateTime.now()
                        ))
                );
    }
}
