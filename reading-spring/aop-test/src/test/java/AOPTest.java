import bat.ke.qq.com.config.AppConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPTest {
	@Test
	public void test(){
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	}
}
