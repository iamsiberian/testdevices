package net.thumbtack.testdevices.dto.response;

import net.thumbtack.testdevices.core.models.Authority;

import java.util.Objects;
import java.util.Set;

public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Set<Authority> authorities;

    public UserResponse(
            final Long id,
            final String firstName,
            final String lastName,
            final String phone,
            final String email,
            final Set<Authority> authorities
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(final Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserResponse)) {
            return false;
        }
        UserResponse that = (UserResponse) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getAuthorities(), that.getAuthorities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhone(), getEmail(), getAuthorities());
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
