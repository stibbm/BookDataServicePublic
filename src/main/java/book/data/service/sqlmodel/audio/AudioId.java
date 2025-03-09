package book.data.service.sqlmodel.audio;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import book.data.service.sqlmodel.chapter.Chapter;

@Embeddable
public class AudioId  implements Serializable {
    private Long audioNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "book_number"),
            @JoinColumn(name = "chapter_number")
    })
    private Chapter chapter;

    public Long getAudioNumber() {return this.audioNumber;}
    public Chapter getChapter() {return this.chapter;}

    public void setAudioNumber(Long audioNumber) {this.audioNumber = audioNumber;}
    public void setChapter(Chapter chapter) {this.chapter = chapter;}

    public AudioId(Long audioNumber, Chapter chapter) {
        this.audioNumber = audioNumber;
        this.chapter = chapter;
    }
    public AudioId() {}

    @Override
    public int hashCode() {return Objects.hash(this.chapter, this.audioNumber);}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudioId that = (AudioId) o;
        return this.chapter.equals(that.getChapter()) && this.audioNumber.equals(that.getAudioNumber());
    }

    @Override
    public String toString() {
        return "AudioId{" +
                "chapter=" + chapter +
                ", audioNumber=" + audioNumber +
                '}';
    }

}