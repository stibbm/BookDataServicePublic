package book.data.service.manager.uploadtranslatedyoutubebook;

import book.data.service.dao.book.BookDAO;
import book.data.service.dao.tokentransaction.PaymentForTranslatedYoutubeBookDAO;
import book.data.service.dao.youtubevideo.YoutubeVideoDetailsTemplateDAO;
import book.data.service.model.TranslatedYoutubeBookStatus;
import book.data.service.model.VideoData;
import book.data.service.service.s3video.S3YoutubeVideoService;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.payment.PaymentForTranslatedYoutubeBook;
import book.data.service.sqlmodel.youtubevideo.YoutubeVideoDetailsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UploadTranslatedYoutubeBookManager {

    private PaymentForTranslatedYoutubeBookDAO paymentForTranslatedYoutubeBookDAO;
    private YouTubeVideoManager youTubeVideoManager;
    private YoutubeVideoDetailsService youtubeVideoDetailsService;
    private YouTubePlayListService youTubePlayListService;
    private YoutubeVideoDetailsTemplateDAO youtubeVideoDetailsTemplateDAO;
    private YoutubeVideoDetailsFactory youtubeVideoDetailsFactory;
    private YoutubeUploadService youtubeUploadService;
    private BookDAO bookDAO;
    private VideoDetailsTemplates videoDetailsTemplates;
    private S3YoutubeVideoService s3YoutubeVideoService;

    @Autowired
    public UploadTranslatedYoutubeBookManager(
            PaymentForTranslatedYoutubeBookDAO paymentForTranslatedYoutubeBookDAO,
            YouTubeVideoManager youTubeVideoManager,
            YoutubeVideoDetailsService youtubeVideoDetailsService,
            YouTubePlayListService youTubePlayListService,
            YoutubeVideoDetailsTemplateDAO youtubeVideoDetailsTemplateDAO,
            YoutubeVideoDetailsFactory youtubeVideoDetailsFactory,
            YoutubeUploadService youtubeUploadService,
            BookDAO bookDAO,
            VideoDetailsTemplates videoDetailsTemplates,
            S3YoutubeVideoService s3YoutubeVideoService
    ) {
        this.paymentForTranslatedYoutubeBookDAO = paymentForTranslatedYoutubeBookDAO;
        this.youTubeVideoManager = youTubeVideoManager;
        this.youtubeVideoDetailsService = youtubeVideoDetailsService;
        this.youTubePlayListService = youTubePlayListService;
        this.youtubeVideoDetailsTemplateDAO = youtubeVideoDetailsTemplateDAO;
        this.youtubeVideoDetailsFactory = youtubeVideoDetailsFactory;
        this.youtubeUploadService = youtubeUploadService;
        this.bookDAO = bookDAO;
        this.videoDetailsTemplates = videoDetailsTemplates;
        this.s3YoutubeVideoService = s3YoutubeVideoService;
    }

    public YouTubeVideo uploadTranslatedYoutubeBook(VideoData videoData, Long bookNumber,
                                                    Long startChapter,
                                                    Long endChapter, String createdBy) throws IOException {
        log.info(
                "Uploading translated youtube book for bookNumber: {}, startChapter: {}, endChapter: {}, createdBy: {}",
                bookNumber, startChapter, endChapter, createdBy);
        synchronized (UPLOAD_TRANSLATED_YOUTUBE_BOOK_LOCK) {
            if (!paymentForTranslatedYoutubeBookDAO.doesPaymentForTranslatedYoutubeBookExist(
                    bookNumber, startChapter, endChapter
            )) {
                throw new YoutubeBookUploadPaymentNotFoundException();
            }
            PaymentForTranslatedYoutubeBook payment = paymentForTranslatedYoutubeBookDAO.getPaymentByBookNumberAndStartChapterAndEndChapter(
                    bookNumber, startChapter, endChapter);
            if (payment.getTranslatedYoutubeBookStatus()
                    .equals(TranslatedYoutubeBookStatus.PAID_FOR.toString())) {
                log.info("Translated Status currently is PAID_FOR");
                PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook = paymentForTranslatedYoutubeBookDAO.updatePaymentTranslatedYoutubeBookStatus(
                        TranslatedYoutubeBookStatus.IN_PROGRESS.toString(), bookNumber, startChapter,
                        endChapter);
                log.info("Updated Translated status: "
                        + paymentForTranslatedYoutubeBook.getTranslatedYoutubeBookStatus());
                Book book = bookDAO.getBookByBookNumberOnly(bookNumber);
                log.info("HERE IS WHERE ERROR WAS");
                log.info("book: " + book.toString());
                log.info("GET_NAME_TO_SERIES_MAP = " + GET_NAME_TO_SERIES_MAP());
                Series series = GET_NAME_TO_SERIES_MAP().get(book.getBookName());
                YoutubeVideoDetailsTemplate detailsTemplate = videoDetailsTemplates.getSeriesTemplateAsYoutubeVideoDetailsTemplate(
                        series);
                String title = youtubeVideoDetailsFactory.buildTitleOrDescription(
                        detailsTemplate.getTitleTemplate(), startChapter, endChapter);
                log.info("youtube video details factory");
                String description = youtubeVideoDetailsFactory.buildTitleOrDescription(
                        detailsTemplate.getDescriptionTemplate(), startChapter, endChapter);
                Set<String> tags = detailsTemplate.getVideoTags();
                List<String> tagList = tags.stream().toList();

                boolean success = s3YoutubeVideoService.uploadYoutubeVideoToS3(title, videoData);

                YouTubeVideo youtubeVideo = youtubeUploadService.uploadVideo(
                        title,
                        description,
                        tagList,
                        videoData
                );
                log.info("youtube upload completed");
                matt.book.data.service.sqlmodel.youtubevideo.YouTubeVideo dbYoutubeVideo =
                        youTubeVideoManager.createYouTubeVideo(
                                youtubeVideo.getVideoId(),
                                bookNumber,
                                startChapter,
                                endChapter,
                                createdBy
                        );
                log.info("dbYoutubeVideo: " + dbYoutubeVideo);
                if (youtubeVideo != null) {
                    paymentForTranslatedYoutubeBookDAO.updatePaymentTranslatedYoutubeBookStatus(
                            TranslatedYoutubeBookStatus.COMPLETED.toString(), bookNumber, startChapter,
                            endChapter);
                    log.info("updating milestone to completed");
                    paymentForTranslatedYoutubeBookDAO.updatePaymentMilestone(
                            VideoCreationMilestone.COMPLETED,
                            bookNumber,
                            startChapter,
                            endChapter
                    );
                    log.info("updated milestone to completed");
                    return youtubeVideo;
                } else {
                    throw new RuntimeException("failed to upload youtube video");
                }
            } else if (payment.getTranslatedYoutubeBookStatus()
                    .equals(TranslatedYoutubeBookStatus.IN_PROGRESS.toString())) {
                throw new YoutubeBookUploadAlreadyInProgressException();
            } else if (payment.getTranslatedYoutubeBookStatus()
                    .equals(TranslatedYoutubeBookStatus.COMPLETED.toString())) {
                throw new IllegalArgumentException(
                        "TranslatedYoutubeBook upload has already been completed");
            } else {
                throw new RuntimeException("unknown error occurred");
            }
        }
    }
}
