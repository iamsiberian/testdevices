package net.thumbtack.testdevices.web.converters;

import net.thumbtack.testdevices.core.models.ActionType;
import net.thumbtack.testdevices.core.models.Event;
import net.thumbtack.testdevices.dto.response.EventResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class EventDtoToModelConverterTest {
    private Event event;
    private EventResponse eventResponse;
    private LocalDateTime date;
    private EventDtoToModelConverter eventDtoToModelConverter;

    @Before
    public void setup() {
        eventDtoToModelConverter = new EventDtoToModelConverter();
        date = LocalDateTime.now();

        event = new Event(
                1L,
                1L,
                1L,
                ActionType.TAKE,
                date
        );
        eventResponse = new EventResponse(
                1L,
                1L,
                1L,
                ActionType.TAKE,
                date
        );
    }

    @Test
    public void testGetEventResponseFromEvent() {
        Assert.assertEquals(eventResponse, eventDtoToModelConverter.getEventResponseFromEvent(event));
    }
}
