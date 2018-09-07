package system.fastdfs.starter.processors;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import system.fastdfs.starter.config.AppConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author liuxun
 * @apiNote FastDFS 文件上传下载包装类
 */
@Component
public class FdfsClientWrapper {
    private final Logger logger = LoggerFactory.getLogger(FdfsClientWrapper.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private AppConfig appConfig;

    /**
     * @apiNote 上传具体目录的文件
     * @param file 指定目录的文件
     * @throws IOException
     */
    public String uploadFile(File file) throws IOException {
        StorePath storePath = storageClient.uploadFile(new FileInputStream(file),
                file.length(),
                FilenameUtils.getExtension(file.getName()),
                null);
        return getResAccessUrl(storePath);
    }

    /**
     * @param file 文件对象
     * @return 文件访问地址
     * @throws IOException IO异常
     * @apiNote 上传文件
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),
                file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()),
                null);
        return getResAccessUrl(storePath);
    }

    /**
     * @apiNote 上传缩略图文件
     * @param file  文件对象
     * @return  文件访问路径
     * @throws IOException
     */
    public String uploadThumbFile(MultipartFile file,String extension) throws IOException {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(),
                file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()),
                null);
        return getResAccessUrl(storePath);
    }





    /**
     * @param content       文件内容
     * @param fileExtension 文件后缀名
     * @return
     * @apiNote 将一段字符串生成一个文件上传
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(storePath);
    }

    // 封装图片完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = "http://" + appConfig.getFdfsUrl()+ "/" + storePath.getFullPath();
        return fileUrl;
    }

    /**
     * @apiNote 删除文件
     * @param fileUrl 文件路径
     * @return
     */
    public Boolean deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            logger.error("fileUrl 不合法");
            return false;
        }
        Boolean isSuccess = true;
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.error(e.getMessage());
            isSuccess = false;
        } finally {
            logger.info("删除文件{}", isSuccess ? "成功" : "失败");
            return isSuccess;
        }
    }
}
