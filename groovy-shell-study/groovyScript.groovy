/**
 * Created by GengChao on 2016/9/27.
 * Groovy 基础语法教程
 */

/**
 * Hello,World
 */
def str1 = "Hello,world"
println(str1)

/**
 * 单行注释 //
 * 多行注释 / * * /
 * Groovy文档注释 / ** * /
 * 事务行 #!/usr/bin/env groovy 允许脚本直接从命令行运行
 */

/**
 * 关键字:
 * as   assert  break   case
 * catch    class   const   continue
 * def  default     do  else
 * for  goto    if  implements
 * import   in  instanceof  interface
 * new  null    package return
 * super    switch  this    throw
 * throws trait     true    try
 * while
 *
 */

/**
 * 标识符
 * 正常标识符
 * 引用标识符 引用标识符出现在一个点式表达式的点后面
 */

def name,item3,with_un,$dollarStart
//def 3tier
//def a+b

def map = [:]
map."an identifier with a " = "allowed"
map.'with-dash-signs' = 'allowed'
println(map)

map.'single quote'
map."double quote"
map.'''triple single quote'''
map."""triple double quote"""
map./slashy string/
map.$/dollar slashy string/$

// 字符串 GStrings
def firstname = "Homer"
map."Simson-${firstname}" = "Homer Simson"
assert map.'Simson-Homer' == "Homer Simson"

/**
 * 字符串
 * 单引号字符串 '' 单引号字符串是普通的java.lang.String，不支持插值。
 * 字符串连接 +
 * 三单引号字符串 ''' '''  三单引号字符串是普通的java.lang.String，不支持插值。
 * 转义特殊字符 \t \b \n \r \\ \f \' \"
 * .Unicode转义序列 \u20AC
 * 双引号字符串 "" 支持插值  "aa${bb}cc"  $p.v 访问属性的时候可以省略花括号
 * 插入闭包表达式的特殊情况  闭包相比表达式有一个有趣的优势：延迟计算。
 * 三双引号字符串 “”“ ”“”
 * 斜杠字符串 斜杠字符串对于定义正则表达式和模式是特别有用的，
 * 因为不需要转义反斜杠。// 多行 支持插值
 * 美元符修饰的斜杠字符串 $/ $/  转义符号是 $
 */

'a single'
assert 'ab' == 'a'+'b'
'''
    1
    2
    3
'''
def name1 = 'name'
def greeting1 ="Hello ${name1}"
def sum = "The sum of 2 and 3 equals ${2 + 3}"
def error = "The sum of 1 and 2 is equal to ${def a = 1; def b = 2; a + b}}"
println error

def person = [name:'n1',age:36]
println "$person.name is $person.age years old"

def sParameterLessClosure = "1 + 2 == ${-> 3}"
assert sParameterLessClosure == '1 + 2 == 3'

def sOneParamClosure = "1 + 2 == ${ w -> w << 3}"
assert sOneParamClosure == '1 + 2 == 3'

// 闭包问题
// 一个嵌入的闭包表达式，携带超过一个参数，那么在运行时将会产生一个异常。闭包仅仅允许携带0个或1个参数。
def number = 1
def eagerGString = "value == ${number}"
def lazyGString = "value == ${ -> number }"
assert eagerGString == "value == 1"
assert lazyGString == "value == 1"
number = 2
assert eagerGString == "value == 1"
assert lazyGString == "value == 2"

def pattern = /.*foo.*/
assert pattern == '.*foo.*'

/**
 * 字符
 * ''
 */
char c1 = 'A'  //char类型，使变量包含字符
assert c1 instanceof Character
def c2 = 'B' as char  // 通过as操作符使用类型强制转换
assert c2 instanceof Character
def c3 = (char)'C' //通过char操作符做类型转换
assert c3 instanceof Character

/**
 * 数字 Groovy支持不同类型的整数和小数，通常以Java的Number类型返回。
 * 整数 byte char short int long BigInteger
 * 可选择的非十进制表示 0b 二进制 0x 十六进制 0 8进制
 *
 */
byte bb = 1
char cc =2
short sh = 3
int ii = 4
long ll =5
BigInteger bi = 6
def da = 1
assert da instanceof Integer

int xInt = 0b1010_1111
xInt = 077
xInt = 0x77

/**
 * 小数 float double BigDecimal
 */
// primitive types
float f = 1.234
double d = 2.345
// infinite precision
BigDecimal bd = 3.456
assert 1e3 == 1_000.0
assert 2E4 == 20_000.0
assert 3e+1 == 30.0
assert 4E-2 == 0.04
assert 5e-1 == 0.5

/**
 * 有下划线的文本 1_000.00
 * 数字类型后缀
 * Type            Suffix
 * BigInteger      G 或 g
 * Long            L 或 l
 * Integer         I 或 i
 * BigDecimal      G 或 g
 * Double          D 或 d
 * Float           F 或 f
 */

assert 42I == new Integer('42')

/**
 * 数学运算
 *
 */

/**
 * 布尔 true false
 */
def myBooleanVariable = true
boolean untypedBooleanVar = false

/**
 * 列表
 * Groovy使用逗号分隔列表中的值，并使用方括号包围，用来指定一个列表。
 * Groovy的列表是java.util.List，因为Groovy没有定义任何集合类。当定义一个列表常量时，
 * 默认的列表具体实现是java.util.ArrayList，除非你指定，我们将在后面看到。
 */
def numbers = [1,2,3]
assert numbers instanceof List
assert numbers.size() == 3

def heterogeneous = [1, "a", true]

def arrayList = [1, 2, 3]
assert arrayList instanceof java.util.ArrayList

def linkedList = [2, 3, 4] as LinkedList
assert linkedList instanceof java.util.LinkedList

LinkedList otherLinked = [3, 4, 5]
assert otherLinked instanceof java.util.LinkedList

def letters = ['a','b','c','d']
letters[0]
letters[-1]
letters << 'e'
assert letters[4] == 'e'

letters[1,3]
letters[2..4]

def multi = [[0,1],[2,3]]
multi[1][0] = 2

/**
 * 数组
 *
 */
String[] arrStr = ['Ananas', 'Banana', 'Kiwi']
assert arrStr instanceof String[]
assert !(arrStr instanceof List)

def numArr = [1, 2, 3] as int[]
assert numArr instanceof int[]
assert numArr.size() == 3

def matrix3 = new Integer[3][3]
assert matrix3.size() == 3

Integer[][] matrix2
matrix2 = [[1, 2], [3, 4]]
assert matrix2 instanceof Integer[][]

/**
 * 映射
 *
 */
def colors = [red: '#FF0000', green: '#00FF00', blue: '#0000FF']
assert colors['red'] == '#FF0000'
assert colors.green == '#00FF00'

colors['pink'] = '#FF00FF'
colors.yellow = '#FFFF00'

assert colors.pink == '#FF00FF'
assert colors['yellow'] == '#FFFF00'

assert colors instanceof java.util.LinkedHashMap

assert colors.unknown == null

def numbers1 = [1: 'one', 2: 'two']
assert numbers1[1] == 'one'

def key = 'name'
def person1 = [key: 'Guillaume']

assert !person1.containsKey('name')
assert person1.containsKey('key')

person2 = [(key): 'Guillaume']
assert person2.containsKey('name')
assert !person2.containsKey('key')

def a

//定义函数
def func(a,b=20){
    println(a+b+"")
}

func("cc",30)

//字符串使用
def country="china"

def strTemp(contry){
    return "${contry} haha";
}
def s1 = strTemp(country)
s1.length()
s1[0,2]
s1.substring(0,2)

def greeting ="""
    welcome ${country}
    6.11-7.11 hhh
"""
println(greeting)

// List的使用
def myrange = 25 .. 10
println(myrange)

def coll = ["c","c++","java","javascript","python"]
println(coll)

for(i in coll)
    println(i)






