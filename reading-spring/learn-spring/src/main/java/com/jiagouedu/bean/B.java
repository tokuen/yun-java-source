package com.jiagouedu.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class B {

	@Autowired
	private A a;

	public B(){

	}
	public B(A a){
		this.a = a;
		System.out.println(a);
	}
}
