package org.chance.grammar

/**
 * Created by gengchao on 15/12/26.
 * Groovy reuses the list notation for arrays, but to make such literals
 * arrays, you need to explicitely define the type of the array through
 * coercion or type declaration.
 */
class Arrays {
    public static void main(String[] args) {
        String[] arrStr = ['Ananas', 'Banana', 'Kiwi']

        assert arrStr instanceof String[]
        assert !(arrStr instanceof java.util.List)

        def numArr = [1, 2, 3] as int[]

        assert numArr instanceof int[]
        assert numArr.size() == 3

        /**
         * multi-dimensional arrays
         */
        def matrix3 = new Integer[3][3]
        assert matrix3.size() == 3

        Integer[][] matrix2
        matrix2 = [[1, 2], [3, 4]]
        assert matrix2 instanceof Integer[][]

        String[] names = ['Cédric', 'Guillaume', 'Jochen', 'Paul']
        assert names[0] == 'Cédric'

        names[2] = 'Blackdrag'
        assert names[2] == 'Blackdrag'
    }
}

