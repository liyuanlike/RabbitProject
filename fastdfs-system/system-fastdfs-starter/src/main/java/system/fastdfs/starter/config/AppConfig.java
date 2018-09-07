package system.fastdfs.starter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${fdfs.tracker-list[0]}")
    private String fdfsUrl;


    public String getFdfsUrl() {
        return fdfsUrl.split(":")[0];
    }

    public void setFdfsUrl(String fdfsUrl) {
        this.fdfsUrl = fdfsUrl;
    }

}
