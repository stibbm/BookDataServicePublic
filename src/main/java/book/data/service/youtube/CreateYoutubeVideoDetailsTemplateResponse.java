package book.data.service.youtube;

import book.data.service.sqlmodel.youtubevideo.YoutubeVideoDetailsTemplate;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateYoutubeVideoDetailsTemplateResponse implements Serializable {
    private YoutubeVideoDetailsTemplate youtubeVideoDetailsTemplate;
}
