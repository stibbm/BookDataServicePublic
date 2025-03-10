package book.data.service.service.filter;

import book.data.service.sqlmodel.tag.SearchTermFilterType;
import book.data.service.sqlmodel.textblock.TextBlock;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TextBlockFilterService {
    public List<TextBlock> selectTextBlockBySearchTerms(List<TextBlock> textBlockList, List<String> searchTerms, SearchTermFilterType searchTermFilterType) {
        if (searchTermFilterType == SearchTermFilterType.MATCH_ALL_SEARCH_TERMS) {
            return selectTextBlocksWithAllSearchTerms(textBlockList, searchTerms);
        }
        else if (searchTermFilterType == SearchTermFilterType.MATCH_AT_LEAST_ONE_SEARCH_TERM) {
            return selectTextBlocksWithAtLeastOneSearchTerm(textBlockList, searchTerms);
        }
        else {
            throw new IllegalArgumentException("Invalid SearchTermFilterType");
        }
    }

    private List<TextBlock> selectTextBlocksWithAtLeastOneSearchTerm(List<TextBlock> textBlockList, List<String> searchTerms) {
        List<TextBlock> textBlocksWithAtLeastOneSearchTerm = textBlockList.stream().filter(textBlock -> doesTextBlockContainAnySearchTerms(textBlock, searchTerms)).toList();
        return textBlocksWithAtLeastOneSearchTerm;
    }

    private List<TextBlock> selectTextBlocksWithAllSearchTerms(List<TextBlock> textBlockList, List<String> searchTerms) {
        List<TextBlock> textBlocksWithAllSearchTerms = textBlockList.stream().filter(textBlock -> doesTextBlockContainAllSearchTerms(textBlock, searchTerms)).toList();
        return textBlocksWithAllSearchTerms;
    }

    private boolean doesTextBlockContainAnySearchTerms(TextBlock textBlock, List<String> searchTermList) {
        searchTermList = makeSearchTermsLowerCase(searchTermList);
        String textBlockText = textBlock.getTextBlockText().toLowerCase();
        String[] textBlockTextTokenArray = textBlockText.split("\\s+");
        List<String> textBlockTokenList = List.of(textBlockTextTokenArray);
        for (String searchTerm: searchTermList) {
            if (textBlockTokenList.contains(searchTerm)) {
                return true;
            }
        }
        return false;
    }

    private boolean doesTextBlockContainAllSearchTerms(TextBlock textBlock, List<String> searchTerms) {
        searchTerms = makeSearchTermsLowerCase(searchTerms);
        String textBlockText = textBlock.getTextBlockText().toLowerCase();
        String[] textBlockTextTokenArray = textBlockText.split("\\s+");
        List<String> textBlockTokenList = List.of(textBlockTextTokenArray);
        if (textBlockTokenList.containsAll(searchTerms)) {
            return true;
        }
        else {
            return false;
        }
    }

    private List<String> makeSearchTermsLowerCase(List<String> searchTerms) {
        List<String> lowerCaseSearchTerms = searchTerms.stream().map(String::toLowerCase).toList();
        return lowerCaseSearchTerms;
    }
}
