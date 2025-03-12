package book.data.service.activity.audio;


import static book.data.service.constants.Constants.S3_AUDIO_BUCKET;
import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_AUDIO;
import book.data.service.message.audio.CreateAudioRequest;
import book.data.service.message.audio.CreateAudioResponse;
import book.data.service.sqlmodel.audio.Audio;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import lombok.extern.slf4j.Slf4j;
import book.data.service.firebase.FirebaseService;
import book.data.service.manager.audio.AudioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreateAudioActivity {
    private AudioManager audioManager;
    private FirebaseService firebaseService;

    @Autowired
    public CreateAudioActivity(
        AudioManager audioManager,
        FirebaseService firebaseService
    ) {
        this.audioManager = audioManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_AUDIO)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody CreateAudioResponse createAudio(
        @RequestBody CreateAudioRequest createAudioRequest,
        @RequestHeader("Authorization") String authToken
    ) throws MalformedURLException, URISyntaxException {
        createAudioRequest.validate();
        String email = firebaseService.getEmail(authToken);
        Audio audio = audioManager.createAudio(
            createAudioRequest.getBookName(),
            Long.parseLong(createAudioRequest.getChapterNumber()),
            Long.parseLong(createAudioRequest.getAudioNumber()),
            createAudioRequest.getFileBytes(),
            createAudioRequest.getFileType(),
            S3_AUDIO_BUCKET,
            email
        );
        return CreateAudioResponse.builder()
            .audio(audio)
            .build();
    }
}
