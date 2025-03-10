package book.data.service.factory;

import book.data.service.sqlmodel.textblock.TextBlock;
import book.data.service.sqlmodel.textblock.TextBlockId;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TextBlockFactory {

    public TextBlock buildCombinedTextBlock(List<TextBlock> textBlockList) {
        String combinedText = "";
        for (TextBlock textBlock : textBlockList) {
            combinedText += textBlock.getTextBlockText();
        }
        TextBlockId textBlockId = new TextBlockId(0L,
            textBlockList.get(0).getTextBlockId().getChapter());
        TextBlock textBlock = new TextBlock(textBlockId, combinedText.toString(),
            textBlockList.get(0).getCreatedBy(), textBlockList.get(0).getCreatedEpochMilli());
        return textBlock;
    }
}
