package com.jiagouedu.config;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class MyProxyRegistrar implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {


		AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(registry);

		AnnotationAttributes enableAspectJAutoProxy =
		AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MyProxyRegistrar.class.getName(), false));

		if (enableAspectJAutoProxy != null) {
//			if (enableAspectJAutoProxy.getBoolean("proxyTargetClass")) {
//
//			}
//			if (enableAspectJAutoProxy.getBoolean("exposeProxy")) {
//				AopConfigUtils.forceAutoProxyCreatorToExposeProxy(registry);
//			}

		}



	}
}
