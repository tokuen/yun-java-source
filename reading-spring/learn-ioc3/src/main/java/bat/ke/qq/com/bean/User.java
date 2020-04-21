package bat.ke.qq.com.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class User {

	@Autowired
	private Fox fox;


	public User() {

		System.out.println("=====user====");
	}


	public Fox getFox() {
		return fox;
	}
//
	public void setFox(Fox fox) {
		System.out.println(fox+"=============");
		this.fox = fox;
	}
}
