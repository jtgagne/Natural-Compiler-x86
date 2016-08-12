
	.text


	.globl main

main:

L1:	la	 $a0, CONST1		 #Load an immediate value to register
	l.d	 $f2, 0($a0)		 #Load the value at the address
	s.d	 $f2, d1

L3:	la	 $a0, CONST2		 #Load an immediate value to register
	l.d	 $f2, 0($a0)		 #Load the value at the address
	s.d	 $f2, d2

L4:	l.d	 $f2, d2
	l.d	 $f4, d1
	c.lt.d	 $f4, $f2
	bc1f	 0, L5

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

L7:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, d2 		#Load the float from f12 to d2
	syscall


L8:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, d1 		#Load the float from f12 to d1
	syscall


L5:	l.d	 $f2, d1
	l.d	 $f4, d2
	c.lt.d	 $f2, $f4
	bc1f	 0, L9

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

L9:	l.d	 $f2, d2
	l.d	 $f4, d1
	c.lt.d	 $f2, $f4
	bc1f	 0, L2

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

d2:	.double	0,0,0
d1:	.double	0,0,0
CONST1:	.double	10.000000
CONST2:	.double	16.000000
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "d2 is greater than d1"
msg2:	.asciiz "\n"
msg3:	.asciiz " > "
msg4:	.asciiz "\nLess than works"
msg5:	.asciiz "\Error with 'less than'"
