package net.thumbtack.testdevices.dto.response;

import net.thumbtack.testdevices.core.models.ActionType;

import java.time.LocalDateTime;
import java.util.Objects;

public class EventResponse {
    private Long id;
    private Long userId;
    private Long deviceId;
    private ActionType actionType;
    private LocalDateTime date;

    public EventResponse(
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
        if (!(o instanceof EventResponse)) {
            return false;
        }
        EventResponse that = (EventResponse) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getDeviceId(), that.getDeviceId()) &&
                getActionType() == that.getActionType() &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getDeviceId(), getActionType(), getDate());
    }

    @Override
    public String toString() {
        return "EventResponse{" +
                "id=" + id +
                ", userId=" + userId +
                ", deviceId=" + deviceId +
                ", actionType=" + actionType +
                ", date=" + date +
                '}';
    }
}
