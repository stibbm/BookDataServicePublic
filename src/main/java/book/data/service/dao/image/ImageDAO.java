package book.data.service.dao.image;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import book.data.service.exception.BookDoesNotExistException;
import book.data.service.exception.ChapterDoesNotExistException;
import book.data.service.exception.ImageAlreadyExistsException;
import book.data.service.exception.ImageDoesNotExistException;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
import book.data.service.repository.ImageRepository;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.chapter.LockStatus;
import book.data.service.sqlmodel.image.Image;
import book.data.service.sqlmodel.image.ImageId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImageDAO {

    private BookRepository bookRepository;
    private ChapterRepository chapterRepository;
    private ImageRepository imageRepository;

    @Autowired
    public ImageDAO(
        ImageRepository imageRepository,
        ChapterRepository chapterRepository,
        BookRepository bookRepository
    ) {
        this.imageRepository = imageRepository;
        this.chapterRepository = chapterRepository;
        this.bookRepository = bookRepository;
    }

    public List<Image> getImagesByBookNameAndChapterNumberPaged(
        String bookName,
        Long chapterNumber,
        int pageNumber,
        int pageSize,
        String createdBy
    ) throws BookDoesNotExistException, ChapterDoesNotExistException {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(bookName, chapterNumber, createdBy)) {
            throw new ChapterDoesNotExistException();
        }
        Chapter currChapter = chapterRepository
            .findChapterByBookNameAndChapterNumber(
                bookName,
                chapterNumber,
                createdBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return imageRepository.findImagesByBookNameAndChapterNumberPaged(
            bookName,
            chapterNumber,
            pageRequest,
            createdBy
        );
    }

    public List<Image> getImagesByBookNumber(Long bookNumber) {
        return imageRepository.findImagesByBookNumber(bookNumber);
    }

    public Image getImageByBookNameAndChapterNumberAndImageNumber(
        String bookName,
        Long chapterNumber,
        Long imageNumber,
        String createdBy
    ) throws BookDoesNotExistException, ChapterDoesNotExistException, ImageAlreadyExistsException {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(bookName, chapterNumber, createdBy)) {
            throw new ChapterDoesNotExistException();
        }
        if (!imageRepository.doesImageExist(bookName, chapterNumber, imageNumber, createdBy)) {
            throw new ImageDoesNotExistException();
        }
        return imageRepository.findImageByBookNameAndChapterNumberAndImageNumber(
            bookName,
            chapterNumber,
            imageNumber,
            createdBy
        );
    }

    public Image createImage(
        Chapter chapter,
        Long imageNumber,
        String s3Key,
        String s3Bucket,
        String relativeImageUrl,
        String createdBy,
        String fileType,
        Long createdEpochMilli
    ) throws BookDoesNotExistException, ChapterDoesNotExistException, ImageAlreadyExistsException {
        if (!bookRepository.doesBookExist(chapter.getChapterId().getBook().getBookName(),
            createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(
            chapter.getChapterId().getBook().getBookName(),
            chapter.getChapterId().getChapterNumber(),
            createdBy

        )) {
            throw new ChapterDoesNotExistException();
        }
        if (imageRepository.doesImageExist(
            chapter.getChapterId().getBook().getBookName(),
            chapter.getChapterId().getChapterNumber(),
            imageNumber,
            createdBy
        )) {
            throw new ImageAlreadyExistsException();
        }
        ImageId imageId = new ImageId(
            imageNumber, chapter
        );
        Image image = new Image(
            imageId,
            s3Key,
            s3Bucket,
            relativeImageUrl,
            createdBy,
            fileType,
            createdEpochMilli
        );
        imageRepository.saveImage(image);
        return image;
    }

}
