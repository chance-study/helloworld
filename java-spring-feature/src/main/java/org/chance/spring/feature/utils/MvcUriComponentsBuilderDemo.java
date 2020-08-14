package org.chance.spring.feature.utils;

import org.chance.spring.feature.mvc.HelloController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-03 15:32:19
 */
public class MvcUriComponentsBuilderDemo {
    public static void main(String[] args) {
        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(HelloController.class, "hello", 21).buildAndExpand(42);

        URI uri = uriComponents.encode().toUri();
    }
}
