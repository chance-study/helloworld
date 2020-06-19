package org.chance.simple.service.impl;

import org.chance.simple.enums.StatusEnum;
import org.chance.simple.service.HelloService;
import org.chance.validation.constraints.enums.EnumValidation;
import org.springframework.stereotype.Service;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-18 18:09:39
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public Object hello(Integer id, String name) {
        return null;
    }

    @Override
    public Object hello0(@EnumValidation(target = StatusEnum.class, field = "code") Integer id) {
        return null;
    }

    @Override
    public Object helloReturn(Integer id, String name) {
        return null;
    }

}
