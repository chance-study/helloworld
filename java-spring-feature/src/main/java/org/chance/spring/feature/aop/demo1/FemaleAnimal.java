package org.chance.spring.feature.aop.demo1;

import org.springframework.stereotype.Component;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-09 14:39:18
 */
@Component
public class FemaleAnimal implements Animal {
    @Override
    public void eat() {
        System.out.println("我是雌性，我比雄性更喜欢吃零食");
    }
}
