
	.text


	.globl main

main:

	la	 $a0, f1		 #Load an immediate value to register
	l.s	 $f0, 0($a0)		 #Load the value at the address
	s.s	 $f0, f1		#Store the value at the address of f1

	la	 $a0, f2		 #Load an immediate value to register
	l.s	 $f0, 0($a0)		 #Load the value at the address
	s.s	 $f0, f2		#Store the value at the address of f2

	l.s	 $f0, f1
	l.s	 $f1, f2
	add.s	 $f2, $f0, $f1		#add the two registers

	s.s	 $f2, sum		#Store the value at the address of sum

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, f1		#Load the float from f12 to f1
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, f2		#Load the float from f12 to f2
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, sum		#Load the float from f12 to sum
	syscall


	l.s	 $f0, f2
	l.s	 $f1, f1
	sub.s	 $f2, $f0, $f1		#add the two registers

	s.s	 $f2, difference		#Store the value at the address of difference

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, difference		#Load the float from f12 to difference
	syscall


	li $v0, 10		#Load system call to exit
	syscall


	.data

f1:	.float	12.400000
f2:	.float	33.400002
sum:	.float	 0,0,0
difference:	.float	 0,0,0
msg1:	.asciiz "f1 is "
msg2:	.asciiz "\nf2 is "
msg3:	.asciiz "\nsum is "
msg4:	.asciiz "\ndifference is "
