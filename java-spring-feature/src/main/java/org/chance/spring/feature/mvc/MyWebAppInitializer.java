package org.chance.spring.feature.mvc;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 自己实现 基于注解驱动的ServletInitializer来初始化DispatcherServlet
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-24 14:49:04
 */
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 根容器的配置类；（Spring的配置文件）   父容器；
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};
    }

    /**
     * web容器的配置类（SpringMVC配置文件）  子容器；
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }

    //获取DispatcherServlet的映射信息
    // 注意： /：拦截所有请求（包括静态资源（xx.js,xx.png）），但是不包括*.jsp；
    //  /*：拦截所有请求；连*.jsp页面都拦截；jsp页面是tomcat的jsp引擎解析的；
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    // 若你想定制化父类的一些默认行为  这里都是可以复写父类的protected方法的~~~~
    // Spring MVC也推荐你这么干~
    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        // dispatcherServlet.setDetectAllHandlerAdapters(false);
        return dispatcherServlet;
    }

}
