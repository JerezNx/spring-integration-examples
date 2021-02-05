package xyz.jerez.spring.fastdfs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jerez.spring.fastdfs.domain.ByteArrayMultipartFile;
import xyz.jerez.spring.fastdfs.service.FastDfsTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class SpringFastdfsApplicationTests {

    @Autowired
    private FastDfsTemplate fastDfsTemplate;

    @Test
    void contextLoads() {
        File file = new File("C:\\Users\\Jerez\\Pictures\\头像\\小林.jpg");
        String path = fastDfsTemplate.uploadFile(file);
        System.out.println(fastDfsTemplate.getResAccessUrl(path));
    }

    @Test
    void upload() throws IOException {
        final ByteArrayMultipartFile file = new ByteArrayMultipartFile(
                "log.txt",
                "test".getBytes(StandardCharsets.UTF_8));
        final String url = fastDfsTemplate.uploadFile(file);
        System.out.println(fastDfsTemplate.getResAccessUrl(url));
    }

}
