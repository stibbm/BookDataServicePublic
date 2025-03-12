package book.data.service.constants;

import com.google.gson.Gson;

public class Constants {
    public static final Boolean ACCOUNT_UNVERIFIED = false;
    public static final String S3_IMAGE_BUCKET = "<s3 image bucket>";
    public static final String S3_AUDIO_BUCKET = "<s3 audio bucket>";
    public static final String S3_VIDEO_BUCKET = "<s3 video bucket>";
    public static final String S3_PREFIX = "<s3 prefix>";
    public static final String S3_PREFIX_TWO = "<s3 prefix two>";
    public static final String S3_AUDIO_PREFIX = "<s3 audio prefix>";
    public static final String S3_VIDEO_PREFIX = "<s3 video prefix>";
    public static final String S3_YOUTUBE_VIDEO_PREFIX = "<s3 youtube video prefix>";
    public static final String S3_THUMBNAILS_PREFIX = "<s3 thumbnails prefix>";
    public static final Integer TEXT_BLOCK_CHUNK_SIZE = 3000;
    public static final Integer MAX_PAGE_SIZE = 1000000;
    public static final Integer PAGE_ZERO = 0;

    public static final String YOUTUBE_VIDEO_URL_TEMPLATE = "https://www.youtube.com/watch?v={youTubeVideoId}";
    public static final Gson GSON = new Gson();

    public static final Long TEN_SECONDS_IN_MILLI = 10 * 1000L;
    public static final Long OPEN_API_COST_PER_MILLION_TOKENS_IN_DOLLARS = 30L;
    public static final Long POLLY_API_COST_PER_MILLION_CHARS_IN_DOLLARS = 16L;

    public static final Long OPEN_API_COST_PER_MILLION_TOKENS_IN_CENTS = OPEN_API_COST_PER_MILLION_TOKENS_IN_DOLLARS * 100L;
    public static final Long POLLY_API_COST_PER_MILLION_CHARS_IN_CENTS = POLLY_API_COST_PER_MILLION_CHARS_IN_DOLLARS * 100L;

    public static final Long ONE_MILLION = 1000000L;
    public static final Long TOKEN_COST_PER_THOUSAND_IN_DOLLARS = 1L;
    public static final Long ONE_THOUSAND = 1000L;
    public static final Long YOUTUBE_VIDEO_CHAPTER_COUNT = 5L;
    public static final Double PER_MILLION_CHARS_TRUE_COST_ESTIMATE_DOLLARS = 76.22;
    public static final Double PER_MILLION_CHARS_TRUE_COST_ESTIMATE_CENTS = PER_MILLION_CHARS_TRUE_COST_ESTIMATE_DOLLARS * 100;
    public static final Double PRICE_MULTIPLIER = 1.3;
    public static final Double PER_MILLION_CHARS_PRICE_DOLLARS = PER_MILLION_CHARS_TRUE_COST_ESTIMATE_DOLLARS * PRICE_MULTIPLIER;

    public static final Long MAX_PRE_TRANSLATION_TEXT_SIZE = 40000L;
    public static final Long PRICE_CENTS_TO_TOKENS_MULTIPLIER = 10L;
    public static final Long PRICE_DOLLARS_TO_TOKENS_MULTIPLIER = PRICE_CENTS_TO_TOKENS_MULTIPLIER * 100L;

    public static final Object TOKEN_TRANSACTION_LOCK = new Object();
    public static final Object UPLOAD_TRANSLATED_YOUTUBE_BOOK_LOCK = new Object();
    public static final Long NO_VIEWS = 0L;
}

