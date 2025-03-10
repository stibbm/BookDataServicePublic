package book.data.service.message.audio;

import book.data.service.sqlmodel.audio.Audio;
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
public class GetAudiosByBookNameAndChapterNumberResponse implements Serializable {
    private List<Audio> audioList;
}
