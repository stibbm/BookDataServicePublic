package book.data.service.sqlmodel.transaction;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "TokenTransaction")
@Table(name = "tokenTransaction")
public class TokenTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tokenTransactionNumber;

    private Long checkoutSessionNumber;

    // dedup token?
    private Long tokensAmount;
    private Long createdEpochMilli;
    private String createdBy;

    public Long getTokenTransactionNumber() {return this.tokenTransactionNumber;}
    public Long getTokensAmount() {return this.tokensAmount;}
    public Long getCreatedEpochMilli() {return this.createdEpochMilli;}
    public String getCreatedBy() {return this.createdBy;}
    public Long getCheckoutSessionNumber() {return this.checkoutSessionNumber;}

    public void setTokenTransactionNumber(Long tokenTransactionId) {this.tokenTransactionNumber = tokenTransactionId;}
    public void setTokensAmount(Long tokensDelta) {this.tokensAmount = tokensDelta;}
    public void setCreatedEpochMilli(Long createdEpochTime) {this.createdEpochMilli = createdEpochTime;}
    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}
    public void setCheckoutSessionNumber(Long checkoutSessionNumber) {this.checkoutSessionNumber = checkoutSessionNumber;}
    public TokenTransaction(){}
    public TokenTransaction(
        Long tokenTransactionNumber,
        Long checkoutSessionNumber,
        Long tokensAmount,
        Long createdEpochMilli,
        String createdBy
    ) {
        this.tokenTransactionNumber = tokenTransactionNumber;
        this.checkoutSessionNumber = checkoutSessionNumber;
        this.tokensAmount = tokensAmount;
        this.createdEpochMilli = createdEpochMilli;
        this.createdBy = createdBy;
    }

    public static TokenTransaction buildTokenTransactionWithoutId(
        Long checkoutSessionNumber,
        Long tokensAmount,
        Long createdEpochMilli,
        String createdBy
    ) {
        TokenTransaction tokenTransaction = new TokenTransaction();
        tokenTransaction.setCheckoutSessionNumber(checkoutSessionNumber);
        tokenTransaction.setTokensAmount(tokensAmount);
        tokenTransaction.setCreatedEpochMilli(createdEpochMilli);
        tokenTransaction.setCreatedBy(createdBy);
        return tokenTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TokenTransaction tokenTransaction = (TokenTransaction)o;
        return tokenTransaction.getTokenTransactionNumber().equals(tokenTransactionNumber)
            && tokenTransaction.getTokensAmount().equals(tokensAmount)
            && tokenTransaction.getCreatedEpochMilli().equals(createdEpochMilli)
            && tokenTransaction.getCreatedBy().equals(createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenTransactionNumber);
    }

    @Override
    public String toString() {
        return "TokenTransaction{" +
            "tokenTransactionId=" + tokenTransactionNumber +
            ", tokensDelta=" + tokensAmount +
            ", createdEpochTime=" + createdEpochMilli +
            ", createdBy='" + createdBy + '\'' +
            '}';
    }
}
