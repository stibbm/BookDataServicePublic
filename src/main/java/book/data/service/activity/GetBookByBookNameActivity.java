package book.data.service.activity;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_BOOK_BY_BOOK_NAME;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.BookManager;
import book.data.service.message.book.GetBookByBookNameRequest;
import book.data.service.message.book.GetBookByBookNameResponse;
import book.data.service.model.Book;
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

    @Autowired
    public GetBookByBookNameActivity(
        BookManager bookManager,
        FirebaseService firebaseService
    ) {
        this.bookManager = bookManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_BOOK_BY_BOOK_NAME)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetBookByBookNameResponse getBookByBookName(
        @RequestBody GetBookByBookNameRequest getBookByBookNameRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String createdBy = firebaseService.getEmail(authToken);
        Book book = bookManager.getBookByBookName(getBookByBookNameRequest.getBookName(),
            createdBy);
        GetBookByBookNameResponse getBookByBookNameResponse =
            GetBookByBookNameResponse.builder().book(book).build();
        return getBookByBookNameResponse;
    }
}
