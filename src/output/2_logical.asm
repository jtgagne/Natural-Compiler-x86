
	.text


	.globl main

main:

L1:	li	 $t0, 5		#Load an immediate value into the register
	sw	 $t0, var1

L3:	li	 $t0, 10		#Load an immediate value into the register
	sw	 $t0, var2

L4:	lb	 $t0, BOOL_TRUE		#Load a boolean value
	sb	 $t0, isGood

L5:	lw	 $t0, var1
	lw	 $t1, var2
	bgt	 $t0, $t1, L6

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

L6:	lb	 $t2, isGood
	lb	 $t1, BOOL_TRUE
	bne	 $t1, $t2, L8

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

L8:	lw	 $t3, var1
	lw	 $t4, var2
	ble	 $t3, $t4, L10

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

L10:	lw	 $t0, var1
	sw	 $t0, var3

L12:	lw	 $t0, var1
	lw	 $t1, var3
	blt	 $t0, $t1, L2

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

var1:	.word	0,0,0
var2:	.word	0,0,0
isGood:	.byte	0,0,0
var3:	.word	0,0,0
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "var1 is less than var2"
msg2:	.asciiz "\n\nWe outchea"
msg3:	.asciiz "This should not print"
msg4:	.asciiz "\nvar1 equals var3"
