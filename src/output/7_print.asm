
	.text


	.globl main

main:

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	li	 $v0, 12		#System call to read in char
	syscall

	sb	 $v0, c		#Store the input in variable: 'c'

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	ld	 $a0, c		#Load the char to a0
	syscall

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

	li	 $v0, 5		#System call to read in long
	syscall

	sw	 $v0, l		#Store the input in variable: 'l'

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg6		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, l		#Load the long into a0
	syscall

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg8		#Load the String to be printed
	syscall

	li	 $v0, 6		#System call to read in float
	syscall

	s.d	 $f0, f		#Store the input in variable: 'f'

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg9		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.d	$f12, f		#Load the float from f12 to f
	syscall

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg11		#Load the String to be printed
	syscall

	li	 $v0, 5		#System call to read in int
	syscall

	sw	 $v0, i		#Store the input in variable: 'i'

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg12		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call for to print integer
	ld	 $a0, i		#Load the integer into a0
	syscall

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg14		#Load the String to be printed
	syscall

	li	 $v0, 7		#System call to read in double
	syscall

	s.d	 $f0, d		#Store the input in variable: 'd'

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg15		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, d 		#Load the float from f12 to d
	syscall

	li $v0, 10		#Load system call to exit
	syscall

	.data

i:	.word
d:	.double
f:	.float
c:	.byte
l:	.word
msg1:	.asciiz "Enter a char value for c: "
msg2:	.asciiz "\nc = \'"
msg4:	.asciiz "\'"
msg5:	.asciiz "\nEnter a long value for l: "
msg6:	.asciiz "l = "
msg8:	.asciiz "\nEnter a float value for f: "
msg9:	.asciiz "f = "
msg11:	.asciiz "\nEnter an integer value for i: "
msg12:	.asciiz "i = "
msg14:	.asciiz "\nEnter a double value for d: "
msg15:	.asciiz "d = "
