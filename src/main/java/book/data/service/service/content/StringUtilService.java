package book.data.service.service.content;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StringUtilService {
    public Long extractLongFromDirtyString(String dirtyString) {
        String extractedString = "";
        for (int i=0;i<dirtyString.length();i++) {
            char ch = dirtyString.charAt(i);
            if (Character.isDigit(ch)) {
                extractedString += ch;
            }
        }
        return Long.parseLong(extractedString);
    }

    public String extractFileType(String filename) {
        String fileType = filename.substring(filename.lastIndexOf(".")+1);
        return fileType;
    }

    public List<String> splitTextWithoutBreakingSentences(String text, int softCutoff) {
        String[] sentencesArray = text.split("\\.");
        List<String> textChunksList = new ArrayList<>();
        String textChunk = "";
        for (String sentence: sentencesArray) {
            textChunk = textChunk + sentence + ".";
            if (textChunk.length() > softCutoff) {
                textChunksList.add(textChunk);
                textChunk = "";
            }
        }
        if (textChunk.length()>0) {
            textChunksList.add(textChunk);
        }
        return textChunksList;
    }

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
