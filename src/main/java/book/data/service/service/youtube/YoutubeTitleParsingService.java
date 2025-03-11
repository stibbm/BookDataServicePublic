package book.data.service.service.youtube;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class YoutubeTitleParsingService {

    // replaces the chapters listed in either video or description with new chapter values
    public String replaceChaptersInText(String text, Long newStartChapter, Long newEndChapter) {
        String chaptersText = findLastMatch(text);
        String newChaptersText = newStartChapter.toString() + "-" + newEndChapter;
        String newText = text.replace(chaptersText, newChaptersText);
        return newText;
    }

    public String findLastMatch(String text) {
        // Regex pattern to find numbers formatted as 'number-number'
        String regex = "\\d+-\\d+";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // Variable to store the last match found
        String lastMatch = null;

        // Find all matches and keep the last one
        while (matcher.find()) {
            lastMatch = matcher.group();
        }

        return lastMatch;
    }
}
