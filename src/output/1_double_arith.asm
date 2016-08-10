
	.text


	.globl main

main:

	la	 $a0, d1		 #Load an immediate value to register
	l.d	 $f0, 0($a0)		 #Load the value at the address
	s.d	 $f0, d1		#Store the value at the address of d1

	la	 $a0, d2		 #Load an immediate value to register
	l.d	 $f0, 0($a0)		 #Load the value at the address
	s.d	 $f0, d2		#Store the value at the address of d2

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, d1 		#Load the float from f12 to d1
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, d2 		#Load the float from f12 to d2
	syscall


	l.d	 $f0, d1
	l.d	 $f2, d2
	add.d	 $f4, $f0, $f2		#add the two registers

	s.d	 $f4, sum		#Store the value at the address of sum

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, sum 		#Load the float from f12 to sum
	syscall


	l.d	 $f0, d2
	l.d	 $f2, d1
	sub.d	 $f4, $f0, $f2		#add the two registers

	s.d	 $f4, difference		#Store the value at the address of difference

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, difference 		#Load the float from f12 to difference
	syscall


	li $v0, 10		#Load system call to exit
	syscall


	.data

d1:	.double	12.400000
d2:	.double	33.400002
sum:	.double	 0,0,0
difference:	.double	 0,0,0
msg1:	.asciiz "d1 is "
msg2:	.asciiz "\nd2 is "
msg3:	.asciiz "\nd1 + d2 = "
msg4:	.asciiz "\nd1 - d2 = "
