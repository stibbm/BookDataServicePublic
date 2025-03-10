package book.data.service.service.s3video;

import book.data.service.service.id.IdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class S3YoutubeVideoService {
    private S3ClientWrapper s3ClientWrapper;
    private IdService idService;

    @Autowired
    public S3YoutubeVideoService(S3ClientWrapper s3ClientWrapper, IdService idService) {
        this.s3ClientWrapper = s3ClientWrapper;
        this.idService = idService;
    }

    public boolean uploadYoutubeVideoToS3(String videoTitle, VideoData videoData) {
        String s3Key = idService.removeIllegalS3PathCharacters(videoTitle) + "." + videoData.getVideoFileType();
        s3Key = S3_YOUTUBE_VIDEO_PREFIX + "/" + s3Key;
        boolean success = this.s3ClientWrapper.createS3File(videoData.getVideoBytes(), S3_YOUTUBE_VIDEO_BUCKET, s3Key);
        if (success) {
            log.info("successfully uploaded completed file to s3");
            return true;
        }
        else {
            log.error(String.format("failed to upload video to s3 %s", videoTitle));
            return false;
        }
    }
}
