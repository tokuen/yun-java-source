package bat.ke.qq.com.proxy;

import bat.ke.qq.com.dao.FoxDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
	private Object obj;

	public MyInvocationHandler(Object obj) {
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("==jdk==");

		return method.invoke(obj,args);
	}
}
