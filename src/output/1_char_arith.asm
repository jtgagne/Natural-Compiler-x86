
	.text


	.globl main

main:

	la	 $t0, c		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, c		#Store the value at the address of c

	la	 $t0, o		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, o		#Store the value at the address of o

	la	 $t0, m		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, m		#Store the value at the address of m

	la	 $t0, p		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, p		#Store the value at the address of p

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, c		#Load the char to a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, o		#Load the char to a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, m		#Load the char to a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, p		#Load the char to a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, c		#Load the char to a0
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, o		#Load the char to a0
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, m		#Load the char to a0
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, p		#Load the char to a0
	syscall

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg6		#Load the String to be printed
	syscall

	lb	 $t0, c
	lb	 $t1, o
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, a		#Store the value at the address of a

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg7		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call for to print integer
	ld	 $a0, a		#Load the integer into a0
	syscall


	lb	 $t0, c
	lb	 $t1, o
	sub	 $t2, $t0, $t1		#subtract the two registers

	sw	 $t2, b		#Store the value at the address of b

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg8		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call for to print integer
	ld	 $a0, b		#Load the integer into a0
	syscall


	li $v0, 10		#Load system call to exit
	syscall


	.data

b:	.word	0,0,0
a:	.word	0,0,0
c:	.byte	'c'
o:	.byte	'o'
m:	.byte	'm'
p:	.byte	'p'
msg1:	.asciiz "c = "
msg2:	.asciiz "\no = "
msg3:	.asciiz "\nm = "
msg4:	.asciiz "\np = "
msg5:	.asciiz "\nPrinted together:\n"
msg6:	.asciiz "\n\nChars can be treated as ints:\n"
msg7:	.asciiz "\nc + o = "
msg8:	.asciiz "\nc - o = "
