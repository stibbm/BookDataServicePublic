package book.data.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import book.data.service.constants.videodetailstemplates.Series;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayListData implements Serializable {
    private String bookName;
    private String playListId;
    private Series series;
    private byte[] thumbnailBytes;
    private String thumbnailFileType;
}
