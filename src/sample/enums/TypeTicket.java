package sample.enums;

public enum TypeTicket {
    ANNUAL("Годовой"),
    MONTHLY("Месяц"),
    UNLIMITED("Безлимитный"),
    WEEK("Неделя");
    private final String claim;

    TypeTicket(String claim) {
        this.claim = claim;
    }

    public String getClaim() {
        return claim;
    }
}
