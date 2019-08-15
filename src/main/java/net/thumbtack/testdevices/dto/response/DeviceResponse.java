package net.thumbtack.testdevices.dto.response;

import net.thumbtack.testdevices.core.models.DeviceType;

import java.util.Objects;

public class DeviceResponse {
    private Long id;
    private DeviceType type;
    private String owner;
    private String model;
    private String osType;
    private String description;

    public DeviceResponse(
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
        if (!(o instanceof DeviceResponse)) {
            return false;
        }
        DeviceResponse that = (DeviceResponse) o;
        return Objects.equals(getId(), that.getId()) &&
                getType() == that.getType() &&
                Objects.equals(getOwner(), that.getOwner()) &&
                Objects.equals(getModel(), that.getModel()) &&
                Objects.equals(getOsType(), that.getOsType()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getOwner(), getModel(), getOsType(), getDescription());
    }

    @Override
    public String toString() {
        return "DeviceResponse{" +
                "id=" + id +
                ", type=" + type +
                ", owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                ", osType='" + osType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
