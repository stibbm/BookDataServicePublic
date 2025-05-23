package book.data.service.message.account;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import book.data.service.sqlmodel.account.Account;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountResponse implements Serializable {
    private Account account;
}
