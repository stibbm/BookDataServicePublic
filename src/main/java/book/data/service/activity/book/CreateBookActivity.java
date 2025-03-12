package book.data.service.activity.book;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_BOOK;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.message.book.CreateBookRequest;
import book.data.service.message.book.CreateBookResponse;
import book.data.service.sqlmodel.book.Book;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class CreateBookActivity {
    private BookManager bookManager;
    private FirebaseService firebaseService;

    @Autowired
    public CreateBookActivity(
        BookManager bookManager,
        FirebaseService firebaseService
    ) {
        this.bookManager = bookManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_BOOK)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody CreateBookResponse createBook(
        @RequestBody CreateBookRequest createBookRequest,
        @RequestHeader("Authorization") String authToken
    ) throws MalformedURLException, URISyntaxException {
        String createdBy = firebaseService.getEmail(authToken);
        Book book = bookManager.createBook(
            createBookRequest.getBookName(),
            createdBy,
            createBookRequest.getBookDescription(),
            createBookRequest.getBookLanguage(),
            Long.parseLong(createBookRequest.getBookViews()),
            createBookRequest.getBookTags(),
            createBookRequest.getBookThumbnailImageBytes(),
            createBookRequest.getFileType()
        );
        CreateBookResponse createBookResponse = CreateBookResponse.builder().book(book).build();
        return createBookResponse;
    }

}
