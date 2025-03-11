package book.data.service.repository;

import book.data.service.sqlmodel.account.Account;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class AccountRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Account findAccountByEmailAddress(String emailAddress) {
        String queryString = "SELECT a FROM Account a "
            + "WHERE a.emailAddress=:emailAddress";
        TypedQuery<Account> query = entityManager.createQuery(queryString, Account.class);
        log.info("query = " + query);
        Account account = query.setParameter("emailAddress", emailAddress)
            .getSingleResult();
        return account;
    }

    public Account findAccountByStripeCustomerId(String customerId) {
        String queryString = "SELECT a FROM Account a WHERE a.stripeCustomerId=:customerId";
        TypedQuery<Account> query = entityManager.createQuery(queryString, Account.class);
        Account account = query.setParameter("customerId", customerId)
            .getSingleResult();
        return account;
    }

    public boolean doesAccountExistByEmailAddress(String emailAddress) {
        try {
            Account account = findAccountByEmailAddress(emailAddress);
            if (account == null) {
                return false;
            }
            return true;
        }
        catch (NoResultException exception) {
            return false;
        }
        catch (NonUniqueResultException exception) {
            log.warn("Found two accounts with the same emailAddress, this should not be possible");
            return true;
        }
    }

    @Transactional
    public void saveAccount(Account account) {
        log.info("save account: " + account);
        entityManager.persist(account);
    }
}
