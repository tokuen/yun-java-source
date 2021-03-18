package org.springframework.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;

/**
 * 自定义MVC流程
 * 模板模式 init方法
 * 策略模式 handlerMapping
 * 单例模式
 * 工厂模式
 * 代理模式
 * 原型模式
 * 适配器模式
 * 委派模式
 * 享元模式
 * 观察者模式
 * 装饰者模式
 */
public class MyDispatchServlet extends HttpServlet {

	//保存application.properties 配置
	private Properties contextConfig = new Properties();

	//保存className
	private List<String> classNames = new ArrayList<>();

	//实例化的bean
	private Map<String, Object> ioc = new HashMap<>();

//	private Map<String, Method> handlerMapping = new HashMap<>();
	private List<HandleMapping> handlerMapping = new ArrayList<HandleMapping>();

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
		for (Map.Entry<String, Object> entry : ioc.entrySet()) {
			String baseUrl = "";
			//获取所有的方法
			Class<?> clazz = entry.getValue().getClass();
			if (clazz.isAnnotationPresent(Controller.class)) {
				RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
				baseUrl = requestMapping.value()[0];
			} else {
				continue;
			}
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if (method.isAnnotationPresent(RequestMapping.class)) {
					String methodName = method.getName();
					RequestMapping annotation = method.getAnnotation(RequestMapping.class);
					String url = "/" + baseUrl + "/" + annotation.value();
					handlerMapping.put(url, method);
				} else {
					continue;
				}

			}


		}
	}

	private void doAutoWrite() {
		for (Map.Entry<String, Object> entry : ioc.entrySet()) {
			//获取类的所有 字段
			Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				field.setAccessible(true);
				//自动注入
				if (field.getClass().isAnnotationPresent(Autowired.class)) {
					//根据类型注入 bean的全路径
					String beanName = field.getType().getName();
					try {
						field.set(entry.getValue(), beanName);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else if (field.getClass().isAnnotationPresent(Resource.class)) {
					//根据名称注入 bean的别名
					Resource resource = field.getAnnotation(Resource.class);
					String name = resource.name();
					try {
						//设置被反射的对象的新值
						field.set(entry.getValue(), ioc.get(name));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					continue;
				}


			}
		}
	}

	private void doInstance() {
		for (String className : classNames) {
			try {
				Class<?> clazz = Class.forName(className);
				//只有带注解的类有必要进行实例化
				if (clazz.isAnnotation()) {
					if (clazz.isAnnotationPresent(Controller.class)) {
						Object instance = clazz.newInstance();
						//key 首字母大写
						ioc.put(toLowerFirstCase(clazz.getSimpleName()), instance);
					} else if (clazz.isAnnotationPresent(Service.class)) {
						//自定义类名
						Service serviceAnnotation = clazz.getAnnotation(Service.class);
						String beanName = serviceAnnotation.value();
						Object instance = clazz.newInstance();
						if (beanName == null || beanName.trim().length() == 0) {
							beanName = toLowerFirstCase(clazz.getSimpleName());
						}
						for (Class<?> i : clazz.getInterfaces()) {
							if (ioc.containsKey(beanName)) {
								throw new Exception("the class instance " + beanName + " exists");
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

		try {
			doDispatch(req, resp);
		} catch (Exception e) {
			resp.getWriter().write("500");
			return;
		}
	}

	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String requestURI = req.getRequestURI();
		if (handlerMapping.containsKey(requestURI)) {
			resp.getWriter().write("404");
			return;
		}
		Method method = handlerMapping.get(requestURI);
		String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
		//方法的形参列表
		Parameter[] parameters = method.getParameters();
		Object[] o = new Object[parameters.length];
		//请求的参数列表
		Map<String, String[]> reqParameterMap = req.getParameterMap();
		//使用for循环可以使o对象的顺序得到保证
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].getType() == HttpServletRequest.class) {
				o[i] = req;
				continue;
			}else if(parameters[i].getType() == HttpServletResponse.class) {
				o[i] = resp;
				continue;
			}else {
				//http传输过来都是string类型的
				//parameters[i].getAnnotation(RequestParam.class).value()获取不到参数的注解值
				//因为参数可能有多个注解所以是二维的
				Annotation[][] parameterAnnotations = method.getParameterAnnotations();
				for (int j = 0; j < parameterAnnotations.length; j++) {
					for (Annotation annotation:parameterAnnotations[j]){
						if(annotation instanceof RequestParam){
							RequestParam requestParam = (RequestParam) annotation;
							if(reqParameterMap.containsKey(requestParam.value())) {
								//因为req.getParameterMap()是一个String, String[]是String[]因为http支持name=1&name=2的请求方式
								String value = Arrays.toString(reqParameterMap.get(requestParam.value())).
										replaceAll("\\[|\\]", "").
										replaceAll("\\s",",");
								Object convert = convert(parameters[i].getType(), value);
								o[i] = convert;
							}
						}
					}
				}
			}
		}
		method.invoke(ioc.get(beanName), o);
	}

	private Object convert(Class<?> type,String value){
		if(String.class == type){
			return value;
		}
		if(Integer.class == type){
			return Integer.valueOf(value);
		}
		return value;
	}
	class HandleMapping{
		private String url;
		private Method method;
		private Object controller;
		//形参列表 参数名称 参数位置index
		private Map<String,Integer> paramIndexMapping;

		public HandleMapping(String url, Method method, Object controller) {
			this.url = url;
			this.method = method;
			this.controller = controller;
			paramIndexMapping = new HashMap<>();
			putParamIndexMapping(method);
		}

		private void putParamIndexMapping(Method method) {
			Annotation[][] parameterAnnotations = method.getParameterAnnotations();
			for (int j = 0; j < parameterAnnotations.length; j++) {
				for (Annotation annotation:parameterAnnotations[j]){
					if(annotation instanceof RequestParam){
						String paramName = ((RequestParam) annotation).value();
						if(!"".equals(paramName.trim())){
							paramIndexMapping.put(paramName,j);
						}
					}
				}
			}
			Class<?>[] parameterTypes = method.getParameterTypes();
			for (int i = 0; i < parameterTypes.length; i++) {
				Class<?> parameterType = parameterTypes[i];
				if(parameterType == HttpServletRequest.class || parameterType == HttpServletResponse.class){
					paramIndexMapping.put(parameterType.getName(),i);
				}
			}
		}
	}
}

