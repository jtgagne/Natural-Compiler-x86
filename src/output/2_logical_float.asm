
	.text


	.globl main

main:

L1:	la	 $a0, CONST1		 #Load an immediate value to register
	l.s	 $f2, 0($a0)		 #Load the value at the address
	s.s	 $f2, f1

L3:	la	 $a0, CONST2		 #Load an immediate value to register
	l.s	 $f2, 0($a0)		 #Load the value at the address
	s.s	 $f2, f2

L4:	l.s	 $f2, f1
	l.s	 $f3, f2
	c.lt.s	 $f2, $f3
	bc1f	 0, L5

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

L7:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, f1		#Load the float from f12 to f1
	syscall


L8:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, f2		#Load the float from f12 to f2
	syscall


L5:	l.s	 $f2, f1
	l.s	 $f3, f2
	c.lt.s	 $f2, $f3
	bc1f	 0, L9

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

L9:	l.s	 $f2, f2
	l.s	 $f3, f1
	c.lt.s	 $f2, $f3
	bc1f	 0, L2

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

f1:	.float	0,0,0
f2:	.float	0,0,0
CONST1:	.float	12.000000
CONST2:	.float	16.000000
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "f1 is less than f2"
msg2:	.asciiz "\n"
msg3:	.asciiz " < "
msg4:	.asciiz "\nLess than works"
msg5:	.asciiz "\Error with 'less than'"
