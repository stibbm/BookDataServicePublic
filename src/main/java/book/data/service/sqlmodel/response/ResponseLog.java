package book.data.service.sqlmodel.response;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity(name = "ResponseLog")
@Table(name = "responseLog")
public class ResponseLog implements Serializable {

    public Long getResponseId() {return responseId;}
    public void setResponseId(Long responseId) {this.responseId = responseId;}

    public String getRequestPath() {return requestPath;}
    public void setRequestPath(String requestPath) {this.requestPath = requestPath;}

    public String getResponseBody() {return responseBody;}
    public void setResponseBody(String responseBody) {this.responseBody = responseBody;}

    public Long getTimestamp() {return timestamp;}
    public void setTimestamp(Long timestamp) {this.timestamp=timestamp;}

    public String getResponseUuid() {return responseUuid;}
    public void setResponseUuid(String uuid) {this.responseUuid = uuid;}

    public ResponseLog(
            Long responseId,
            String requestPath,
            String responseBody,
            Long timestamp,
            String responseUuid
    ) {
        this.responseId = responseId;
        this.requestPath = requestPath;
        this.responseBody = responseBody;
        this.timestamp = timestamp;
        this.responseUuid = responseUuid;
    }

    public ResponseLog() {}

    @Id
    @GeneratedValue
    private Long responseId;
    private String requestPath;
    @Lob
    @Column(columnDefinition = "LONGBLOB NOT NULL")
    private String responseBody;
    private Long timestamp;
    private String responseUuid;

}
