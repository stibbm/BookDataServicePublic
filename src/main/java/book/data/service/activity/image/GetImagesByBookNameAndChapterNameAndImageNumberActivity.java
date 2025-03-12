package book.data.service.activity.image;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_IMAGE_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_IMAGE_NUMBER;
import static book.data.service.constants.Routes.GSON;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.RequestLogManager;
import book.data.service.manager.image.ImageManager;
import book.data.service.message.image.GetImageByBookNameAndChapterNumberAndImageNumberRequest;
import book.data.service.message.image.GetImageByBookNameAndChapterNumberAndImageNumberResponse;
import book.data.service.sqlmodel.image.Image;
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
public class GetImagesByBookNameAndChapterNameAndImageNumberActivity {
    private ImageManager imageManager;
    private RequestLogManager requestLogManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetImagesByBookNameAndChapterNameAndImageNumberActivity(
        ImageManager imageManager,
        RequestLogManager requestLogManager,
        FirebaseService firebaseService
    ) {
        this.imageManager = imageManager;
        this.requestLogManager = requestLogManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_IMAGE_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_IMAGE_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetImageByBookNameAndChapterNumberAndImageNumberResponse getImageByBookNameAndChapterNumberAndImageNumber
        (@RequestBody GetImageByBookNameAndChapterNumberAndImageNumberRequest getImageByBookNameAndChapterNumberAndImageNumberRequest,
            @RequestHeader("Authorization") String authToken
        )
    {
        getImageByBookNameAndChapterNumberAndImageNumberRequest.validate();
        String createdBy = firebaseService.getEmail(authToken);
        requestLogManager.createRequestLog(
            GET_IMAGE_BY_BOOK_NAME_AND_CHAPTER_NUMBER_AND_IMAGE_NUMBER,
            GSON.toJson(getImageByBookNameAndChapterNumberAndImageNumberRequest)
        );
        Image image = imageManager.getImageByBookNameAndChapterNameAndImageNumber(
            getImageByBookNameAndChapterNumberAndImageNumberRequest.getBookName(),
            Long.parseLong(getImageByBookNameAndChapterNumberAndImageNumberRequest.getChapterNumber()),
            Long.parseLong(getImageByBookNameAndChapterNumberAndImageNumberRequest.getImageNumber()),
            createdBy
        );
        return GetImageByBookNameAndChapterNumberAndImageNumberResponse.builder()
            .image(image)
            .build();
    }

}
