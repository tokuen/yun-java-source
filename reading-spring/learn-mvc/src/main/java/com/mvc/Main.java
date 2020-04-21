package com.mvc;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		try {
			SpringApplication.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
