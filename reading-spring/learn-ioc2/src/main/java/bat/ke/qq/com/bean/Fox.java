package bat.ke.qq.com.bean;

import org.springframework.beans.factory.annotation.Autowired;

public class Fox {

	private String name;
	private int age;

	@Autowired
	private User user;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Fox{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
