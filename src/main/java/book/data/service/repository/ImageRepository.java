package book.data.service.repository;

import book.data.service.sqlmodel.image.Image;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ImageRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Image> findImagesByBookNameAndChapterNumberPaged(
        String bookName,
        Long chapterNumber,
        PageRequest pageRequest,
        String createdBy
    ) {
        String queryString = "SELECT i FROM Image i "
            + "WHERE i.imageId.chapter.chapterId.book.bookName=:bookName AND "
            + "i.imageId.chapter.chapterId.chapterNumber=:chapterNumber AND "
            + "i.createdBy=:createdBy";
        TypedQuery<Image> query = entityManager.createQuery(queryString, Image.class);
        query.setParameter("bookName", bookName);
        query.setParameter("chapterNumber", chapterNumber);
        query.setParameter("createdBy", createdBy);
        query.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());
        List<Image> imageList = query.getResultList();
        return imageList;
    }

    public Image findImageByBookNameAndChapterNumberAndImageNumber(
        String bookName,
        Long chapterNumber,
        Long imageNumber,
        String createdBy
    ) {
        String queryString = "SELECT i FROM Image i WHERE i.imageId.chapter.chapterId.book.bookName=:bookName "
            + "AND i.imageId.chapter.chapterId.chapterNumber=:chapterNumber "
            + "AND i.imageId.imageNumber=:imageNumber "
            + "AND i.createdBy=:createdBy";
        TypedQuery<Image> query = entityManager.createQuery(queryString, Image.class);
        query.setParameter("bookName", bookName);
        query.setParameter("chapterNumber", chapterNumber);
        query.setParameter("imageNumber", imageNumber);
        query.setParameter("createdBy", createdBy);
        List<Image> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    public boolean doesImageExist(
        String bookName, Long chapterNumber, Long imageNumber, String createdBy
    ) {
        Image image = findImageByBookNameAndChapterNumberAndImageNumber(
            bookName, chapterNumber, imageNumber, createdBy
        );
        return image!=null;
    }

    @Transactional
    public void saveImage(Image image) {
        this.entityManager.persist(image);
    }
}
