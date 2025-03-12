package book.data.service.activity.book;


import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.SEARCH_BOOKS_BY_BOOK_TAGS;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.message.book.searchbooksbybooktags.SearchBooksByBookTagsRequest;
import book.data.service.message.book.searchbooksbybooktags.SearchBooksByBookTagsResponse;
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
public class SearchBooksByBookTagsActivity {
    private BookManager bookManager;
    private FirebaseService firebaseService;

    @Autowired
    public SearchBooksByBookTagsActivity(BookManager bookManager, FirebaseService firebaseService) {
        this.bookManager = bookManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(SEARCH_BOOKS_BY_BOOK_TAGS)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody SearchBooksByBookTagsResponse searchBooksByBookTags(
        @RequestBody SearchBooksByBookTagsRequest searchBooksByBookTagsRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        List<Book> bookList = bookManager.searchBooksByBookTags(
            searchBooksByBookTagsRequest.getBookTags(),
            searchBooksByBookTagsRequest.getTagFilterType(),
            email
        );
        SearchBooksByBookTagsResponse searchBooksByBookTagsResponse =
            SearchBooksByBookTagsResponse.builder().bookList(bookList).build();
        return searchBooksByBookTagsResponse;
    }

}
