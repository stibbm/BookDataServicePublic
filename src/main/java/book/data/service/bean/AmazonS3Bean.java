package book.data.service.bean;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Bean {
    @Bean
    public AmazonS3 provideAmazonS3Bean() {
        return AmazonS3ClientBuilder.defaultClient();
    }

}
