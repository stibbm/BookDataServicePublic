package book.data.service.manager.youtubevideo;

import book.data.service.dao.youtubevideo.YoutubeVideoDetailsTemplateDAO;
import book.data.service.sqlmodel.youtubevideo.YoutubeVideoDetailsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class YoutubeVideoDetailsTemplateManager {
    private YoutubeVideoDetailsTemplateDAO youtubeVideoDetailsTemplateDAO;

    @Autowired
    public YoutubeVideoDetailsTemplateManager(YoutubeVideoDetailsTemplateDAO youtubeVideoDetailsTemplateDAO) {
        this.youtubeVideoDetailsTemplateDAO = youtubeVideoDetailsTemplateDAO;
    }

    public YoutubeVideoDetailsTemplate getYoutubeVideoDetailsTemplateByBookNumber(Long bookNumber) {
        return youtubeVideoDetailsTemplateDAO.getYoutubeVideoDetailsTemplateByBookNumber(bookNumber);
    }

    public YoutubeVideoDetailsTemplate createYoutubeVideoDetailsTemplate(
            Long bookNumber,
            String titleTemplate,
            String descriptionTemplate,
            List<String> videoTagList
    ) {
        Set<String> videoTagSet = new HashSet<>(videoTagList);
        return youtubeVideoDetailsTemplateDAO.createYoutubeVideoDetailsTemplate(
                bookNumber,
                titleTemplate,
                descriptionTemplate,
                videoTagSet
        );
    }
}
