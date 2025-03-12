package book.data.service.service.git;

import book.data.service.constants.Constants;
import book.data.service.manager.chapter.ChapterManager;
import book.data.service.model.CodeLanguage;
import book.data.service.service.file.FileService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import book.data.service.sqlmodel.chapter.Chapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GitRepositoryStatsService {

    private ChapterManager chapterManager;
    private FileService fileService;

    @Autowired
    public GitRepositoryStatsService(ChapterManager chapterManager, FileService fileService) {
        this.chapterManager = chapterManager;
        this.fileService = fileService;
    }

    public CodeLanguage getCodeLanguage(Chapter chapter) {
        return fileService.determineFileCodeLanguage(chapter.getChapterName());
    }

    public Map<CodeLanguage, Long> countCodeLanguageFiles(String repositoryName,
        String createdBy) {
        List<Chapter> chapterList = chapterManager.getChaptersByBookNamePaged(repositoryName,
            Constants.PAGE_ZERO,
            Constants.MAX_PAGE_SIZE, createdBy);
        Map<CodeLanguage, Long> countLanguageMap = chapterList.stream()
            .collect(Collectors.groupingBy(
                this::getCodeLanguage,
                Collectors.counting()
            ));
        return countLanguageMap;
    }

    public CodeLanguage getMostFrequentCodeLanguage(String repositoryName, String createdBy) {
        Map<CodeLanguage, Long> languageCounts = countCodeLanguageFiles(repositoryName, createdBy);
        return languageCounts.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }
}
