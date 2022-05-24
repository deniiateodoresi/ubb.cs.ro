package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bank_card")
public class BankCard {
    private String number;
    private LocalDate expirationDate;
    private String validationCode;

    public BankCard(String number, LocalDate expirationDate, String validationCode) {
        this.number = number;
        this.expirationDate = expirationDate;
        this.validationCode = validationCode;
    }

    public BankCard() {

    }

    @Id
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_date")
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Column(name = "validation_code")
    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }
}
