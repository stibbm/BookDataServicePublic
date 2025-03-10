package book.data.service.service.file;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class FileService {
    public void writeToFile(byte[] fileBytes, String fileName) throws IOException {
        File file = new File(fileName);
        FileUtils.writeByteArrayToFile(file, fileBytes);
    }

    public void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    public byte[] readFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] fileBytes = FileUtils.readFileToByteArray(file);
        return fileBytes;
    }

    public byte[] readFileReturnsNullOnFailure(String fileName) {
        try {
            return readFile(fileName);
        }
        catch (Exception e) {
            return null;
        }
    }

    public List<String> listFilesInDirectoryAndSubdirectories(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() == false) {
            throw new IllegalArgumentException("directory does not exist");
        }
        if (directory.isDirectory() == false) {
            throw new IllegalArgumentException("not a directory");
        }

        Collection<File> files = FileUtils.listFiles(
                directory,
                TrueFileFilter.INSTANCE, // No file filter, accept all files
                TrueFileFilter.INSTANCE  // Accept all directories for recursive search
        );
        List<String> filePathList = files.stream().map(File::getAbsolutePath).toList();
        return filePathList;
    }

    public boolean createDirectoryIfDoesNotExist(String dirPath) {
        File dir = new File(dirPath);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                return true;
            }
            else {
                throw new IllegalArgumentException("a file already exists at that location");
            }
        }
        else {
            return dir.mkdirs();
        }
    }

    public boolean isCodeFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File is null");
        }
        if (file.isDirectory()) {
            throw new IllegalArgumentException("file passed is a directory, must be a file");
        }
        String ext = file.getAbsolutePath();
        ext = ext.substring(ext.lastIndexOf(".")+1);
        for (CodeLanguage codeLanguage : CodeLanguage.values()) {
            if (ext.equalsIgnoreCase(codeLanguage.getExtension())) {
                return true;
            }
        }
        return false;
    }

    public CodeLanguage determineFileCodeLanguage(String filePath) {
        CodeLanguage[] codeLanguages = CodeLanguage.values();
        for (CodeLanguage codeLanguage: codeLanguages) {
            String fileType = filePath.substring(filePath.lastIndexOf(".")+1);
            if (codeLanguage.getExtension().equalsIgnoreCase(fileType)) {
                return codeLanguage;
            }
        }
        return NOTCODE;
    }

}
