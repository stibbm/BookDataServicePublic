package book.data.service.activity.image;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_IMAGES_BY_BOOK_NAME_AND_CHAPTER_NUMBER_PAGED;
import static book.data.service.constants.Routes.GSON;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.RequestLogManager;
import book.data.service.manager.image.ImageManager;
import book.data.service.message.image.GetImagesByBookNameAndChapterNumberPagedRequest;
import book.data.service.message.image.GetImagesByBookNameAndChapterNumberPagedResponse;
import book.data.service.sqlmodel.image.Image;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GetImagesByBookNameAndChapterNumberPagedActivity {
    private ImageManager imageManager;
    private RequestLogManager requestLogManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetImagesByBookNameAndChapterNumberPagedActivity(
        ImageManager imageManager,
        RequestLogManager requestLogManager,
        FirebaseService firebaseService
    ) {
        this.imageManager = imageManager;
        this.requestLogManager = requestLogManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_IMAGES_BY_BOOK_NAME_AND_CHAPTER_NUMBER_PAGED)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetImagesByBookNameAndChapterNumberPagedResponse
    getImagesByBookNameAndChapterNumber(@RequestBody
    GetImagesByBookNameAndChapterNumberPagedRequest getImagesByBookNameAndChapterNumberPagedRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        getImagesByBookNameAndChapterNumberPagedRequest.validate();
        String createdBy = firebaseService.getEmail(authToken);
        requestLogManager.createRequestLog(
            GET_IMAGES_BY_BOOK_NAME_AND_CHAPTER_NUMBER_PAGED,
            GSON.toJson(getImagesByBookNameAndChapterNumberPagedRequest)
        );
        List<Image> imageList = imageManager.getImagesByBookNameAndChapterNumberPaged(
            getImagesByBookNameAndChapterNumberPagedRequest.getBookName(),
            Long.parseLong(getImagesByBookNameAndChapterNumberPagedRequest.getChapterNumber()),
            Integer.parseInt(getImagesByBookNameAndChapterNumberPagedRequest.getPageNumber()),
            Integer.parseInt(getImagesByBookNameAndChapterNumberPagedRequest.getPageSize()),
            createdBy
        );
        return GetImagesByBookNameAndChapterNumberPagedResponse.builder()
            .imageList(imageList)
            .build();
    }

}
