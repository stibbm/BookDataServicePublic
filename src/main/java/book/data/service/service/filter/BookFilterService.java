package book.data.service.service.filter;
import book.data.service.sqlmodel.book.Book;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookFilterService {

    public List<Book> selectBooksWithAllSpecifiedTags(
        List<Book> bookList,
        List<String> tagList
    ) {
        List<Book> selectedBookList = new ArrayList<>();
        for (Book book: bookList) {
            if (doesBookContainAllTags(book, tagList)) {
                selectedBookList.add(book);
            }
        }
        return selectedBookList;
    }

    public List<Book> selectBooksWithAtLeastOneSpecifiedTag(
        List<Book> bookList,
        List<String> tagList
    ) {
        List<Book> selectedBookList = new ArrayList<>();
        for (Book book: bookList) {
            if (doesBookContainAtLeastOneTag(book, tagList)) {
                selectedBookList.add(book);
            }
        }
        return selectedBookList;
    }

    public boolean doesBookContainAllTags(Book book, List<String> tagList) {
        return book.getBookTags().containsAll(tagList);
    }

    public boolean doesBookContainAtLeastOneTag(Book book, List<String> tagList) {
        for (String tag: tagList) {
            if (book.getBookTags().contains(tag)) {
                return true;
            }
        }
        return false;
    }
}