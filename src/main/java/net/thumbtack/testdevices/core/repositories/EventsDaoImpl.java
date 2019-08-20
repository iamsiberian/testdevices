package net.thumbtack.testdevices.core.repositories;

import net.thumbtack.testdevices.core.mappers.EventMapper;
import net.thumbtack.testdevices.core.models.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public class EventsDaoImpl implements EventsDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsDaoImpl.class);
    private EventMapper eventMapper;

    public EventsDaoImpl(final EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public Event insert(final Event event) {
        LOGGER.debug("EventsDaoImpl insert event: {}", event);
        eventMapper.insert(event);
        return event;
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public void deleteAll() {
        LOGGER.debug("EventsDaoImpl deleteAll events");
        eventMapper.deleteAll();
    }
}
