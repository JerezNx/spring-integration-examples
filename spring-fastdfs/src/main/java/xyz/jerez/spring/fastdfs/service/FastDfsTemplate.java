package xyz.jerez.spring.fastdfs.service;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * fdfs操作类
 *
 * @author liqilin
 * @since 2020/12/3 17:29
 */
@Component
public class FastDfsTemplate {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        String fullPath = storePath.getFullPath();
        getResAccessUrl(fullPath);
        return fullPath;
    }

    public String uploadFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
            String thumbPath = thumbImageConfig.getThumbImagePath(storePath.getPath());
            System.out.println(thumbPath);
            return storePath.getFullPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] downloadFile(String filePath) {
        StorePath storePath = StorePath.parseFromUrl(filePath);
        return fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
    }

    public Boolean deleteFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(filePath);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 访问文件完整URL地址
     *
     * @param path 返回路径
     * @return 访问路径
     */
    public String getResAccessUrl(String path) {
        return fdfsWebServer.getWebServerUrl() + "/" + path;
    }

}
