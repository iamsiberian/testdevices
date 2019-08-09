package net.thumbtack.testdevices.core.models;

import java.util.Objects;

public class Authority {
    private Long id;
    private AuthorityType authorityType;

    public Authority(final AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    public Authority(final Long id, final AuthorityType authorityType) {
        this.id = id;
        this.authorityType = authorityType;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public AuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(final AuthorityType authorityType) {
        this.authorityType = authorityType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authority)) {
            return false;
        }
        Authority authority = (Authority) o;
        return Objects.equals(getId(), authority.getId()) &&
                getAuthorityType() == authority.getAuthorityType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthorityType());
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", authorityType=" + authorityType +
                '}';
    }
}
