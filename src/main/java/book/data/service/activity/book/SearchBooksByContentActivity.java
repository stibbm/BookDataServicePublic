package book.data.service.activity.book;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.SEARCH_BOOKS_BY_BOOK_CONTENT;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.message.book.searchbooks.SearchBooksRequest;
import book.data.service.message.book.searchbooks.SearchBooksResponse;
import book.data.service.sqlmodel.book.Book;
import java.util.List;
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
public class SearchBooksByContentActivity {
    private BookManager bookManager;
    private FirebaseService firebaseService;

    @Autowired
    public SearchBooksByContentActivity(BookManager bookManager, FirebaseService firebaseService) {
        this.bookManager = bookManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(SEARCH_BOOKS_BY_BOOK_CONTENT)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody SearchBooksResponse searchBooksByBookContent(
        @RequestBody SearchBooksRequest searchBooksRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        List<Book> bookList = bookManager.searchBooks(
            searchBooksRequest.getSearchText(),
            searchBooksRequest.getSearchTermFilterType(),
            email
        );
        SearchBooksResponse searchBooksResponse = SearchBooksResponse.builder()
            .bookList(bookList)
            .build();
        return searchBooksResponse;
    }
}
