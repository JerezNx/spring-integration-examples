package xyz.jerez.spring.fastdfs.domain;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author liqilin
 * @since 2021/2/4 17:29
 */
@Data
public class ByteArrayMultipartFile implements MultipartFile {

    String name;

    @NonNull
    String originalFilename;

    String contentType;

    @NonNull
    byte[] bytes;

    @Override
    public boolean isEmpty() {
        return bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(File destination) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(destination);
            outputStream.write(bytes);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

}
