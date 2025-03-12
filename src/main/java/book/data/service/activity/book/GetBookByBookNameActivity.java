package book.data.service.activity.book;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_BOOK_BY_BOOK_NAME;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.manager.book.GetBookRequestLogManager;
import book.data.service.message.book.GetBookByBookNameRequest;
import book.data.service.message.book.GetBookByBookNameResponse;
import book.data.service.sqlmodel.book.Book;
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
public class GetBookByBookNameActivity {

    private BookManager bookManager;
    private FirebaseService firebaseService;
    private GetBookRequestLogManager getBookRequestLogManager;

    @Autowired
    public GetBookByBookNameActivity(
        BookManager bookManager,
        FirebaseService firebaseService,
        GetBookRequestLogManager getBookRequestLogManager
    ) {
        this.bookManager = bookManager;
        this.firebaseService = firebaseService;
        this.getBookRequestLogManager = getBookRequestLogManager;
    }

    @PostMapping(GET_BOOK_BY_BOOK_NAME)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetBookByBookNameResponse getBookByBookName(
        @RequestBody GetBookByBookNameRequest getBookByBookNameRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String createdBy = firebaseService.getEmail(authToken);
        getBookRequestLogManager.createGetBookRequestLog(
            getBookByBookNameRequest.getBookName(),
            createdBy
        );
        Book book = bookManager.getBookByBookName(getBookByBookNameRequest.getBookName(),
            createdBy);
        GetBookByBookNameResponse getBookByBookNameResponse =
            GetBookByBookNameResponse.builder().book(book).build();
        return getBookByBookNameResponse;
    }
}
