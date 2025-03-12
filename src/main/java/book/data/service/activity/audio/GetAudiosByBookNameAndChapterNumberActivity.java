package book.data.service.activity.audio;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_AUDIOS_BY_BOOK_NAME_AND_CHAPTER_NUMBER;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.audio.AudioManager;
import book.data.service.message.audio.GetAudiosByBookNameAndChapterNumberRequest;
import book.data.service.message.audio.GetAudiosByBookNameAndChapterNumberResponse;
import book.data.service.sqlmodel.audio.Audio;
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
public class GetAudiosByBookNameAndChapterNumberActivity {
    private AudioManager audioManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetAudiosByBookNameAndChapterNumberActivity(
        AudioManager audioManager,
        FirebaseService firebaseService
    ) {
        this.audioManager = audioManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_AUDIOS_BY_BOOK_NAME_AND_CHAPTER_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetAudiosByBookNameAndChapterNumberResponse getAudiosByBookNameAndChapterNumber(
        @RequestBody GetAudiosByBookNameAndChapterNumberRequest request,
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        List<Audio> audioList = audioManager.getAudiosByBookNameAndChapterNumber(
            request.getBookName(),
            Long.parseLong(request.getChapterNumber()),
            email
        );
        return GetAudiosByBookNameAndChapterNumberResponse.builder().audioList(audioList).build();
    }
}
