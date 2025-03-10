package book.data.service.message.textblock;

import book.data.service.sqlmodel.textblock.TextBlock;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTextBlockResponse implements Serializable {
    private TextBlock textBlock;
}
