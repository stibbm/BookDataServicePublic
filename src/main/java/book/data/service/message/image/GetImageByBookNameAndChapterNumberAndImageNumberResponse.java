package book.data.service.message.image;

import book.data.service.sqlmodel.image.Image;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetImageByBookNameAndChapterNumberAndImageNumberResponse implements Serializable {
    private Image image;
}
