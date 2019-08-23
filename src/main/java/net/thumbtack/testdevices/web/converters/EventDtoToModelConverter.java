package net.thumbtack.testdevices.web.converters;

import net.thumbtack.testdevices.core.models.Event;
import net.thumbtack.testdevices.dto.response.EventResponse;

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
}
