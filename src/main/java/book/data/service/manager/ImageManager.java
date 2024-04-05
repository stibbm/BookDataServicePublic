package book.data.service.manager;

import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.dao.image.ImageDAO;
import book.data.service.model.chapter.Chapter;
import book.data.service.model.image.Image;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImageManager {
    private ImageDAO imageDAO;
    private ChapterDAO chapterDAO;

    @Autowired
    public ImageManager(
        ImageDAO imageDAO,
        ChapterDAO chapterDAO
    ) {
        this.imageDAO = imageDAO;
        this.chapterDAO = chapterDAO;
    }

    public List<Image> getImagesByBookNameAndChapterNumberPaged(
        String bookName,
        Long chapterNumber,
        int pageNumber,
        int pageSize,
        String createdBy
    ) {
        return imageDAO.getImagesByBookNameAndChapterNumberPaged(
            bookName,
            chapterNumber,
            pageNumber,
            pageSize,
            createdBy
        );
    }

    public Image getImageByBookNameAndChapterNameAndImageNumber(
        String bookName,
        Long chapterNumber,
        Long imageNumber,
        String createdBy
    ) {
        return imageDAO.getImageByBookNameAndChapterNumberAndImageNumber(
            bookName,
            chapterNumber,
            imageNumber,
            createdBy
        );
    }

    public Image createImage(
        String bookName,
        Long chapterNumber,
        Long imageNumber,
        String s3Key,
        String s3Bucket,
        String relativeImageUrl,
        String createdBy
    ) {
        Chapter chapter = chapterDAO.getChapterByBookNameAndChapterNumber(
            bookName, chapterNumber, createdBy
        );
        return imageDAO.createImage(
            chapter,
            imageNumber,
            s3Key,
            s3Bucket,
            relativeImageUrl,
            createdBy
        );
    }
}
