package book.data.service.manager.bookview;

import book.data.service.dao.book.BookDAO;
import book.data.service.dao.bookview.BookViewDAO;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.bookview.BookView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookViewManager {
    private BookViewDAO bookViewDAO;
    private BookDAO bookDAO;
    private TimeService timeService;

    public BookViewManager(
        BookViewDAO bookViewDAO,
        BookDAO bookDAO,
        TimeService timeService
    ) {
        this.bookViewDAO = bookViewDAO;
        this.bookDAO = bookDAO;
        this.timeService = timeService;
    }

    public List<BookView> getBookViewsByBookNumber(Long bookNumber, String createdBy) {
        List<BookView> bookViewList = bookViewDAO.getBookViewsByBookNumber(bookNumber, createdBy);
        return bookViewList;
    }

    public BookView createBookView(
        Long bookNumber,
        String createdBy
    ) {
        Book book = bookDAO.getBookByBookNumber(bookNumber, createdBy);
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        return bookViewDAO.createBookView(
            book,
            createdBy,
            createdEpochMilli
        );
    }
}
