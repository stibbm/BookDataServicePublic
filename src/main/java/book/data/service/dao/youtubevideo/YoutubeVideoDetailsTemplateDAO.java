package book.data.service.dao.youtubevideo;

import book.data.service.exception.book.BookDoesNotExistException;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import book.data.service.repository.BookRepository;
import book.data.service.repository.YoutubeVideoDetailsTemplateRepository;
import book.data.service.sqlmodel.youtubevideo.YoutubeVideoDetailsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class YoutubeVideoDetailsTemplateDAO {

    private YoutubeVideoDetailsTemplateRepository youtubeVideoDetailsTemplateRepository;
    private BookRepository bookRepository;


    @Autowired
    public YoutubeVideoDetailsTemplateDAO(
        YoutubeVideoDetailsTemplateRepository youtubeVideoDetailsTemplateRepository,
        BookRepository bookRepository
    ) {
        this.youtubeVideoDetailsTemplateRepository = youtubeVideoDetailsTemplateRepository;
        this.bookRepository = bookRepository;
    }

    public YoutubeVideoDetailsTemplate getYoutubeVideoDetailsTemplateByBookNumber(Long bookNumber) {
        if (!bookRepository.doesBookExistWithOnlyBookNumber(bookNumber)) {
            throw new BookDoesNotExistException();
        }
        if (!youtubeVideoDetailsTemplateRepository.doesYoutubeVideoDetailsTemplateExist(bookNumber)) {
            throw new RuntimeException(
                "youtubeVideoDetailsTemplateDoesNotExist for bookNumber: " + bookNumber);
        }
        return youtubeVideoDetailsTemplateRepository.findYoutubeVideoDetailsTemplateByBookNumber(
            bookNumber);
    }

    public boolean doesYoutubeVideoDetailsTemplateExist(Long bookNumber) {
        return youtubeVideoDetailsTemplateRepository.doesYoutubeVideoDetailsTemplateExist(
            bookNumber);
    }


    public YoutubeVideoDetailsTemplate createYoutubeVideoDetailsTemplate(
        Long bookNumber,
        String titleTemplate,
        String descriptionTemplate,
        Set<String> videoTags
    ) {
        if (!bookRepository.doesBookExistWithOnlyBookNumber(bookNumber)) {
            throw new BookDoesNotExistException();
        }
        YoutubeVideoDetailsTemplate youtubeVideoDetailsTemplate =
            new YoutubeVideoDetailsTemplate(bookNumber, titleTemplate, descriptionTemplate,
                videoTags);
        youtubeVideoDetailsTemplateRepository.save(
            youtubeVideoDetailsTemplate
        );
        youtubeVideoDetailsTemplate = youtubeVideoDetailsTemplateRepository.findYoutubeVideoDetailsTemplateByBookNumber(
            bookNumber);
        return youtubeVideoDetailsTemplate;
    }

}
