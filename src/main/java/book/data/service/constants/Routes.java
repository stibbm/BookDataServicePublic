package book.data.service.constants;

import com.google.gson.Gson;

public class Routes {
    public static final String CREATE_BOOK = "/createBook";
    public static final String CREATE_CHAPTER = "/createChapter";
    public static final String CREATE_IMAGE = "/createImage";
    public static final String GET_ALL_BOOKS_PAGED = "/getAllBooksPaged";
    public static final String GET_BOOK_BY_BOOK_NAME = "/getBookByBookName";
    public static final String GET_CHAPTER_BY_BOOK_NAME_AND_CHAPTER_NUMBER =
        "/getChapterByBookNameAndChapterNumber";
    public static final String GET_CHAPTERS_BY_BOOK_NAME_PAGED = "/getChaptersByBookNamePaged";
    public static final String GET_IMAGES_BY_BOOK_NAME_AND_CHAPTER_NUMBER_PAGED =
        "/getImagesByBookNameAndChapterNumberPaged";
    public static final String GET_IMAGE_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_IMAGE_NUMBER =
        "/getImageByBookNameAndChapterAndChapterNumberAndImageNumber";
	
	
	public static final String DELETE_BOOK_BY_NAME = "/deleteBookByName";
	

	public static final String SEARCH_BOOKS_BY_BOOK_TAGS = "/searchBooksByBookTags";
	public static final String SEARCH_BOOKS_BY_BOOK_CONTENT = "/searchBooksByBookContent";

	public static final String GET_CHAPTER_HEADERS_BY_BOOK_NUMBER = "/getChapterHeadersByBookNumber";


    public static final String ALL_ORIGINS = "*";
    public static final Gson GSON = new Gson();
}
