package book.data.service.service.git;


import book.data.service.service.content.CommandLineService;
import book.data.service.service.file.FileService;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GitService {

    private CommandLineService commandLineService;
    private FileService fileService;

    @Autowired
    public GitService(CommandLineService commandLineService, FileService fileService) {
        this.commandLineService = commandLineService;
        this.fileService = fileService;
    }

    public String cloneRepositoryToDirectory(String sshRepository, String directoryPath)
        throws IOException, InterruptedException {
        String[] cloneCommand = new String[]{
            "git", "clone", sshRepository, directoryPath
        };
        String commandLine = String.join(" ", cloneCommand);
        log.info("commandLine: " + commandLine);
        String result = commandLineService.executeCommandArray(cloneCommand);
        log.info("command result");
        return result;
    }

    public String cloneRepository(String sshRepository) throws IOException, InterruptedException {
        String[] cloneCommand = new String[]{
            "git", "clone", sshRepository
        };
        String commandLine = String.join(" ", cloneCommand);
        log.info("commandLine: " + commandLine);
        String result = commandLineService.executeCommandArray(cloneCommand);
        log.info("command result");
        return result;
    }

    public boolean createGitDirectoryIfDoesNotExist(String directoryPath) {
        boolean success = fileService.createDirectoryIfDoesNotExist(directoryPath);
        if (success == false) {
            throw new RuntimeException("failed to create the git directory");
        }
        return success;
    }

}
