package book.data.service.activity.book;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_ALL_BOOKS_PAGED;

import book.data.service.manager.book.BookManager;
import book.data.service.message.book.GetAllBooksPagedRequest;
import book.data.service.message.book.GetAllBooksPagedResponse;

import java.util.List;

import book.data.service.sqlmodel.book.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import book.data.service.firebase.FirebaseService;

@Slf4j
@RestController
public class GetAllBooksPagedActivity {
    private BookManager bookManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetAllBooksPagedActivity(
        BookManager bookManager,
        FirebaseService firebaseService
    ) {
        this.bookManager = bookManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_ALL_BOOKS_PAGED)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetAllBooksPagedResponse getAllBooksPaged(
        @RequestBody GetAllBooksPagedRequest getAllBooksPagedRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String createdBy = firebaseService.getEmail(authToken);
        List<Book> bookList = bookManager.getAllBooksPaged(
            Integer.parseInt(getAllBooksPagedRequest.getPageNumber()),
            Integer.parseInt(getAllBooksPagedRequest.getPageSize()),
            createdBy
        );
        GetAllBooksPagedResponse getAllBooksPagedResponse = GetAllBooksPagedResponse.builder()
            .bookList(bookList)
            .pageNumber(Integer.parseInt(getAllBooksPagedRequest.getPageNumber()))
            .pageSize(Integer.parseInt(getAllBooksPagedRequest.getPageSize()))
            .build();
        return getAllBooksPagedResponse;
    }
}
