package org.chance.spring.feature.aop.demo1;

import org.springframework.stereotype.Component;

/**
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-09 14:38:03
 */
@Component("women")
public class Women implements Person {

    @Override
    public void likePerson() {
        System.out.println("我是女生，我喜欢帅哥");
    }

}
