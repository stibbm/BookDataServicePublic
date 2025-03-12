package book.data.service.activity.book;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_BOOK_BY_BOOK_NUMBER;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.message.book.getbookbybooknumber.GetBookByBookNumberRequest;
import book.data.service.message.book.getbookbybooknumber.GetBookByBookNumberResponse;
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
public class GetBookByBookNumberActivity {

    private BookManager bookManager;
    private FirebaseService firebaseService;

    @Autowired
    private GetBookByBookNumberActivity(BookManager bookManager, FirebaseService firebaseService) {
        this.bookManager = bookManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_BOOK_BY_BOOK_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetBookByBookNumberResponse getBookByBookNumber(
        @RequestBody GetBookByBookNumberRequest getBookByBookNumberRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info("getBookByBookNumber");
        String email = firebaseService.getEmail(authToken);
        Book book = bookManager.getBookByBookNumber(getBookByBookNumberRequest.getBookNumber(), email);
        GetBookByBookNumberResponse getBookByBookNumberResponse = GetBookByBookNumberResponse.builder()
            .book(book)
            .build();
        return getBookByBookNumberResponse;
    }
}
