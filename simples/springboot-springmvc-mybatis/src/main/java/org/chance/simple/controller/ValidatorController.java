package org.chance.simple.controller;

import org.chance.simple.enums.StatusEnum;
import org.chance.simple.vo.req.IndexReqVO;
import org.chance.simple.vo.resp.IndexRespVO;
import org.chance.validation.constraints.enums.EnumValidation;
import org.chance.validation.util.ValidatorUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-19 14:52:32
 */
@RestController
@RequestMapping("/validator")
@Validated(Default.class)
public class ValidatorController {

    /**
     * spring基于方法级别的校验，使用aop 需要@Validated注解
     *
     * @param status
     * @return
     */
    @GetMapping("/{status}")
    public IndexRespVO path(@Valid @NotNull @EnumValidation(target = StatusEnum.class, field = "code") @PathVariable("status") Integer status) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * spring基于方法级别的校验，使用aop 需要@Validated注解
     *
     * @param status
     * @return
     */
    @GetMapping
    public IndexRespVO index(@Valid @NotNull @EnumValidation(target = StatusEnum.class, field = "code") Integer status) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * springMvc数据绑定校验(只能校验JavaBean)不需要@Validated注解
     *
     * @param indexReqVO
     * @return
     */
    @GetMapping("/query")
    public IndexRespVO query(@Valid IndexReqVO indexReqVO) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * 表单传参
     * <p>
     * springMvc数据绑定校验(只能校验JavaBean)不需要@Validated注解
     *
     * @param indexReqVO
     * @return
     */
    @PostMapping("/form")
    public IndexRespVO form(@Valid IndexReqVO indexReqVO) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * @param indexReqVO
     * @return
     * @RequestBody注解接收参数 <p>
     * springMvc数据绑定校验(只能校验JavaBean)不需要@Validated注解
     */
    @PostMapping
    public IndexRespVO index(@Valid @RequestBody IndexReqVO indexReqVO) {
        return IndexRespVO.builder().info("success").build();
    }

    /**
     * 直接使用工具类进行校验
     *
     * @param indexReqVO
     * @return
     */
    @PostMapping("/manual")
    public IndexRespVO manual(@RequestBody IndexReqVO indexReqVO) {
        ValidatorUtils.check(indexReqVO);
        return IndexRespVO.builder().info("success").build();
    }

}
