import bat.ke.qq.com.bean.Fox;
import bat.ke.qq.com.config.AppConfig;
import bat.ke.qq.com.dao.FoxDao;
import bat.ke.qq.com.dao.IFoxDao;
import bat.ke.qq.com.proxy.MyInvocationHandler;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Proxy;

public class AopTest {

	@Test
	public void test(){
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		IFoxDao dao = (IFoxDao) context.getBean(FoxDao.class);
		dao.query();
		dao.query("fox");
		dao.query(new Fox());

//		IFoxDao dao = (IFoxDao) Proxy.newProxyInstance(AopTest.class.getClassLoader(),
//				new Class[]{IFoxDao.class},new MyInvocationHandler(new FoxDao()));
//		dao.query();




	}


}
