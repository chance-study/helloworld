package org.chance.grammar

/**
 * Created by gengchao on 15/12/24.
 */
class Strings {

    public static void main(String[] args) {
        /* a single quoted string
         * Single quoted strings are plain java.lang.String and don’t support interpolation.
         * */
        println 'a single quoted string'
        println '''a triple single quoted string'''
        assert 'ab'=='a'+'b'

        def aMultilineString='''
        line one
        line two
        line three
        '''
        def strippedFirstNewline='''\
        line one
        line two
        line three
        '''
        assert !strippedFirstNewline.startsWith('\n')

        println 'an escaped single quote: \' needs a backslash'
        println 'an escaped escape character: \\ needs a double backslash'
        println 'The Euro currency symbol: \u20AC'

        /*Double quoted string
        * Double quoted strings are plain java.lang.String if there’s no interpolated expression, but are groovy.lang.GString instances if interpolation is present.
        * Any Groovy expression can be interpolated in all string literals
        * The placeholder expressions are surrounded by ${} or prefixed with $ for dotted expressions.
        *
        * */
        println "a double quoted string"

        def name='Guillaume' //a plain string
        def greeting="Hello ${name}"
        assert greeting.toString()=='Hello Guillaume'
        println greeting

        def sum = "The sum of 2 and 3 equals ${2 + 3}"
        assert sum.toString() == 'The sum of 2 and 3 equals 5'

        println "The sum of 1 and 2 is equal to ${def a=1;def b=2;a+b}"

        def person = [name:'Guillaume',age:36];
        assert "$person.name is $person.age years old"=='Guillaume is 36 years old'

        assert '${name}'=="\${name}"

        def sParameterLessClosure="1+2==${->3}"
        assert sParameterLessClosure == '1+2==3'

        def sOneParamClosure="1+2==${w->w<<3}"
        assert sOneParamClosure=='1+2==3'

        def number=1
        def eagerGString="value==${number}"
        def lazyGString="value==${->number}"
        assert eagerGString=="value==1"
        assert lazyGString=="value==1"
        number=2
        assert eagerGString=="value==1"
        assert lazyGString=="value==2"

        def message="The message is ${'hello'}"
        assert message instanceof GString

        def result=takeString(message)
        assert result instanceof String
        assert result =='The message is hello'

        assert "one:${1}".hashCode() != "one:1".hashCode()

        def key="a"
        def m=["${key}":"letter ${key}"]
        assert m["a"] == null


        /*
        *Triple double quoted string
        *
        * */
        def name1='Groovy'
        def template="""
            Dear Mr ${name1},
            You're the winner of the lottery1
            yours sincerly,
            Dave
        """
        assert template.toString().contains('Groovy')


        /*
        *Slashy string
        * Beyond the usual quoted strings,
        * Groovy offers slashy strings, which use / as delimiters.
        * Slashy strings are particularly useful for defining regular expressions and patterns,
        * as there is no need to escape backslashes.
        * */
        def fooPattern=/.*foo.*/
        "afooa".matches(fooPattern);
        println fooPattern
        assert fooPattern=='.*foo.*'
        def escapeSlash=/The character \/ is a forward slash/
        assert escapeSlash=='The character / is a forward slash'

        /*Slashy strings are multiline*/
        def multilineSlashy=/one
        two
        three/
        assert multilineSlashy.concat("\n")
        def color='blue'
        def interpolatedSlashy=/a ${color} car/
        assert interpolatedSlashy=='a blue car'
//        assert '' == //

        /*
        *Dollar slashy string
        *$/ ... /$
        * */
        def name2="Guillaume"
        def date="April,1st"
        def dollarSlashy=$/
            Hello $name,
            today we're ${date}.
            $ dollar sign
            $$ escaped dollar sign
            \backslash
            /forward slash
            $/escaped forward slash
            $/$ escaped dollar slashy string delimiter
        /$
        println dollarSlashy
        assert [
                'Guillaume',
                'April, 1st',
                '$ dollar sign',
                '$ escaped dollar sign',
                '\\ backslash',
                '/ forward slash',
                '$/ escaped forward slash',
                '/$ escaped dollar slashy string delimiter'

        ].each { dollarSlashy.contains(it) }


        /* Characters*/
        char c1='A'
        assert c1 instanceof Character

        def c2='B' as char
        assert c2 instanceof Character

        def c3=(char) 'C'
        assert c3 instanceof Character

    }

    public static String takeString(String message){
        assert message instanceof String
        return message
    }
}

/*
'\t'  tabulation
'\b'    backspace
'\n'    newline
'\r'    carriage return
'\f'    formfeed
'\\'    backslash
'\''    single quote (for single quoted and triple single quoted strings)
'\"'    double quote (for double quoted and triple double quoted strings)
* */