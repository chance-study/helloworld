package org.chance.object;

import java.util.Objects;
import java.util.Random;

/**
 * Cat
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/6
 */
public class Cat {

    private String name;

    private int age;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * 根据属性计算hashCode
     */
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, age);
//    }

    /**
     * 返回固定的hashCode
     * @return
     */
    @Override
    public int hashCode() {
        return 1;
    }

    /**
     * 返回随机的hashCode
     * @param
     * @return
     */
//    @Override
//    public int hashCode() {
//        return new Random().nextInt();
//    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//        final Cat other = (Cat) obj;
//        return Objects.equals(this.name, other.name)
//                && Objects.equals(this.age, other.age);
//    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }

//    @Override
//    public boolean equals(Object obj) {
//       return false;
//    }


}
