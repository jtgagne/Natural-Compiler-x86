
	.text


	.globl main

main:

L1:	lb	 $t0, BOOL_TRUE		#Load a boolean value
	sb	 $t0, b

L3:	li	 $t0, 5		#Load an immediate value into the register
	sw	 $t0, num1

L4:	li	 $t0, 6		#Load an immediate value into the register
	sw	 $t0, num2

L5:	lb	 $t1, b
	beqz	 $t1, L8

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	j	 L6

L8:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

L6:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

L9:	lw	 $t0, num1
	lw	 $t1, num2
	sgt	 $s0, $t0, $t1
	beqz	 $s0, L11

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	j	 L2

L11:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

b:	.byte	0,0,0
num1:	.word	0,0,0
num2:	.word	0,0,0
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "Hello "
msg2:	.asciiz "World\n"
msg3:	.asciiz "World"
msg4:	.asciiz "This should not be printed"
msg5:	.asciiz "\nnum1 is less than num2"
