package book.data.service.clientwrapper;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

@Slf4j
@Component
public class S3ClientWrapper {
    private S3Client s3Client;
    private AmazonS3 amazonS3;

    @Autowired
    public S3ClientWrapper(
            S3Client s3Client,
            AmazonS3 amazonS3
    ) {
        this.s3Client = s3Client;
        this.amazonS3 = amazonS3;
    }

    public Vector<String> listS3Files(String bucket, String prefix) {
        Vector<String> filesList = new Vector<>();
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix(prefix)
                .build();
        ListObjectsV2Iterable listObjectsV2Iterable = s3Client.listObjectsV2Paginator(listObjectsV2Request);
        for (ListObjectsV2Response listObjectsV2Response : listObjectsV2Iterable) {
            listObjectsV2Response.contents()
                    .forEach((software.amazon.awssdk.services.s3.model.S3Object object) -> {
                        String key = object.key();
                        filesList.add(key);
                    });
        }
        return filesList;
    }

    public S3Object getS3File(String s3BucketName, String s3FilePath) {
        return amazonS3.getObject(s3BucketName, s3FilePath);
    }

    public byte[] getS3FileBytes(String s3BucketName, String s3FilePath) throws IOException {
        S3Object s3Object = getS3File(s3BucketName, s3FilePath);
        return IOUtils.toByteArray(s3Object.getObjectContent());
    }

    public boolean createS3File(
            byte[] fileBytes,
            String s3BucketName,
            String s3FilePath
    ) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileBytes.length);
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                s3BucketName,
                s3FilePath,
                byteArrayInputStream,
                metadata
        );
        PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);
        return true;
    }
}