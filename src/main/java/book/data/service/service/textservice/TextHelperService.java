package book.data.service.service.textservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TextHelperService {
    public List<String> splitTextIntoChunks(
            String text,
            int chunkSize
    ) {
        char[] charsArray = text.toCharArray();
        String textChunk = "";
        List<String> textChunksList = new ArrayList<>();
        for (int i=0;i<charsArray.length;i++) {
            textChunk  = textChunk + charsArray[i];
            if (textChunk.length() > chunkSize) {
                textChunksList.add(textChunk);
                textChunk = "";
            }
        }
        textChunksList.add(textChunk);
        return textChunksList;
    }
}
