package book.data.service.service.git;

import book.data.service.service.file.FileService;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// get info about how many of each type of file exist in the
// directory and other metadata stuff

@Slf4j
@Service
public class GitFileStatsService {
    private FileService fileService;

    @Autowired
    public GitFileStatsService(FileService fileService) {
        this.fileService = fileService;
    }

    public String getRelativePathFromRepository(File repository, File file) {
        String repoPath = repository.getAbsolutePath();
        String filePath = file.getAbsolutePath();
        String relativeRepoPath = filePath.substring(repoPath.length());
        return relativeRepoPath;
    }

    public CodeLanguage determineProbableCodeLanguage(File gitDirectory) {
        Map<String, Long> codeFileCountMap = new HashMap<>();
        CodeLanguage[] codeLanguages = CodeLanguage.values();
        CodeLanguage codeLanguageWithMostFiles = codeLanguages[0];
        Long mostCount = 0L;
        for (CodeLanguage codeLanguage: codeLanguages) {
            Long codeLanguageCount = countFileType(gitDirectory, codeLanguage);
            if (codeLanguageCount >= mostCount) {
                mostCount = codeLanguageCount;
                codeLanguageWithMostFiles = codeLanguage;
            }
        }
        return codeLanguageWithMostFiles;
    }

    public Long countFileType(File dir, CodeLanguage codeLanguage) {
        log.info("count file type: " + dir.getAbsolutePath());
        Collection<File> files = FileUtils.listFiles(dir, new String[]{codeLanguage.getExtension()}, true);
        log.info("collection size = " + files.size());
        Integer fileSizeInt = files.size();
        Long codeFilesCountLong = fileSizeInt.longValue();
        return codeFilesCountLong;
    }

    public List<CodeLanguage> getCodeLanguagesFoundInDirectory(File dir) {
        List<CodeLanguage> codeLanguageList = new ArrayList<>();
        for (CodeLanguage codeLanguage: codeLanguageList) {
            Long fileCount = countFileType(dir, codeLanguage);
            if (fileCount > 0) {
                codeLanguageList.add(codeLanguage);
            }
        }
        return codeLanguageList;
    }

}
