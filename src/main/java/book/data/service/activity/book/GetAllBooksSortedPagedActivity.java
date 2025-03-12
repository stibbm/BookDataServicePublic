package book.data.service.activity.book;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_ALL_BOOKS_SORTED_PAGED;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.book.BookManager;
import book.data.service.message.book.allbookssortedpaged.GetAllBooksSortedPagedRequest;
import book.data.service.message.book.allbookssortedpaged.GetAllBooksSortedPagedResponse;
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
public class GetAllBooksSortedPagedActivity {
    private BookManager bookManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetAllBooksSortedPagedActivity(
        BookManager bookManager,
        FirebaseService firebaseService
    ) {
        this.bookManager = bookManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_ALL_BOOKS_SORTED_PAGED)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetAllBooksSortedPagedResponse getAllBooksSortedPaged(
        @RequestBody GetAllBooksSortedPagedRequest getAllBooksSortedPagedRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        getAllBooksSortedPagedRequest.validateSortTypeAndPagingValues();
        String createdBy = firebaseService.getEmail(authToken);
        List<Book> bookListSorted = bookManager.getAllBooksSortedPaged(
            getAllBooksSortedPagedRequest.getSortType(),
            Integer.parseInt(getAllBooksSortedPagedRequest.getPageNumber()),
            Integer.parseInt(getAllBooksSortedPagedRequest.getPageSize()),
            createdBy
        );
        GetAllBooksSortedPagedResponse getAllBooksSortedPagedResponse =
            GetAllBooksSortedPagedResponse.builder()
                .bookList(bookListSorted)
                .sortType(getAllBooksSortedPagedRequest.getSortType())
                .build();
        return getAllBooksSortedPagedResponse;
    }

}
