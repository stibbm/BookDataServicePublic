package book.data.service.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import book.data.service.sqlmodel.youtubevideo.YoutubeVideoDetailsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class YoutubeVideoDetailsTemplateRepository {

  private EntityManager entityManager;

  @Autowired
  public YoutubeVideoDetailsTemplateRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public YoutubeVideoDetailsTemplate findYoutubeVideoDetailsTemplateByBookNumber(Long bookNumber) {
    String queryString = "SELECT yvdt FROM YoutubeVideoDetailsTemplate yvdt WHERE "
        + "yvdt.bookNumber=:bookNumber";
    TypedQuery<YoutubeVideoDetailsTemplate> query = entityManager.createQuery(queryString,
        YoutubeVideoDetailsTemplate.class);
    query.setParameter("bookNumber", bookNumber);
    YoutubeVideoDetailsTemplate result = query.getSingleResult();
    return result;
  }

  public boolean doesYoutubeVideoDetailsTemplateExist(Long bookNumber) {
    try {
      YoutubeVideoDetailsTemplate youtubeVideoDetailsTemplate = findYoutubeVideoDetailsTemplateByBookNumber(bookNumber);
      return youtubeVideoDetailsTemplate != null;
    } catch (NoResultException e) {
      return false;
    }
  }

  @Transactional
  public void save(YoutubeVideoDetailsTemplate youtubeVideoDetailsTemplate) {
    entityManager.persist(youtubeVideoDetailsTemplate);
  }
}
