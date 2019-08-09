package net.thumbtack.testdevices.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRequest {
    @NotBlank(message = "firstName can not be blank or null")
    @Pattern(
            regexp = "[а-яА-Яa-zA-Z\\s\\-]+",
            message = "firstName may contain Russian and English letters, whitespaces and minus sign"
    )
    private String firstName;
    @NotBlank(message = "lastName can not be blank or null")
    @Pattern(
            regexp = "[а-яА-Яa-zA-Z\\s\\-]+",
            message = "lastName may contain Russian and English letters, whitespaces and minus sign"
    )
    private String lastName;
    @NotBlank(message = "phone can not be blank or null")
    @Pattern(
            regexp = "(\\+7|8)[-]?\\d{3}[-]?\\d{3}[-]?\\d{2}[-]?\\d{2}",
            message = "phone is incorrect. Example: +7-913-123-45-67 or 89131234567"
    )
    private String phone;
    @Email
    private String email;
    @NotBlank(message = "password can not be blank or null")
    @Pattern(
            regexp = "[a-zA-Zа-яА-Я0-9\\W_]+",
            message = "password may contain English and Russian letters, numbers and all symbols"
    )
    private String password;

    @JsonCreator
    public UserRequest(
            final @JsonProperty("firstName") String firstName,
            final @JsonProperty("lastName") String lastName,
            final @JsonProperty("phone") String phone,
            final @JsonProperty("email") String email,
            final @JsonProperty("password") String password
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
