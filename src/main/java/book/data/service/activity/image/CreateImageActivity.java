package book.data.service.activity.image;

import static book.data.service.constants.Constants.S3_IMAGE_BUCKET;
import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_IMAGE;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.image.ImageManager;
import book.data.service.message.image.CreateImageRequest;
import book.data.service.message.image.CreateImageResponse;
import book.data.service.sqlmodel.image.Image;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class CreateImageActivity {

    private ImageManager imageManager;
    private FirebaseService firebaseService;

    @Autowired
    public CreateImageActivity(
        ImageManager imageManager,
        FirebaseService firebaseService
    ) {
        this.imageManager = imageManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_IMAGE)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody CreateImageResponse createImage(
        @RequestBody CreateImageRequest createImageRequest,
        @RequestHeader("Authorization") String authToken
    ) throws MalformedURLException, URISyntaxException {
        createImageRequest.validate();
        String createdBy = firebaseService.getEmail(authToken);
        Image image = imageManager.createImage(
            createImageRequest.getBookName(),
            Long.parseLong(createImageRequest.getChapterNumber()),
            Long.parseLong(createImageRequest.getImageNumber()),
            S3_IMAGE_BUCKET,
            createdBy,
            createImageRequest.getFileBytes(),
            createImageRequest.getFileType()
        );
        return CreateImageResponse.builder()
            .image(image)
            .build();
    }
}
