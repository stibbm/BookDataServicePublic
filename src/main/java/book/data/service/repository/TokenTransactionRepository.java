package book.data.service.repository;

import book.data.service.sqlmodel.transaction.TokenTransaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class TokenTransactionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TokenTransaction> findTokenTransactions(String createdBy) {
        String sqlQuery = "SELECT tt FROM TokenTransaction tt WHERE tt.createdBy=:createdBy";
        TypedQuery<TokenTransaction> query = entityManager.createQuery(sqlQuery,
            TokenTransaction.class);
        List<TokenTransaction> tokenTransactionList = query
            .setParameter("createdBy", createdBy)
            .getResultList();
        return tokenTransactionList;
    }

    public TokenTransaction findTokenTransactionByTokenTransactionNumber(
        Long tokenTransactionNumber, String createdBy) {
        String sqlQuery =
            "SELECT tt FROM TokenTransaction tt WHERE tt.tokenTransactionNumber=:tokenTransactionNumber "
                + "AND tt.createdBy=:createdBy";
        TypedQuery<TokenTransaction> query = entityManager.createQuery(sqlQuery,
            TokenTransaction.class);
        TokenTransaction tokenTransaction = query
            .setParameter("tokenTransactionNumber", tokenTransactionNumber)
            .setParameter("createdBy", createdBy)
            .getSingleResult();
        return tokenTransaction;
    }

    public boolean doesTokenTransactionExist(Long tokenTransactionNumber, String createdBy) {
        try {
            TokenTransaction transactionToken = findTokenTransactionByTokenTransactionNumber(
                tokenTransactionNumber, createdBy);
            return transactionToken != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Transactional
    public void saveTokenTransaction(TokenTransaction tokenTransaction) {
        entityManager.persist(tokenTransaction);
    }
}
