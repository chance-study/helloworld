package org.chance.pattern.structural.composite.pattern2;

/**
 * Leaf
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2020/7/8
 */
public class Leaf extends Component {

    public Leaf(String name) {
        super(name);
    }

    @Override
    public void display(int depth) {
        //输出树形结构的叶子节点
        for (int i = 0; i < depth; i++) {
            System.out.print('-');
        }
        System.out.println(name);
    }

}
