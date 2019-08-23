package net.thumbtack.testdevices.web.services;

import net.thumbtack.testdevices.core.models.ActionType;
import net.thumbtack.testdevices.core.models.Event;
import net.thumbtack.testdevices.core.repositories.EventsDao;
import net.thumbtack.testdevices.dto.response.EventResponse;
import net.thumbtack.testdevices.exceptions.ErrorCode;
import net.thumbtack.testdevices.exceptions.TestDevicesException;
import net.thumbtack.testdevices.web.converters.EventDtoToModelConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EventsServiceTest {
    private EventsDao eventsDao;
    private EventDtoToModelConverter eventDtoToModelConverter;
    private EventsService eventsService;

    private Event takeEvent;
    private Event returnEvent;
    private EventResponse takeEventResponse;
    private EventResponse returnEventResponse;
    private LocalDateTime takeDate;
    private LocalDateTime returnDate;
    private Event lastEvent;

    @Before
    public void setup() {
        eventsDao = mock(EventsDao.class);
        eventDtoToModelConverter = mock(EventDtoToModelConverter.class);
        eventsService = new EventsServiceImpl(eventsDao, eventDtoToModelConverter);
        takeDate = LocalDateTime.now().minusHours(1);
        returnDate = LocalDateTime.now();
        takeEvent = new Event(
                1L,
                1L,
                1L,
                ActionType.TAKE,
                takeDate
        );
        takeEventResponse = new EventResponse(
                1L,
                1L,
                1L,
                ActionType.TAKE,
                takeDate
        );

        returnEvent = new Event(
                1L,
                1L,
                1L,
                ActionType.RETURN,
                returnDate
        );
        returnEventResponse = new EventResponse(
                1L,
                1L,
                1L,
                ActionType.RETURN,
                returnDate
        );
    }

    @Test
    public void testTakeDevice_withNullLastEvent() {
        when(eventsDao.getLastEventByDeviceIdAndUserId(1L, null)).thenReturn(null);
        when(eventsDao.insert(any(Event.class))).thenReturn(takeEvent);
        when(eventDtoToModelConverter.getEventResponseFromEvent(any(Event.class))).thenReturn(takeEventResponse);

        EventResponse eventResponse = eventsService.takeDevice(1L, 1L);
        Assert.assertNotNull(eventResponse);

        verify(eventsDao, times(1)).getLastEventByDeviceIdAndUserId(1L, null);
        verify(eventsDao, times(1)).insert(any(Event.class));
        verify(eventDtoToModelConverter, times(1)).getEventResponseFromEvent(any(Event.class));
    }

    @Test
    public void testTakeDevice_withNotNullLastEventAndTakeActionTypeAndThisUserId() {
        lastEvent = new Event(
            1L, 1L, ActionType.TAKE, takeDate.minusHours(1)
        );
        when(eventsDao.getLastEventByDeviceIdAndUserId(1L, null)).thenReturn(lastEvent);

        TestDevicesException thrown = assertThrows(TestDevicesException.class, () -> eventsService.takeDevice(1L, 1L));

        List<ErrorCode> list = thrown.getErrorCode();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(ErrorCode.DEVICE_IS_ALREADY_TAKEN_BY_YOU, list.get(0));

        verify(eventsDao, times(1)).getLastEventByDeviceIdAndUserId(1L, null);

    }

    @Test
    public void testTakeDevice_withNotNullLastEventAndTakeActionType() {
        lastEvent = new Event(
                2L, 1L, ActionType.TAKE, takeDate.minusHours(1)
        );
        when(eventsDao.getLastEventByDeviceIdAndUserId(1L, null)).thenReturn(lastEvent);

        TestDevicesException thrown = assertThrows(TestDevicesException.class, () -> eventsService.takeDevice(1L, 1L));

        List<ErrorCode> list = thrown.getErrorCode();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(ErrorCode.DEVICE_IS_ALREADY_TAKEN_BUT_NOT_BY_YOU, list.get(0));

        verify(eventsDao, times(1)).getLastEventByDeviceIdAndUserId(1L, null);
    }

    @Test
    public void testReturnDeviceWithNotNullLastEventAndTakeActionType() {
        lastEvent = new Event(
                1L, 1L, ActionType.TAKE, takeDate.minusHours(1)
        );
        when(eventsDao.getLastEventByDeviceIdAndUserId(1L, 1L)).thenReturn(lastEvent);
        when(eventsDao.insert(any(Event.class))).thenReturn(returnEvent);
        when(eventDtoToModelConverter.getEventResponseFromEvent(any(Event.class))).thenReturn(returnEventResponse);

        EventResponse eventResponse = eventsService.returnDevice(1L, 1L);
        Assert.assertNotNull(eventResponse);

        verify(eventsDao, times(1)).getLastEventByDeviceIdAndUserId(1L, 1L);
        verify(eventsDao, times(1)).insert(any(Event.class));
        verify(eventDtoToModelConverter, times(1)).getEventResponseFromEvent(any(Event.class));
    }

    @Test
    public void testReturnDeviceWithNullLastEvent() {
        when(eventsDao.getLastEventByDeviceIdAndUserId(1L, 1L)).thenReturn(null);

        TestDevicesException thrown = assertThrows(TestDevicesException.class, () -> eventsService.returnDevice(1L, 1L));

        List<ErrorCode> list = thrown.getErrorCode();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(ErrorCode.DEVICE_IS_ALREADY_TAKEN_BUT_NOT_BY_YOU, list.get(0));

        verify(eventsDao, times(1)).getLastEventByDeviceIdAndUserId(1L, 1L);
    }

    @Test
    public void testReturnDeviceWithNotNullLastEventAndRetuenActionType() {
        lastEvent = new Event(
                1L, 1L, ActionType.RETURN, takeDate.minusHours(1)
        );
        when(eventsDao.getLastEventByDeviceIdAndUserId(1L, 1L)).thenReturn(lastEvent);

        TestDevicesException thrown = assertThrows(TestDevicesException.class, () -> eventsService.returnDevice(1L, 1L));

        List<ErrorCode> list = thrown.getErrorCode();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(ErrorCode.DEVICE_IS_ALREADY_RETURN_BY_YOU, list.get(0));

        verify(eventsDao, times(1)).getLastEventByDeviceIdAndUserId(1L, 1L);
    }
}
