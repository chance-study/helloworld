package org.chance.spring.feature.utils;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-03 15:26:20
 */
public class UriComponentsBuilderDemo {

    public static void main(String[] args) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.baidu.com").port("8080")
                .path("/junit-5").build();
        System.out.println(uriComponents.toUriString()); //http://www.baidu.com:8080/junit-5
        System.out.println(uriComponents.toString()); //http://www.baidu.com:8080/junit-5


        UriComponents uriComponents1 = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.baidu.com").port("8080")
                //此处单词之间有空格 RFC 3986规范是不允许的。这时候若需要，后面encode一下即可
                .path("/junit  abc").build().encode();
        System.out.println(uriComponents1.toUriString()); //http://www.baidu.com:8080/junit%20%20abc

    }

}
