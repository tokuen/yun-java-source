package org.springframework.demo;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * 自定义MVC流程
 */
public class MyDispatchServlet extends HttpServlet {

	//保存application.properties 配置
	private Properties contextConfig = new Properties();

	//保存className
	private List<String> classNames = new ArrayList<>();

	private Map<String, Object> ioc = new HashMap<>();

	@Override
	public void init(ServletConfig config) throws ServletException {

//		1加载配置
		doLoadConfig(config.getInitParameter("contextConfigLocation"));
//		2扫描注解
		doScanner(contextConfig.getProperty("scanPackage"));
//		3初始化bean
		doInstance();
//		4注入bean
		doAutoWrite();
//		5设置请求路径和方法的映射关系
		initHandlerMapping();
	}

	private void initHandlerMapping() {

	}

	private void doAutoWrite() {

	}

	private void doInstance() {
		for (String className : classNames) {
			try {
				Class<?> clazz = Class.forName(className);
				//只有带注解的类有必要进行实例化
				if (clazz.isAnnotation()) {
					if (clazz.isAnnotationPresent(Controller.class)) {
						Object instance = clazz.newInstance();
						//key 首字母小写
						ioc.put(toLowerFirstCase(clazz.getSimpleName()), instance);
					} else if(clazz.isAnnotationPresent(Service.class)){
						//自定义类名
						Service serviceAnnotation = clazz.getAnnotation(Service.class);
						String beanName = serviceAnnotation.value();
						Object instance = clazz.newInstance();
						if(beanName==null || beanName.trim().length()==0){
							beanName = toLowerFirstCase(clazz.getSimpleName());
						}
						for (Class<?> i:clazz.getInterfaces()) {
							if(ioc.containsKey(beanName)){
								throw new Exception("the class instance "+ beanName +" exists");
							}
							ioc.put(beanName, instance);
						}
					}
				} else {
					continue;
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private String toLowerFirstCase(String simpleName) {
		char[] chars = simpleName.toCharArray();
		chars[0] += 32;
		return String.valueOf(chars);
	}

	//记录所有的className
	private void doScanner(String scanPackage) {
		URL resource = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
		File file = new File(resource.getFile());
		for (File listFile : file.listFiles()) {
			if (listFile.isDirectory()) {
				doScanner(scanPackage + "." + file.getName());
			} else {
				if (!listFile.getName().endsWith(".class")) {
					continue;
				} else {
					String className = scanPackage + "." + listFile.getName().replace(".class", "");
					classNames.add(className);
				}
			}
		}
	}

	//加载配置文件
	private void doLoadConfig(String contextConfigLocation) {
		//直接从类的加载路径获取spring的主配置文件
		InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
		try {
			contextConfig.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//6调用
		doDispatch(req, resp);
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {

	}
}
