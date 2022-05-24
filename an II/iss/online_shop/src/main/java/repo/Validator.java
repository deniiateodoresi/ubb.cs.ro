package repo;

import model.Address;
import model.BankCard;

import java.time.LocalDate;
import java.util.Objects;

public class Validator {

    public Boolean validateAddress(Address address){
        if(Objects.equals(address.getCounty(), ""))
            return false;
        if(Objects.equals(address.getCity(), ""))
            return false;
        if(Objects.equals(address.getStreet(), ""))
            return false;
        return true;
    }

    public Boolean validateCard(BankCard card){
        if(card.getValidationCode().length() != 3)
            return false;
        if(card.getExpirationDate().isBefore(LocalDate.now()))
            return false;
        if(card.getNumber().length() != 16)
            return false;
        return true;
    }
}
