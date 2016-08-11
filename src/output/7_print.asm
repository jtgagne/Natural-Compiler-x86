
	.text


	.globl main

main:

L1:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

 	li	 $v0, 12		#System call to read in char
	syscall

	sb	 $v0, c		#Store the input in variable: 'c'
L3:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, c		#Load the char to a0
	syscall


L4:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

L5:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

 	li	 $v0, 5		#System call to read in long
	syscall

	sw	 $v0, l		#Store the input in variable: 'l'
L6:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, l		#Load the long into a0
	syscall


L7:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg6		#Load the String to be printed
	syscall

 	li	 $v0, 6		#System call to read in float
	syscall

	s.d	 $f0, f		#Store the input in variable: 'f'
L8:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg7		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, f		#Load the float from f12 to f
	syscall


L9:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg8		#Load the String to be printed
	syscall

 	li	 $v0, 5		#System call to read in int
	syscall

	sw	 $v0, i		#Store the input in variable: 'i'
L10:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg9		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, i		#Load the long into a0
	syscall


L11:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg10		#Load the String to be printed
	syscall

 	li	 $v0, 7		#System call to read in double
	syscall

	s.d	 $f0, d		#Store the input in variable: 'd'
L12:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg11		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, d 		#Load the float from f12 to d
	syscall


L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

l:	.word	0,0,0
c:	.byte	0,0,0
f:	.float	0,0,0
d:	.double	0,0,0
i:	.word	0,0,0
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "Enter a char value for c: "
msg2:	.asciiz "\nc = \'"
msg3:	.asciiz "\'"
msg4:	.asciiz "\n\nEnter a long value for l: "
msg5:	.asciiz "l = "
msg6:	.asciiz "\n\nEnter a float value for f: "
msg7:	.asciiz "f = "
msg8:	.asciiz "\n\nEnter an integer value for i: "
msg9:	.asciiz "i = "
msg10:	.asciiz "\n\nEnter a double value for d: "
msg11:	.asciiz "d = "
