package book.data.service.activity.textblock;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_TEXT_BLOCK_BY_BOOK_NAME_AND_CHAPTER_NUMBER;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.textblock.TextBlockManager;
import book.data.service.message.textblock.GetTextBlockByBookNameAndChapterNumberRequest;
import book.data.service.message.textblock.GetTextBlockByBookNameAndChapterNumberResponse;
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
public class GetTextBlockByBookNameAndChapterNumberActivity {

    private TextBlockManager textBlockManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetTextBlockByBookNameAndChapterNumberActivity(
        TextBlockManager textBlockManager,
        FirebaseService firebaseService
    ) {
        this.textBlockManager = textBlockManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_TEXT_BLOCK_BY_BOOK_NAME_AND_CHAPTER_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetTextBlockByBookNameAndChapterNumberResponse getTextBlockByBookNameAndChapterNumber(
        @RequestBody GetTextBlockByBookNameAndChapterNumberRequest getTextBlockByBookNameAndChapterNumberRequest,
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        TextBlock textBlock = textBlockManager.getCombinedTextBlockByBookNameAndChapterNumber(
            getTextBlockByBookNameAndChapterNumberRequest.getBookName(),
            Long.parseLong(getTextBlockByBookNameAndChapterNumberRequest.getChapterNumber()),
            email
        );
        return GetTextBlockByBookNameAndChapterNumberResponse.builder()
            .textBlock(textBlock)
            .build();
    }
}
