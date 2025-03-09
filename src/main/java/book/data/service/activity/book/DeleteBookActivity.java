package book.data.service.activity.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class DeleteBookActivity {
  private BookManager bookManager;
  private FirebaseService firebaseService;

  @Autowired
  public DeleteBookActivity(BookManager bookManager, FirebaseService firebaseService) {
    this.bookManager = bookManager;
    this.firebaseService = firebaseService;
  }

  @PostMapping(DELETE_BOOK_BY_NAME)
  @CrossOrigin(ALL_ORIGINS)
  public @ResponseBody DeleteBookResponse deleteBook(
    @RequestBody DeleteBookRequest deleteBookRequest,
    @RequestHeader("Authorization") String authToken
  ) {
    log.info("Deleting book: " + deleteBookRequest.toString());
    String createdBy = firebaseService.getEmail(authToken);
    String deletedBook = bookManager.deleteBook(deleteBookRequest.getBookName(), createdBy);
    DeleteBookResponse deleteBookResponse = DeleteBookResponse.builder()
      .book(deletedBook)
      .build();
    return deleteBookResponse;
  }
}
