package book.data.service.activity.bookview;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_BOOK_VIEWS_BY_BOOK_NUMBER;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.bookview.BookViewManager;
import book.data.service.message.bookview.GetBookViewsByBookNumberRequest;
import book.data.service.message.bookview.GetBookViewsByBookNumberResponse;
import book.data.service.sqlmodel.bookview.BookView;
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
public class GetBookViewsByBookNumberActivity {

    private BookViewManager bookViewManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetBookViewsByBookNumberActivity(
        BookViewManager bookViewManager,
        FirebaseService firebaseService
    ) {
        this.bookViewManager = bookViewManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_BOOK_VIEWS_BY_BOOK_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetBookViewsByBookNumberResponse getBookViewsByBookNumber(
        @RequestBody GetBookViewsByBookNumberRequest getBookViewsByBookNumberRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        log.info("getBookViewsByBookNumber");
        log.info(getBookViewsByBookNumberRequest.toString());
        String createdBy = firebaseService.getEmail(authToken);
        List<BookView> bookViewList =
            this.bookViewManager.getBookViewsByBookNumber(Long.parseLong(getBookViewsByBookNumberRequest
                .getBookNumber()), createdBy);
        //log.info("bookViewsList: " + bookViewList);
        return GetBookViewsByBookNumberResponse.builder().bookViewList(bookViewList).build();
    }

}
