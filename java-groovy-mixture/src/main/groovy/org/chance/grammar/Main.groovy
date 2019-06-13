package org.chance.grammar

println 'Groovy world!'

int fib(int n){
    n<2?1:fib(n-1)+fib(n-2)
}

//Methods
assert fib(10)==89


//Variables
int x = 1
int y = 2
assert x+y == 3

xx = 1
yy = 2
assert xx+yy == 3


println 'Hello'

int power(int n) { 2**n }

println "2^6==${power(6)}"
