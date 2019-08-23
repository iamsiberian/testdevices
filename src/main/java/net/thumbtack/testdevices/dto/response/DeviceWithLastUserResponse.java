package net.thumbtack.testdevices.dto.response;

import net.thumbtack.testdevices.core.models.DeviceType;

import java.util.Objects;

public class DeviceWithLastUserResponse {
    private Long id;
    private DeviceType type;
    private String owner;
    private String model;
    private String osType;
    private String description;
    private UserResponse userResponse;

    public DeviceWithLastUserResponse(
            final Long id,
            final DeviceType type,
            final String owner,
            final String model,
            final String osType,
            final String description,
            final UserResponse userResponse
    ) {
        this.id = id;
        this.type = type;
        this.owner = owner;
        this.model = model;
        this.osType = osType;
        this.description = description;
        this.userResponse = userResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(final DeviceType type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(final String osType) {
        this.osType = osType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(final UserResponse userResponse) {
        this.userResponse = userResponse;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceWithLastUserResponse)) {
            return false;
        }
        DeviceWithLastUserResponse that = (DeviceWithLastUserResponse) o;
        return Objects.equals(getId(), that.getId()) &&
                getType() == that.getType() &&
                Objects.equals(getOwner(), that.getOwner()) &&
                Objects.equals(getModel(), that.getModel()) &&
                Objects.equals(getOsType(), that.getOsType()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getUserResponse(), that.getUserResponse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getOwner(), getModel(), getOsType(), getDescription(), getUserResponse());
    }

    @Override
    public String toString() {
        return "DeviceWithLastUserResponse{" +
                "id=" + id +
                ", type=" + type +
                ", owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                ", osType='" + osType + '\'' +
                ", description='" + description + '\'' +
                ", userResponse=" + userResponse +
                '}';
    }
}
