package book.data.service.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class YoutubeVideoDetailsFactory {

    public String buildTitleOrDescription(String template, Long startChapter, Long endChapter) {
        String title = template;
        title = title.replace("{startChapter}", startChapter.toString());
        title = title.replace("{endChapter}", endChapter.toString());
        return title;
    }

}
