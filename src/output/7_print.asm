
	.text


	.globl main

main:

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

 	li	 $v0, 8		#System call to read in boolean
	la	 $a0, isWorking
	li	 $a1, 6		#Allow for up to 5 chars
	syscall

	la	 $a0, isWorking		#Store the input in variable: 'isWorking'
	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	lb	 $t0, isWorking		#Load the value to be compared
	beqz	 $t0, L8		#Goto L8 if greater than 0

L7:	li	 $v0, 4
	la 	$a0, BOOL_TRUE_STR
	syscall
	j	 L9

L8:	li	 $v0, 4
	la 	 $a0, BOOL_FALSE_STR
	syscall
	j	 L9

L9:
	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

 	li	 $v0, 12		#System call to read in char
	syscall

	sb	 $v0, c		#Store the input in variable: 'c'
	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, c		#Load the char to a0
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
	la	 $a0, msg7		#Load the String to be printed
	syscall

 	li	 $v0, 6		#System call to read in float
	syscall

	s.d	 $f0, f		#Store the input in variable: 'f'
	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg8		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, f		#Load the float from f12 to f
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg9		#Load the String to be printed
	syscall

 	li	 $v0, 5		#System call to read in int
	syscall

	sw	 $v0, i		#Store the input in variable: 'i'
	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg10		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, i		#Load the long into a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg11		#Load the String to be printed
	syscall

 	li	 $v0, 7		#System call to read in double
	syscall

	s.d	 $f0, d		#Store the input in variable: 'd'
	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg12		#Load the String to be printed
	syscall

	li	 $v0, 3 		#Load system call to print double
	l.d	$f12, d 		#Load the float from f12 to d
	syscall


	li $v0, 10		#Load system call to exit
	syscall


	.data

isWorking:	.byte	0,0,0
l:	.word	0,0,0
c:	.byte	0,0,0
f:	.float	0,0,0
d:	.double	0,0,0
i:	.word	0,0,0
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "Enter a value (true | false) for isWorking: "
msg2:	.asciiz "\nisWorking = "
msg3:	.asciiz "Enter a char value for c: "
msg4:	.asciiz "\nc = "
msg5:	.asciiz "\nEnter a long value for l: "
msg6:	.asciiz "l = "
msg7:	.asciiz "\nEnter a float value for f: "
msg8:	.asciiz "f = "
msg9:	.asciiz "\nEnter an integer value for i: "
msg10:	.asciiz "i = "
msg11:	.asciiz "\nEnter a double value for d: "
msg12:	.asciiz "d = "
