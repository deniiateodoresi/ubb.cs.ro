package model;

import java.time.LocalDate;

public class BankCard {
    private String number;
    private LocalDate expirationDate;
    private String validationCode;

    public BankCard(String number, LocalDate expirationDate, String validationCode) {
        this.number = number;
        this.expirationDate = expirationDate;
        this.validationCode = validationCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }
}
