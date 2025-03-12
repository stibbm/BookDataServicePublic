package book.data.service.sqlmodel.request;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "GetChapterRequestLog")
@Table(name = "getChapterRequestLog")
public class GetChapterRequestLog implements Serializable {

    public String getBookName() {return this.bookName;}
    public void setBookName(String bookName) {this.bookName = bookName;}

    public Long getRequestId() {
        return requestId;
    }
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getChapterNumber() {return this.chapterNumber;}
    public void setChapterNumber(Long chapterNumber) {this.chapterNumber = chapterNumber;}

    public Long getTimestamp() {return this.timestamp;}
    public void setTimestamp(Long timestamp) {this.timestamp = timestamp;}

    public String getEmail() {return this.email;}
    public void setEmail(String email){this.email = email;}

    public GetChapterRequestLog(
            Long requestId,
            String bookName,
            Long chapterNumber,
            Long timestamp,
            String email
    ) {
        this.requestId = requestId;
        this.bookName = bookName;
        this.chapterNumber = chapterNumber;
        this.timestamp = timestamp;
        this.email = email;
    }

    public GetChapterRequestLog() {}

    @Id
    @GeneratedValue
    private Long requestId;
    private String bookName;
    private Long chapterNumber;
    private Long timestamp;
    private String email;
}
