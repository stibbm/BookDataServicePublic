package book.data.service.constants;

import com.google.gson.Gson;

public class Routes {
    public static final String CREATE_BOOK = "/createBook";
    public static final String CREATE_BOOK_VIEW = "/createBookView";
    public static final String GET_BOOK_VIEWS_BY_BOOK_NUMBER = "/getBookViewsByBookNumber";
    public static final String GET_ALL_BOOKS_PAGED = "/getAllBooksPaged";
    public static final String GET_ALL_BOOKS_SORTED_PAGED = "/getAllBooksSortedPaged";
    public static final String GET_BOOK_BY_BOOK_NAME = "/getBookByBookName";
    public static final String GET_BOOK_BY_BOOK_NUMBER = "/getBookByBookNumber";

    public static final String DELETE_BOOK_BY_NAME = "/deleteBookByName";

    public static final String SEARCH_BOOKS_BY_BOOK_TAGS = "/searchBooksByBookTags";
    public static final String SEARCH_BOOKS_BY_BOOK_CONTENT = "/searchBooksByContent";

    public static final String CREATE_CHAPTER = "/createChapter";
    public static final String CREATE_IMAGE = "/createImage";

    public static final String GET_CHAPTER_HEADERS_BY_BOOK_NUMBER = "/getChapterHeadersByBookNumber";

    public static final String GET_CHAPTER_BY_BOOK_NAME_AND_CHAPTER_NUMBER =
            "/getChapterByBookNameAndChapterNumber";
    public static final String GET_CHAPTERS_BY_BOOK_NAME_PAGED = "/getChaptersByBookNamePaged";
    public static final String GET_IMAGES_BY_BOOK_NAME_AND_CHAPTER_NUMBER_PAGED =
            "/getImagesByBookNameAndChapterNumberPaged";
    public static final String GET_IMAGE_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_IMAGE_NUMBER =
            "/getImageByBookNameAndChapterAndChapterNumberAndImageNumber";

    public static final String CREATE_TEXT_BLOCK = "/createTextBlock";
    public static final String GET_TEXT_BLOCK_BY_BOOK_NAME_AND_CHAPTER_NUMBER = "/getTextBlockByBookNameAndChapterNumber";

    public static final String CREATE_AUDIO = "/createAudio";
    public static final String GET_AUDIO_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_AUDIO_NUMBER = "/getAudioByBookNameAndChapterNumberAndAudioNumber";
    public static final String GET_AUDIOS_BY_BOOK_NAME_AND_CHAPTER_NUMBER = "/getAudiosByBookNameAndChapterNumber";

    public static final String CREATE_VIDEO = "/createVideo";
    public static final String GET_VIDEOS_BY_BOOK_NAME_AND_CHAPTER_NUMBER = "/getVideosByBookNameAndChapterNumber";
    public static final String GET_VIDEO_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_VIDEO_NUMBER = "/getVideoByBookNameAndChapterNumberAndVideoNumber";

    public static final String CREATE_CHECKOUT_PAGE = "/createCheckoutPage";
    public static final String GET_CHECKOUT_SESSION_BY_SESSION_ID = "/getCheckoutSessionBySessionId";
    public static final String GET_STRIPE_CHECKOUT_SESSION_BY_SESSION_ID = "/getStripeCheckoutSessionBySessionId";

    public static final String CREATE_ACCOUNT = "/createAccount";
    public static final String GET_ACCOUNT = "/getAccount";

    public static final String GET_TOKEN_TRANSACTIONS = "/getTokenTransactions";
    public static final String STRIPE_WEBHOOKS = "/stripeWebhooks";

    public static final String GET_STRIPE_PRICE_BY_PRICE_ID = "/getStripePriceByPriceId";

    public static final String CREATE_YOUTUBE_VIDEO = "/createYouTubeVideo";
    public static final String GET_YOUTUBE_VIDEO_BY_YOUTUBE_VIDEO_ID = "/getYouTubeVideoByYouTubeVideoId";
    public static final String GET_YOUTUBE_VIDEOS_BY_BOOK_NUMBER = "/getYouTubeVideosByBookNumber";

    public static final String GET_PUBLIC_BOOK_DETAILS_CARD_BY_BOOK_NUMBER = "/getPublicBookDetailsCardByBookNumber";

    public static final String POPULATE_YOUTUBE_VIDEO_DATA_BY_PLAYLIST_ID_AND_BOOK_NUMBER = "/populateYoutubeVideoDataByPlaylistIdAndBookNumber";

    public static final String CREATE_YOUTUBE_VIDEO_UPLOAD = "/createYoutubeVideoUpload";
    public static final String MAKE_TOKEN_PAYMENT = "/makeTokenPayment";
    public static final String UPLOAD_TRANSLATED_YOUTUBE_BOOK = "/uploadTranslatedYoutubeBook";
    public static final String CREATE_YOUTUBE_VIDEO_DETAILS_TEMPLATE = "/createYoutubeVideoDetailsTemplate";

    public static final String POPULATE_VIDEO_DATA_FOR_ALL_SAVED_PLAYLISTS = "/populateVideoDataForAllSavedPlaylists";
    public static final String UPDATE_VIDEO_CREATION_MILESTONE = "/updateVideoCreationMilestone";
    public static final String GET_VIDEO_CREATION_MILESTONE = "/getVideoCreationMilestone";
    public static final String POPULATE_GIT_BOOKS = "/populateGitBooks";
    public static final String GET_GIT_FILE_BY_REPOSITORY_NAME_AND_FILE_KEY = "/getGitFileByRepositoryNameAndFileKey";
    public static final String GET_GIT_REPOSITORY_BY_REPOSITORY_NAME = "/getGitRepositoryByRepositoryName";


    public static final String ALL_ORIGINS = "*";
    public static final Gson GSON = new Gson();
}
