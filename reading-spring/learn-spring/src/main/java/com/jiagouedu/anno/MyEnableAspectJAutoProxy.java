package com.jiagouedu.anno;

import com.jiagouedu.config.MyProxyRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MyProxyRegistrar.class)
public @interface MyEnableAspectJAutoProxy {

}
