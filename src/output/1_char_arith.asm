
	.text


	.globl main

main:

L1:	la	 $t0, CONST1		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, c

L3:	la	 $t0, CONST2		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, o

L4:	la	 $t0, CONST3		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, m

L5:	la	 $t0, CONST4		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, p

L6:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, c		#Load the char to a0
	syscall


L7:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, o		#Load the char to a0
	syscall


L8:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, m		#Load the char to a0
	syscall


L9:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 11		#System call for printing a char
	lb	 $a0, p		#Load the char to a0
	syscall


L10:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

L11:	li	 $v0, 11		#System call for printing a char
	lb	 $a0, c		#Load the char to a0
	syscall

L12:	li	 $v0, 11		#System call for printing a char
	lb	 $a0, o		#Load the char to a0
	syscall

L13:	li	 $v0, 11		#System call for printing a char
	lb	 $a0, m		#Load the char to a0
	syscall

L14:	li	 $v0, 11		#System call for printing a char
	lb	 $a0, p		#Load the char to a0
	syscall

L15:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg6		#Load the String to be printed
	syscall

L16:	lb	 $t0, c
	lb	 $t1, o
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, a

L17:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg7		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, a		#Load the long into a0
	syscall


L18:	lb	 $t0, c
	lb	 $t1, o
	sub	 $t2, $t0, $t1		#subtract the two registers

	sw	 $t2, b

L19:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg8		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, b		#Load the long into a0
	syscall


L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

b:	.word	0,0,0
a:	.word	0,0,0
p:	.byte	0,0,0
m:	.byte	0,0,0
o:	.byte	0,0,0
c:	.byte	0,0,0
CONST1:	.byte	'c'
CONST2:	.byte	'o'
CONST3:	.byte	'm'
CONST4:	.byte	'p'
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "c = "
msg2:	.asciiz "\no = "
msg3:	.asciiz "\nm = "
msg4:	.asciiz "\np = "
msg5:	.asciiz "\nPrinted together:\n"
msg6:	.asciiz "\n\nChars can be treated as ints:\n"
msg7:	.asciiz "\nc + o + m = "
msg8:	.asciiz "\nc - o = "
