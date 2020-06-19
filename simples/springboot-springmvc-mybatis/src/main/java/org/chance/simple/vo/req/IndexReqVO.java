package org.chance.simple.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chance.simple.enums.StatusEnum;
import org.chance.validation.constraints.enums.Contains;
import org.chance.validation.constraints.enums.EnumValidation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * IndexReqVO
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-17 17:14:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexReqVO {

    /**
     * 查询状态
     * {@link org.chance.simple.enums.StatusEnum}
     */
    @EnumValidation(target = StatusEnum.class, field = "code")
    private Integer status;

    @Contains(target = StatusEnum.class, field = "code")
    private List<Integer> statusList;

    /**
     * 嵌套校验只能使用@Valid，不能用@Validated代替
     */
    @NotNull
    @Valid
    private Info info;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {

        @NotBlank
        private String name; // 企业名称

        @NotBlank
        private String code; // 企业代码


    }

}
