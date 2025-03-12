package book.data.service.sqlmodel.request;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "GetBookRequestLog")
@Table(name = "getBookRequestLog")
public class GetBookRequestLog implements Serializable {

    public String getBookName() {
        return this.bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getRequestId() {
        return this.requestId;
    }
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}

    public GetBookRequestLog(
            Long requestId,
            String bookName,
            Long timestamp,
            String email
    ) {
        this.requestId = requestId;
        this.bookName = bookName;
        this.timestamp = timestamp;
        this.email = email;
    }

    public GetBookRequestLog() {}

    @Id
    @GeneratedValue
    public Long requestId;

    private String bookName;
    private Long timestamp;
    private String email;

}
