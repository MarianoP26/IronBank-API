package com.finalproject.ironhack.models.Agents;

import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.models.Agents.embedded.Address;
import com.finalproject.ironhack.utils.TimeHelperClass;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class AccountHolder extends Agent{

    @NotNull
    private Date dateOfBirth;

    @NotNull
    @Embedded
    private Address primaryAddress;

    @AttributeOverrides({
            @AttributeOverride(name="address",column=@Column(name="mailing_address")),
    })

    @Embedded
    private Address mailingAddress;

    @OneToOne
    private Account account;

    @OneToOne
    private Account secondaryAccount;

    public AccountHolder() {
        super();
    }

    public AccountHolder (String name, String password, Date dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(name, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public int getAge(){
        return TimeHelperClass.getDiffYears(dateOfBirth, new Date());
    }


}
