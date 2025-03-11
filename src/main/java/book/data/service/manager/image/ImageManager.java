package book.data.service.manager.image;


import book.data.service.clientwrapper.S3ClientWrapper;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.dao.image.ImageDAO;
import book.data.service.service.id.IdService;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Component
public class ImageManager {
    private ImageDAO imageDAO;
    private ChapterDAO chapterDAO;
    private S3ClientWrapper s3ClientWrapper;
    private IdService idService;
    private TimeService timeService;

    @Autowired
    public ImageManager(
            ImageDAO imageDAO,
            ChapterDAO chapterDAO,
            S3ClientWrapper s3ClientWrapper,
            IdService idService,
            TimeService timeService
    ) {
        this.imageDAO = imageDAO;
        this.chapterDAO = chapterDAO;
        this.s3ClientWrapper = s3ClientWrapper;
        this.idService = idService;
        this.timeService = timeService;
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
            String s3Bucket,
            String createdBy,
            byte[] fileBytes,
            String fileType
    ) throws MalformedURLException, URISyntaxException {
        Chapter chapter = chapterDAO.getChapterByBookNameAndChapterNumber(
                bookName, chapterNumber, createdBy
        );
        String s3Key = idService.generateS3Key(
                chapter.getChapterId().getBook().getBookNumber(),
                chapterNumber,
                imageNumber,
                fileType
        );
        String relativeImageUrl = idService.generateRelativeImageUrl(
                chapter.getChapterId().getBook().getBookNumber(),
                chapterNumber,
                imageNumber,
                fileType
        );
        s3ClientWrapper.createS3File(
                fileBytes,
                s3Bucket,
                s3Key
        );
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        return imageDAO.createImage(
                chapter,
                imageNumber,
                s3Key,
                s3Bucket,
                relativeImageUrl,
                createdBy,
                fileType,
                createdEpochMilli
        );
    }
}
