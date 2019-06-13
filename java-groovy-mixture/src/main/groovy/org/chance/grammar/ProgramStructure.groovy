//defining a package named org.chance.grammar
package org.chance.grammar

// importing the class MarkupBuilder
/**
 * Simple import
 */
import groovy.xml.MarkupBuilder

/**
 *  Star import *
 */
import groovy.xml.*

/**
 * Static import
 */
import static Boolean.FALSE

/**
 * Static import aliasing  as
 *
 */
import static Calendar.getInstance as now

/**
 * Static star import
 */
import static java.lang.Math.*

/**
 * Import aliasing
 *
 */
import org.chance.grammar.ProgramStructure.MultiplyTwo as OrigMultiplyTwo


/**
 * Created by gengchao on 15/12/27.
 */
class ProgramStructure {

    public static void main(String[] args) {
        // using the imported class to create an object
        def xml = new MarkupBuilder()

        assert xml != null

        /**
         * Default imports
         * The below imports are added by groovy for you:
         import java.lang.*
         import java.util.*
         import java.io.*
         import java.net.*
         import groovy.lang.*
         import groovy.util.*
         import java.math.BigInteger
         import java.math.BigDecimal
         */
        new Date()

        //Static import
        assert !FALSE //use directly, without Boolean prefix!

        /**
         * Static import aliasing
         */
        assert now().class == Calendar.getInstance().class

        //As you can see, we were able to access the methods sin and cos directly,
        // without the Math. prefix.
        assert sin(0) == 0.0
        assert cos(0) == 1.0

        /**
         * Import aliasing
         */
        // nothing to change below here
        def multiplylib = new MultiplyTwo()

        // assert passes as well
        assert 4 == new MultiplyTwoNew().multiply(2)
    }


    static class MultiplyTwo{
        def static multiply(def value){
            return value*3
        }
    }

    static class MultiplyTwoNew extends MultiplyTwo{
        def multiply(def value) {
            return value * 2 // fixed here
        }
    }

}
