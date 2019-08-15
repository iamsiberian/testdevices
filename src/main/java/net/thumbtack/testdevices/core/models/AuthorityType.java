package net.thumbtack.testdevices.core.models;

public enum AuthorityType {
    USER("USER"),
    ADMINISTRATOR("ADMINISTRATOR"),
    ;

    private String authorityType;

    AuthorityType(final String authorityType) {
        this.authorityType = authorityType;
    }

    public String getAuthorityType() {
        return authorityType;
    }
}
