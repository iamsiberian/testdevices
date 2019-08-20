package net.thumbtack.testdevices.web.converters;

import net.thumbtack.testdevices.core.models.ActionType;
import net.thumbtack.testdevices.core.models.Event;
import net.thumbtack.testdevices.dto.response.EventResponse;

import java.time.LocalDateTime;

public class EventDtoToModelConverter {
    public EventResponse getEventResponseFromEvent(final Event event) {
        return new EventResponse(
                event.getId(),
                event.getUserId(),
                event.getDeviceId(),
                event.getActionType(),
                event.getDate()
        );
    }

    public Event getEventFromDeviceIdUserIdActionType(
            final long deviceId,
            final long userId,
            final ActionType actionType,
            final LocalDateTime date
    ) {
        return new Event(
                userId,
                deviceId,
                ActionType.valueOf(actionType.name()),
                date
        );
    }
}
