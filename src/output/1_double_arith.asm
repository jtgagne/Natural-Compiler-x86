
	.text


	.globl main

main:

L1:	la	 $a0, CONST1		 #Load an immediate value to register
	l.d	 $f0, 0($a0)		 #Load the value at the address
	s.d	 $f0, d1

L3:	la	 $a0, CONST2		 #Load an immediate value to register
	l.d	 $f0, 0($a0)		 #Load the value at the address
	s.d	 $f0, d2

L4:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

L5:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, d1 		#Load the float from f12 to d1
	syscall


L6:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, d2 		#Load the float from f12 to d2
	syscall


L7:	l.d	 $f0, d1
	l.d	 $f2, d2
	add.d	 $f4, $f0, $f2		#add the two registers

	s.d	 $f4, sum

L8:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, sum 		#Load the float from f12 to sum
	syscall


L9:	l.d	 $f0, d2
	l.d	 $f2, d1
	sub.d	 $f4, $f0, $f2		#add the two registers

	s.d	 $f4, difference

L10:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, difference 		#Load the float from f12 to difference
	syscall


L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

difference:	.double	0,0,0
sum:	.double	0,0,0
d2:	.double	0,0,0
d1:	.double	0,0,0
CONST1:	.double	12.400000
CONST2:	.double	33.400002
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "Sample program to test arithmetic with doubles:\n\n"
msg2:	.asciiz "d1 is "
msg3:	.asciiz "\nd2 is "
msg4:	.asciiz "\nd1 + d2 = "
msg5:	.asciiz "\nd1 - d2 = "
