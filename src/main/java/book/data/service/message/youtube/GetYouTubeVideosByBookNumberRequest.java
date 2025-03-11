package book.data.service.message.youtube;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetYouTubeVideosByBookNumberRequest implements Serializable {
    private String bookNumber;
}
