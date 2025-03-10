package book.data.service.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3ClientBean {
    @Bean
    public S3Client provideS3ClientBean() {
        return S3Client.create();
    }
}
