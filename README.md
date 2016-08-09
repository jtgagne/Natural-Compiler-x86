# Compiler-Design
Compiler for Natural. Semester project in Compiler Design at Wentworth Institute of Technology Summer 2016.

To see parser output, read the ABOUT.md file located under the src/output/ directory.

# Updates
Added IO to the language:
```java 
  int a
  input("Enter an integer value for a: ", a)
  print("\na = ", a)
```

# Testing the Parser
1. Locate the Test.java file in the parser package.
2. Run the main method from that class.

All the files in the /src/test/ directory will be scanned in by the lexer and checked for syntax correctness by the parser. The files in the test/ directory beginning with E contain errors that should be found by the parser. The remaining files, all starting numbers should not display any errors when being parsed.

# Natural Syntax
The syntax for the natural language is presented below.

## 1. Data Types 
Natural supports the following six basic data types.
* int           (4-bytes)
* long          (8-bytes)
* float         (8-bytes)
* double        (16-bytes)
* char          (1-byte)
* boolean       (1-byte)

## 2. Variable Declaration
Variable declaration is currently supported in the form of a single declaration per line. Each declaration should consist of a datatype and an identifier. Valid identifiers must start with an alphabetic letter.

* int integer
* long aLongNumber
* float floatingPoint
* double aDoubleFloatingPoint
* char singleCharacter
* boolean trueOrFalse

## 3. Comparision Operators
Natural supports the same comparison operators that java, c, and c++ supports but it also supports the written meanings of the operators. This is intended to make the language easy to transfer to for c, java, and c++ programmers but also allow the language to look as close to english/ pseudocode as possible. All synonomous operators are grouped together below.

Less than comparison operators
* <                           
* less than


Greater than comparison operators
* >                           
* greater than


Less than or equal to comparison operators
* <=                          
* less than or equal to


Greater than or equal to  comparison operator
* >=                         
* greater than or equal to


Equality Operators
* =                           
* equal to                    
* equals


Inequality Operators
* !=                          
* not equal to

## 4. Assignment Operators
There are two operators for assigning variables in Natural. Variables can be assigned at the same time that they are declared.
* is    
* :=

An example of assigning varivous data types to values
```java
int a is 5
int b := 6
boolean isNatural is true
char c is 'c'
long bigNumber := 1000000000000
double real is 4.4443
```

## 5. Boolean Joining / Negation
Natural supports the similar joining operators as many c based languages as well as the written version of the operators.
* ||        (double bar for boolean or)
* or        (the reserved "or" keyword can also be used for boolean or)

* &&        (double ampersand is used for boolean and)
* and       (the keyword "and" can also be used for boolean and)

* !         (single exclamation is used for boolean negation)
* not       (the reserved "not" keyword can also be used for boolean negation)


## 6. Comments
To include comments use the following:

```
#this is a single line comment 

##
  This is a multi
  Line 
  Comment
##
```

## 7. Incrementation
To increase / decrease values use the following reserved words:
* increase by
* decrease by 

Example: 
```
int a is 4
a increase by 1     # same as saying a is a + 1
a decrease by 1     # same as saying a is a - 1
```

## 8. Control Operators
Natural Supports the following for control flow:
* if
* else if 
* else 
* while 
* do while
* for 
* break

Example syntax:
```
int a is 4
if(a less than 5){
    #do something
} else if (a greater than or equal to 5){
    #do something else 
} else {
    #default case 
}

boolean aBool is true
while(aBool and a less than 6){
    
    #do something while true
    
    if (a equals 7){
      break;                      #breaks should be followed by a semicolon 
    }
}


do{
  a increase by 1
  
  if (a equals 10){
    break;
  }
  
} while (true)

for(int j is 4; j less than 10; j increase by 1){
  #loop through some code 
}
```
