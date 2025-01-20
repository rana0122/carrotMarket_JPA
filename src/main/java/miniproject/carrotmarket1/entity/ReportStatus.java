package miniproject.carrotmarket1.entity;

public enum ReportStatus {
    RESOLVED("Resolved"),
    PENDING("Pending");

    private final String displayName;

    ReportStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

