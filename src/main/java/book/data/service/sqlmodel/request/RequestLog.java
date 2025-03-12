package book.data.service.sqlmodel.request;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity(name = "RequestLog")
@Table(name = "requestLog")
public class RequestLog implements Serializable {

    public Long getRequestId() {
        return requestId;
    }
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getRequestPath() {
        return requestPath;
    }
    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestBody() {return requestBody;}
    public void setRequestBody(String requestBody) {this.requestBody=requestBody;}

    public Long getTimestamp() {return this.timestamp;}
    public void setTimestamp(Long timestamp){this.timestamp = timestamp;}

    public String getRequestUuid() {return this.requestUuid;}
    public void setRequestUuid(String uuid) {this.requestUuid = uuid;}

    public RequestLog(
            Long requestId,
            String requestPath,
            String requestBody,
            Long timestamp,
            String requestUuid
    ) {
        this.requestId = requestId;
        this.requestPath = requestPath;
        this.requestBody = requestBody;
        this.timestamp = timestamp;
        this.requestUuid = requestUuid;
    }

    public RequestLog() {}

    @Id
    @GeneratedValue
    private Long requestId;
    private String requestPath;

    @Lob
    @Column(columnDefinition = "LONGBLOB NOT NULL")
    private String requestBody;
    private Long timestamp;
    private String requestUuid;
}
