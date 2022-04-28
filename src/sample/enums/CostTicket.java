package sample.enums;

public enum CostTicket {
    ANNUAL("12000"),
    MONTHLY("1300"),
    UNLIMITED("20000"),
    WEEK("555");
    private final String claim;

    CostTicket(String claim) {
        this.claim = claim;
    }

    public String getClaim() {
        return claim;
    }
}
