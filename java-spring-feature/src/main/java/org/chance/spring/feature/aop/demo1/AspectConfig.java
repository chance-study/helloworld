package org.chance.spring.feature.aop.demo1;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-09 14:40:38
 */
@Aspect
@Component
public class AspectConfig {

    //"+"表示person的所有子类；defaultImpl 表示默认需要添加的新的类
    @DeclareParents(value = "org.chance.spring.feature.aop.demo1.Person+", defaultImpl = FemaleAnimal.class)
    public Animal animal;

}
