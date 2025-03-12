package book.data.service.activity.textblock;

import static book.data.service.constants.Routes.CREATE_TEXT_BLOCK;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.textblock.TextBlockManager;
import book.data.service.message.textblock.CreateTextBlockRequest;
import book.data.service.message.textblock.CreateTextBlockResponse;
import book.data.service.sqlmodel.textblock.TextBlock;
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
public class CreateTextBlockActivity {
    private TextBlockManager textBlockManager;
    private FirebaseService firebaseService;

    @Autowired
    public CreateTextBlockActivity(
        TextBlockManager textBlockManager,
        FirebaseService firebaseService
    ) {
        this.textBlockManager = textBlockManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_TEXT_BLOCK)
    @CrossOrigin("*")
    public @ResponseBody CreateTextBlockResponse createTextBlock(
        @RequestBody CreateTextBlockRequest createTextBlockRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        createTextBlockRequest.validate();
        String email = firebaseService.getEmail(authToken);
        TextBlock textBlock = textBlockManager.createLargeTextBlock(
            createTextBlockRequest.getBookName(),
            Long.parseLong(createTextBlockRequest.getChapterNumber()),
            createTextBlockRequest.getTextBlockText(),
            email
        );
        CreateTextBlockResponse createTextBlockResponse = CreateTextBlockResponse.builder()
            .textBlock(textBlock)
            .build();
        return createTextBlockResponse;
    }
}
