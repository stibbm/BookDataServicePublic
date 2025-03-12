package book.data.service.service.image;

import book.data.service.model.ImageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Service
public class ImageService {
    private ResourceLoader resourceLoader;

    @Autowired
    public ImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public ImageData getImageData(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        byte[] imageBytes =  Files.readAllBytes(resource.getFile().toPath());
        String fileType = resource.getFile().getName();
        fileType = fileType.substring(fileType.lastIndexOf(".")+1);
        ImageData imageData = ImageData.builder()
                .imageBytes(imageBytes)
                .imageFileType(fileType)
                .build();
        return imageData;
    }
}
