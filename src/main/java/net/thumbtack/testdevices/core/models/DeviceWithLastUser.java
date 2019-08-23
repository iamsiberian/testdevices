package net.thumbtack.testdevices.core.models;

import java.util.Objects;

public class DeviceWithLastUser {
    private long id;
    private DeviceType type;
    private String owner;
    private String model;
    private String osType;
    private String description;
    private User user;

    public DeviceWithLastUser(
            final long id,
            final DeviceType type,
            final String owner,
            final String model,
            final String osType,
            final String description
    ) {
        this.id = id;
        this.type = type;
        this.owner = owner;
        this.model = model;
        this.osType = osType;
        this.description = description;
    }

    public DeviceWithLastUser(
            final long id,
            final DeviceType type,
            final String owner,
            final String model,
            final String osType,
            final String description,
            final User user
    ) {
        this.id = id;
        this.type = type;
        this.owner = owner;
        this.model = model;
        this.osType = osType;
        this.description = description;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceWithLastUser)) {
            return false;
        }
        DeviceWithLastUser that = (DeviceWithLastUser) o;
        return getId() == that.getId() &&
                getType() == that.getType() &&
                Objects.equals(getOwner(), that.getOwner()) &&
                Objects.equals(getModel(), that.getModel()) &&
                Objects.equals(getOsType(), that.getOsType()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getOwner(), getModel(), getOsType(), getDescription(), getUser());
    }

    @Override
    public String toString() {
        return "DeviceWithLastUser{" +
                "id=" + id +
                ", type=" + type +
                ", owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                ", osType='" + osType + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}
