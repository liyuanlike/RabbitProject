package system.fastdfs.starter.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(FdfsClientConfig.class)
@ComponentScan(basePackages = {"system.fastdfs.starter"})
@PropertySource(value = {"classpath:fdfs.properties"},ignoreResourceNotFound = true)
public class StarterConfig {
}
