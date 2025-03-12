package book.data.service.activity.book;


import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.message.book.getbooksbybooktag.GetBooksByBookTagRequest;
import book.data.service.message.book.getbooksbybooktag.GetBooksByBookTagResponse;
import book.data.service.sqlmodel.book.Book;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GetBooksByBookTagPagedActivity {
    private BookManager bookManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetBooksByBookTagPagedActivity(
        BookManager bookManager,
        FirebaseService firebaseService
    ) {
        this.bookManager = bookManager;
        this.firebaseService = firebaseService;
    }

    public @ResponseBody GetBooksByBookTagResponse getBooksByBookTag(
        @RequestBody GetBooksByBookTagRequest getBooksByBookTagRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        List<Book> bookList = bookManager.getBooksByBookTag(
            getBooksByBookTagRequest.getBookTag(),
            Integer.parseInt(getBooksByBookTagRequest.getPageNumber()),
            Integer.parseInt(getBooksByBookTagRequest.getPageNumber()),
            email
        );
        return GetBooksByBookTagResponse.builder().bookList(bookList).build();
    }
}
