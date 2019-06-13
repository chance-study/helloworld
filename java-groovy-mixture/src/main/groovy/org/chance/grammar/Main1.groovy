package org.chance.grammar

/**
 * Created by gengchao on 15/12/27.
 */

import org.codehaus.groovy.runtime.InvokerHelper

/**
 * Script class
 *
 */
class Main1 extends Script {

    int power(int n) { 2** n}

    def run() {
        println 'Groovy world!'
        println 'Hello'
        println "2^6==${power(6)}"
    }
    static void main(String[] args) {
        InvokerHelper.runScript(Main, args)
    }

    /**
     * Methods
     */
}
