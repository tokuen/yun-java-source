package bat.ke.qq.com.config;

import bat.ke.qq.com.bean.Fox;
import bat.ke.qq.com.bean.MyImportBeanDefinitionRegistrar;
import bat.ke.qq.com.bean.MyImportSelector;
import bat.ke.qq.com.bean.User;
import org.springframework.context.annotation.*;

import java.util.HashMap;

@ComponentScan("bat.ke.qq.com")
@Configuration
//@Import(value = MyImportBeanDefinitionRegistrar.class)
public class AppConfig {

	@Bean
	public User user(){
		return new User();
	}

//	@Bean
//	public Fox fox(){
//		//System.out.println(user());
//		//System.out.println("fox()");
//		return new Fox();
//	}



}
