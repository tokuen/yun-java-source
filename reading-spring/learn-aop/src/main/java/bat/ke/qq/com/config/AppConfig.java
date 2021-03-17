package bat.ke.qq.com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("bat.ke.qq.com")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppConfig {


}
