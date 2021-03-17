package bat.ke.qq.com.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class A {
	@Autowired
	private B b;

	public A(){
		System.out.println("调用了构造器A()");
	}

	public A(B b){
		System.out.println("调用了构造器A(B b)");
		this.b = b;
	}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		System.out.println("调用了setB(B b)");
		this.b = b;
	}

	@Override
	public String toString() {
		return "A{" +
				"b=" + b +
				'}';
	}
}
