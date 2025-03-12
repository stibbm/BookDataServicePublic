package book.data.service.activity.bookview;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_BOOK_VIEW;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.manager.bookview.BookViewManager;
import book.data.service.message.bookview.CreateBookViewRequest;
import book.data.service.message.bookview.CreateBookViewResponse;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.bookview.BookView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreateBookViewActivity {

    private BookViewManager bookViewManager;
    private FirebaseService firebaseService;
    private BookManager bookManager;

    @Autowired
    public CreateBookViewActivity(
        BookViewManager bookViewManager,
        FirebaseService firebaseService,
        BookManager bookManager
    ) {
        this.bookViewManager = bookViewManager;
        this.firebaseService = firebaseService;
        this.bookManager = bookManager;
    }

    @PostMapping(CREATE_BOOK_VIEW)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody CreateBookViewResponse createBookView(
        @RequestBody CreateBookViewRequest createBookViewRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String createdBy = firebaseService.getEmail(authToken);
        BookView bookView = bookViewManager.createBookView(
            Long.parseLong(createBookViewRequest.getBookNumber()),
            createdBy
        );
        long bookViewsCount = bookViewManager.getBookViewsByBookNumber(
            Long.parseLong(createBookViewRequest.getBookNumber()),
            createdBy).size();
        Book updatedBook = bookManager.updateBookViewsByBookNumber(bookView.getBookNumber(),
            bookViewsCount, createdBy);
        log.info("updatedBook: " + updatedBook);
        return CreateBookViewResponse.builder()
            .bookView(bookView)
            .build();
    }
}
