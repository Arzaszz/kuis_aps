package entity;

public enum StatusPemesanan {
    CONFIRMED("confirmed"),
    BOOKED("booked"),
    WAITING_FOR_PAYMENT("waiting for the payment"),
    CANCELLED("cancelled");

    private final String label;

    StatusPemesanan(String label) { this.label = label; }

    public String getLabel() { return label; }

    public static StatusPemesanan fromLabel(String label) {
        if (label != null) {
            for (StatusPemesanan status : values()) {
                if (status.label.equalsIgnoreCase(label.trim())
                        || status.name().equalsIgnoreCase(label.trim())) {
                    return status;
                }
            }
        }
        throw new IllegalArgumentException("Status pemesanan tidak dikenal: " + label);
    }
}
