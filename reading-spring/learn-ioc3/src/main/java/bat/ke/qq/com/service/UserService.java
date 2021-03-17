package bat.ke.qq.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;



public class UserService {


	public UserService(MyService myService) {
		System.out.println(myService);
		System.out.println("=====UserService======");
	}
}
