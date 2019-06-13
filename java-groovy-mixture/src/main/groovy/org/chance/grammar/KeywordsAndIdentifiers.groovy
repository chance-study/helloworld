package org.chance.grammar

/**
 * Created by gengchao on 15/12/23.
 */
class KeywordsAndIdentifiers {
    public static void main(String[] args) {
        //Here are a few examples of valid identifiers (here, variable names):
        def name
        def item3
        def with_underscore
        def $dollarStart

        //def name
//        def item3
//        def with_underscore
//        def $dollarStart

//        All keywords are also valid identifiers when following a dot:
        def foo
        foo.as
        foo.assert
        foo.break
        foo.case
        foo.catch

//        Quoted identifiers
        def map = [:]

        map."an identifier with a space and double quotes" = "ALLOWED"
        map.'with-dash-signs-and-single-quotes' = "ALLOWED"

        assert map."an identifier with a space and double quotes" == "ALLOWED"
        assert map.'with-dash-signs-and-single-quotes' == "ALLOWED"

        map.'single quote'
        map."double quote"
        map.'''triple single quote'''
        map."""triple double quote"""
        map./slashy string/
        map.$/dollar slashy string/$

        def firstname = "Homer"
        map."Simson-${firstname}" = "Homer Simson"
        assert map.'Simson-Homer' == "Homer Simson"

    }
}

/*
#3.1.Normal identifiers
'a' to 'z' (lowercase ascii letter)

'A' to 'Z' (uppercase ascii letter)

'\u00C0' to '\u00D6'

'\u00D8' to '\u00F6'

'\u00F8' to '\u00FF'

'\u0100' to '\uFFFE'

#3.2. Quoted identifiers
persion.name  persion.'name'  persion."name"
* */

/*
Table 1. Keywords
as

assert

break

case

catch

class

const

continue

def

default

do

else

enum

extends

false

finally

for

goto

if

implements

import

in

instanceof

interface

new

null

package

return

super

switch

this

throw

throws

trait

true

try

while

*/
