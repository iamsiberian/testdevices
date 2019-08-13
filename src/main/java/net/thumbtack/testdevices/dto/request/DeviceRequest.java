package net.thumbtack.testdevices.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.thumbtack.testdevices.core.models.DeviceType;

public class DeviceRequest {
    private DeviceType type;
    private String owner;
    private String model;
    private String osType;
    private String description;

    @JsonCreator
    public DeviceRequest(
            final @JsonProperty("type") DeviceType type,
            final @JsonProperty("owner") String owner,
            final @JsonProperty("model") String model,
            final @JsonProperty("osType") String osType,
            final @JsonProperty("description") String description
    ) {
        this.type = type;
        this.owner = owner;
        this.model = model;
        this.osType = osType;
        this.description = description;
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
    public String toString() {
        return "DeviceRequest{" +
                "type=" + type +
                ", owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                ", osType='" + osType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
