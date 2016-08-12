
	.text


	.globl main

main:

L1:	li	 $t0, 4		#Load an immediate value into the register
	sw	 $t0, var1

L3:	li	 $t0, 5		#Load an immediate value into the register
	sw	 $t0, var2

L4:	lb	 $t0, BOOL_TRUE		#Load a boolean value
	sb	 $t0, isWorking

L5:	lb	 $t2, isWorking
	xor	 $t3, $t2, $t2
	beqz	 $t3, L6

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

L6:	lb	 $t2, isWorking
	lb	 $t1, BOOL_TRUE
	bne	 $t1, $t2, L8

	beqz	 $t2, L8

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

L8:	lw	 $t0, var1
	lw	 $t1, var2
	slt	 $s0, $t0, $t1
	beqz	 $s0, L10

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

L10:	lw	 $t0, var1
	lw	 $t1, var2
	sgt	 $s0, $t0, $t1
	beqz	 $s0, L12

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

L12:	lw	 $t1, var2
	lw	 $t2, var1
	sgt	 $s0, $t1, $t2
	lb	 $t3, isWorking
	and	 $s1, $s0, $t3
	li	 $t4, 4		#Load an immediate value into the register
	li	 $t5, 5		#Load an immediate value into the register
	slt	 $s2, $t4, $t5
	and	 $s3, $s1, $s2
	beqz	 $s3, L14

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

L14:	lb	 $t4, BOOL_FALSE		#Load a boolean value
	lb	 $t5, BOOL_FALSE		#Load a boolean value
	or	 $s3, $t4, $t5
	lb	 $t6, BOOL_FALSE		#Load a boolean value
	or	 $s4, $s3, $t6
	lb	 $t7, BOOL_TRUE		#Load a boolean value
	or	 $s5, $s4, $t7
	beqz	 $s5, L16

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg6		#Load the String to be printed
	syscall

L18:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg7		#Load the String to be printed
	syscall

L16:	lw	 $t1, var2
	lw	 $t2, var1
	sgt	 $s0, $t1, $t2
	lb	 $t3, isWorking
	and	 $s1, $s0, $t3
	li	 $t4, 4		#Load an immediate value into the register
	li	 $t5, 5		#Load an immediate value into the register
	sgt	 $s2, $t4, $t5
	and	 $s3, $s1, $s2
	beqz	 $s3, L19

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg8		#Load the String to be printed
	syscall

L19:	lw	 $t0, var2
	lw	 $t1, var1
	sge	 $s0, $t0, $t1
	beqz	 $s0, L21

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg9		#Load the String to be printed
	syscall

L21:	lw	 $t0, var1
	lw	 $t1, var2
	sle	 $s0, $t0, $t1
	beqz	 $s0, L23

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg10		#Load the String to be printed
	syscall

L23:	lw	 $t0, var2
	sw	 $t0, var1

L25:	lw	 $t0, var1
	lw	 $t1, var2
	seq	 $s0, $t0, $t1
	beqz	 $s0, L2

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg11		#Load the String to be printed
	syscall

L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

isWorking:	.byte	0,0,0
var1:	.word	0,0,0
var2:	.word	0,0,0
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "This will not print\n"
msg2:	.asciiz "This will print\n"
msg3:	.asciiz "Less than is working properly\n"
msg4:	.asciiz "Error with greater than\n"
msg5:	.asciiz "Greater than is working properly\n"
msg6:	.asciiz "Testing 'OR'\n"
msg7:	.asciiz "'OR' is working correctly\n"
msg8:	.asciiz "This will not print\n"
msg9:	.asciiz "Greater than or equal to is working properly\n"
msg10:	.asciiz "Less than or equal to is working properly\n"
msg11:	.asciiz "Equality is working properly\n"
