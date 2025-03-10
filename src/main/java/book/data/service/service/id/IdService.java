package book.data.service.service.id;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IdService {
    public String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public String generateRelativeImageUrl(
            Long bookNumber,
            Long chapterNumber,
            Long imageNumber,
            String fileType
    ) throws MalformedURLException, URISyntaxException {
        String s3Key = generateS3Key(bookNumber, chapterNumber, imageNumber, fileType);
        String relativeImageUrl = generateRelativeImageUrlFromS3Key(s3Key);
        return relativeImageUrl;
    }

    public String generateS3Key(Long bookNumber, Long chapterNumber, Long imageNumber, String fileType) {
        String s3Key = S3_PREFIX + "/" + bookNumber + "/"  + chapterNumber + "/" + imageNumber + "." + fileType;
        s3Key = s3Key.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        return s3Key;
    }

    public String generateAudioS3Key(Long bookNumber, Long chapterNumber, Long audioNumber, String fileType) {
        String s3Key = S3_AUDIO_PREFIX + "/" + bookNumber + "/"  + chapterNumber + "/" + audioNumber + "." + fileType;
        s3Key = s3Key.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        return s3Key;
    }

    public String generateVideoS3Key(Long bookNumber, Long chapterNumber, Long videoNumber, String fileType) {
        String s3Key = S3_VIDEO_PREFIX + "/" + bookNumber + "/"  + chapterNumber + "/" + videoNumber + "." + fileType;
        s3Key = s3Key.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        return s3Key;
    }

    public String generateRelativeImageUrlFromS3Key(String s3Key)
            throws URISyntaxException, MalformedURLException {
        String urlPrefix = "http://localhost:9125";
        URIBuilder uriBuilder = new URIBuilder(urlPrefix);
        uriBuilder.addParameter("s3Key", s3Key);
        uriBuilder.addParameter("s3Bucket", S3_IMAGE_BUCKET);
        URL url = uriBuilder.build().toURL();
        String urlString = url.toString();
        String relativeImageUrl = urlString.substring(urlPrefix.length());
        return relativeImageUrl;
    }

    public String removeIllegalS3PathCharacters(String input) {
        return input.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
    }

    public String generateThumbnailS3Key(String bookName, String fileType, String createdBy) {
        if (createdBy.contains("@")) {
            createdBy = createdBy.substring(0, createdBy.indexOf("@"));
        }
        String s3Key = S3_PREFIX + "/" + S3_THUMBNAILS_PREFIX + "/" + createdBy + "/" + bookName + "." + fileType;
        s3Key = s3Key.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
        return s3Key;
    }

}
