package net.thumbtack.testdevices.core.models;

public enum DeviceType {
    TABLET_PC("TABLET_PC"),
    PHONE("PHONE"),
    ;

    private String deviceType;

    DeviceType(final String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceType() {
        return deviceType;
    }
}
