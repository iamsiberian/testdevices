package net.thumbtack.testdevices.core.models;

import java.util.Objects;

public class Device {
    private Long id;
    private DeviceType type;
    private String owner;
    private String model;
    private String osType;
    private String description;

    public Device(
            final Long id,
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

    public Device(
            final DeviceType type,
            final String owner,
            final String model,
            final String osType,
            final String description
    ) {
        this.type = type;
        this.owner = owner;
        this.model = model;
        this.osType = osType;
        this.description = description;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        Device device = (Device) o;
        return Objects.equals(getId(), device.getId()) &&
                getType() == device.getType() &&
                Objects.equals(getOwner(), device.getOwner()) &&
                Objects.equals(getModel(), device.getModel()) &&
                Objects.equals(getOsType(), device.getOsType()) &&
                Objects.equals(getDescription(), device.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getOwner(), getModel(), getOsType(), getDescription());
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", type=" + type +
                ", owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                ", osType='" + osType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
