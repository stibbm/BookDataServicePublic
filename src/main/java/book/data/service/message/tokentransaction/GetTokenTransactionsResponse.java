package book.data.service.message.tokentransaction;

import book.data.service.sqlmodel.transaction.TokenTransaction;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTokenTransactionsResponse implements Serializable {
    private List<TokenTransaction> tokenTransactionList;
}
