package bat.ke.qq.com.bean;

import bat.ke.qq.com.anno.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Dao
@Component
public class Fox {


	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
