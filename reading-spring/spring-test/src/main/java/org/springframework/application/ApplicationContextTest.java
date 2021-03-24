package org.springframework.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextTest {
	public static void main(String[] args) {
		//xml 容器
		ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("application.xml");
	}
}
