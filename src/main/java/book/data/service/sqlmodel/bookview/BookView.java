package book.data.service.sqlmodel.bookview;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@Builder
@Entity(name = "BookView")
@Table(name = "bookView")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class BookView implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookViewNumber;

    private Long bookNumber;
    private String createdBy;
    private Long createdEpochMilli;
}
