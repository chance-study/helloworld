package org.chance.spring.feature.mybatis;

import org.apache.ibatis.annotations.Select;
import org.chance.spring.feature.jpa.User;

import java.util.List;
import java.util.Map;

/**
 * 使用<pre>@Mapper</pre> 接口或者使用<pre>@MapperScan</pre>扫描
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-09-28 09:55:58
 */
//@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<Map<String, String>> findAll();

    User findUserById(Long id);

}
