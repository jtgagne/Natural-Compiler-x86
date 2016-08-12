
	.text


	.globl main

main:

L1:	la	 $t0, CONST1		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, c1

L3:	la	 $t0, CONST2		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, c2

L4:	la	 $t0, CONST3		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, c3

L5:	la	 $t0, CONST4		
	lb	 $t1, 0($t0)		#Load an immediate value to register
	sb	 $t1, c4

L6:	lb	 $t0, c1
	lb	 $t1, c2
	slt	 $s0, $t0, $t1
	beqz	 $s0, L7

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

L7:	lb	 $t0, c1
	lb	 $t1, c2
	sgt	 $s0, $t0, $t1
	beqz	 $s0, L9

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

L9:	lb	 $t0, c1
	lb	 $t1, c2
	sge	 $s0, $t0, $t1
	beqz	 $s0, L2

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

c4:	.byte	0,0,0
c3:	.byte	0,0,0
c1:	.byte	0,0,0
c2:	.byte	0,0,0
CONST1:	.byte	'c'
CONST2:	.byte	'h'
CONST3:	.byte	'a'
CONST4:	.byte	'r'
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "c < h"
msg2:	.asciiz "this is ignored"
msg3:	.asciiz "\nGreater than or equal to works"
