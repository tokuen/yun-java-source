package com.jiagouedu.bean;

import org.springframework.stereotype.Component;

@Component
public abstract class Animal {

	public Animal() {
		System.out.println("=====Animal====");
	}
}
