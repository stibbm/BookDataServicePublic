package book.data.service.message.video;

import book.data.service.sqlmodel.video.Video;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVideoResponse implements Serializable {
    private Video video;
}
