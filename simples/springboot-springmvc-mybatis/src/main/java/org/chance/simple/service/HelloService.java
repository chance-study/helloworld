package org.chance.simple.service;

import org.chance.simple.enums.StatusEnum;
import org.chance.validation.constraints.enums.EnumValidation;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Validated(Default.class)
public interface HelloService {


    Object hello(@NotNull @Min(10) Integer id, @NotNull String name);

    /**
     * javax.validation.ConstraintDeclarationException
     * OverridingMethodMustNotAlterParameterConstraints
     *
     * @param id
     * @return
     */
    Object hello0(@EnumValidation(target = StatusEnum.class, field = "code") Integer id);

    @NotNull
    Object helloReturn(Integer id, String name);

}
