package book.data.service.sqlmodel.account;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Account")
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"emailAddress"}
        ),
        @UniqueConstraint(
                columnNames = {"stripeCustomerId"}
        )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountNumber;
    private String stripeCustomerId;
    @Column(nullable = false)
    private String emailAddress;
    private String fullName;
    private Boolean emailAddressVerified;
    private Long createdEpochMilli;

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) {
            return true;
        }
        if (o==null || this.getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account)o;
        return Objects.equals(accountNumber, account.getAccountNumber())
                && Objects.equals(stripeCustomerId, account.getStripeCustomerId())
                && Objects.equals(emailAddress, account.getEmailAddress())
                && Objects.equals(emailAddressVerified, account.getEmailAddressVerified())
                && Objects.equals(createdEpochMilli, account.getCreatedEpochMilli());
    }

    public static Account createAccountWithoutId(
            String stripeCustomerId,
            String emailAddress,
            String fullName,
            Boolean emailAddressVerified,
            Long createdEpochMilli
    ) {
        Account account = new Account();
        account.setStripeCustomerId(stripeCustomerId);
        account.setEmailAddress(emailAddress);
        account.setFullName(fullName);
        account.setEmailAddressVerified(emailAddressVerified);
        account.setCreatedEpochMilli(createdEpochMilli);
        return account;
    }
}
