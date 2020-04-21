package bat.ke.qq.com.bean;

import bat.ke.qq.com.service.MyService;
import org.springframework.stereotype.Component;


public class Fox {

	private Cat cat;

	public Fox() {
	}

	public Fox(Cat cat) {
		System.out.println(cat+"===Fox(Cat cat)=====");
	}

	public Fox(Cat cat,Tiger tiger) {
		System.out.println(cat+"===Fox(Cat cat,Tiger tiger)=====");
	}
//
	public Fox(Cat cat, MyService myService) {
		System.out.println(cat+"=== Fox(Cat cat, MyService myService) =====");
	}

	public Cat getCat() {
		return cat;
	}


	public void setCat(Cat cat) {
		System.out.println(cat+"======setCat(Cat cat)");
		this.cat = cat;
	}

	@Override
	public String toString() {
		return "Fox{" +
				"cat=" + cat +
				'}';
	}

	//	public Fox(User user,Cat cat) {
//		System.out.println(user+"===Fox(User user,Cat cat) =====");
//	}
}
