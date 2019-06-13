package org.chance.grammar

/**
 * Created by gengchao on 15/12/26.
 * Groovy uses a comma-separated list of values, surrounded by
 * square brackets, to denote lists. Groovy lists are plain JDK
 * java.util.List, as Groovy doesn’t define its own collection
 * lasses. The concrete list implementation used when defining list
 * literals are java.util.ArrayList by default, unless you decide to
 * specify otherwise, as we shall see later on.
 */
class List {
    public static void main(String[] args) {
        def numbers=[1,2,3]
        assert numbers instanceof List
        assert numbers.size()==3

        def heterogeneous=[1,"a",true]

        def arrayList = [1, 2, 3]
        assert arrayList instanceof java.util.ArrayList

        def linkedList = [2, 3, 4] as LinkedList
        assert linkedList instanceof java.util.LinkedList

        LinkedList otherLinked = [3, 4, 5]
        assert otherLinked instanceof java.util.LinkedList


        /**
         * access elements of the list with the [] subscript operator
         * use the << leftShift operator to append elements to a list
         * */

        def letters = ['a', 'b', 'c', 'd']

        assert letters[0] == 'a'
        assert letters[1] == 'b'

        assert letters[-1] == 'd'
        assert letters[-2] == 'c'

        letters[2] = 'C'
        assert letters[2] == 'C'

        letters << 'e'
        assert letters[ 4] == 'e'
        assert letters[-1] == 'e'

        assert letters[1, 3] == ['b', 'd']
        assert letters[2..4] == ['C', 'd', 'e']


        def multi = [[0, 1], [2, 3]]
        assert multi[1][0] == 2
    }
}
