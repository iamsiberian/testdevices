package net.thumbtack.testdevices.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.thumbtack.testdevices.core.models.DeviceType;
import net.thumbtack.testdevices.validators.EnumValidator;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class DeviceRequest {
    private static final String EMPTY_STRING = "";

    @EnumValidator(enumClazz = DeviceType.class, message = "type not found in DeviceType enum")
    private String type;
    @NotBlank(message = "owner can not be blank or null")
    @Pattern(
            regexp = "[а-яА-Яa-zA-Z\\s\\-]+",
            message = "owner may contain Russian and English letters, whitespaces and minus sign"
    )
    private String owner;
    @NotBlank(message = "model can not be blank or null")
    @Pattern(
            regexp = "[a-zA-Zа-яА-Я0-9\\W_]+",
            message = "model may contain English and Russian letters, numbers and all symbols"
    )
    private String model;
    @NotBlank(message = "osType can not be blank or null")
    @Pattern(
            regexp = "[a-zA-Zа-яА-Я0-9\\W_]+",
            message = "osType may contain English and Russian letters, numbers and all symbols"
    )
    private String osType;

    private String description;

    @JsonCreator
    public DeviceRequest(
            final @JsonProperty("type") String type,
            final @JsonProperty("owner") String owner,
            final @JsonProperty("model") String model,
            final @JsonProperty("osType") String osType,
            final @JsonProperty("description") String description
    ) {
        this.type = type;
        this.owner = owner;
        this.model = model;
        this.osType = osType;
        this.description = StringUtils.isEmpty(description) ? EMPTY_STRING : description;
    }

    public DeviceRequest(
            final @JsonProperty("type") String type,
            final @JsonProperty("owner") String owner,
            final @JsonProperty("model") String model,
            final @JsonProperty("osType") String osType
    ) {
        this.type = type;
        this.owner = owner;
        this.model = model;
        this.osType = osType;
        this.description = EMPTY_STRING;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
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
