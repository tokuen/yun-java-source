package bat.ke.qq.com.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class B {
	@Autowired
	private A a;

	public B(){
		System.out.println("调用了构造器B()");
	}


	public B(A a){
		System.out.println("调用了构造器B(A a)");
		this.a = a;
	}

	public A getA() {
		return a;
	}

	public void setA(A a) {
		System.out.println("调用了setA(A a)");
		this.a = a;
	}

//	@Override
//	public String toString() {
//		return "B{" +
//				"a=" + a +
//				'}';
//	}
}
