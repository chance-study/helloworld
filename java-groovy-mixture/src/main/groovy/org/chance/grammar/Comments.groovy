package org.chance.grammar;

/**
 * GroovyDoc comment
 * A Class description
 * Created by gengchao on 15/12/23.
 */
class Comments {

    /** the name of the person */
    String name

    /**
     * Creates a greeting method for a certain person.
     *
     * @param otherPerson the person to greet
     * @return a greeting message
     */
    String greet(String otherPerson) {

        /*
        Multiline comment
        a standalone multiline comment
        spanning two lines */
        println "hello"    /* a multiline comment starting
                            at the end of a statement */

        // a standalone single line comment
        "Hello ${otherPerson}"  // a comment till the end of the line
    }

}

/*
*Shebang line
* Beside the single line comment, there is a special line comment,
* often called the shebang line understood by UNIX systems which allows scripts to be run directly from the command-line,
* provided you have installed the Groovy distribution and the groovy command is available on the PATH.
* #!/usr/bin/env groovy
* println "Hello from the shebang line"
* */
