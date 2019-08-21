package net.thumbtack.testdevices.core.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.TimeZone;

public class Event {
    private Long id;
    private Long userId;
    private Long deviceId;
    private ActionType actionType;
    private LocalDateTime date;

    public Event(
            final ActionType actionType,
            final Long date
    ) {
        this.actionType = actionType;
        this.date = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), TimeZone.getDefault().toZoneId());
    }

    public Event(
            final ActionType actionType,
            final LocalDateTime date
    ) {
        this.actionType = actionType;
        this.date = date;
    }

    public Event(
            final Long id,
            final Long userId,
            final Long deviceId,
            final ActionType actionType,
            final LocalDateTime date
    ) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.actionType = actionType;
        this.date = date;
    }

    public Event(
            final Long userId,
            final Long deviceId,
            final ActionType actionType,
            final LocalDateTime date
    ) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.actionType = actionType;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(final Long deviceId) {
        this.deviceId = deviceId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(final ActionType actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(getId(), event.getId()) &&
                Objects.equals(getUserId(), event.getUserId()) &&
                Objects.equals(getDeviceId(), event.getDeviceId()) &&
                getActionType() == event.getActionType() &&
                Objects.equals(getDate(), event.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getDeviceId(), getActionType(), getDate());
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", userId=" + userId +
                ", deviceId=" + deviceId +
                ", actionType=" + actionType +
                ", date=" + date +
                '}';
    }
}
