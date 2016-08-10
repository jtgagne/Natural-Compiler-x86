
	.text


	.globl main

main:

	lb	 $t0, BOOL_TRUE		#Load a boolean value
	sb	 $t0, isWorking

	lb	 $t0, BOOL_TRUE		#Load a boolean value
	sb	 $t0, amHappy

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	lb	 $t0, isWorking		#Load the value to be compared
	beqz	 $t0, L2		#Goto L2 if greater than 0

L1:	li	 $v0, 4
	la 	$a0, BOOL_TRUE_STR
	syscall
	j	 L3

L2:	li	 $v0, 4
	la 	 $a0, BOOL_FALSE_STR
	syscall
	j	 L3

L3:
	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	lb	 $t1, amHappy		#Load the value to be compared
	beqz	 $t1, L5		#Goto L5 if greater than 0

L4:	li	 $v0, 4
	la 	$a0, BOOL_TRUE_STR
	syscall
	j	 L6

L5:	li	 $v0, 4
	la 	 $a0, BOOL_FALSE_STR
	syscall
	j	 L6

L6:
	lb	 $t2, isWorking
	lb	 $t3, amHappy
	add	 $t4, $t2, $t3		#add the two registers

	sw	 $t4, sum

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, sum		#Load the long into a0
	syscall


	lw	 $t0, sum
	li	 $t1, 1		#Load an immediate value into the register
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, sum

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, sum		#Load the long into a0
	syscall


	lw	 $t0, sum
	lb	 $t1, isWorking
	mul	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, product

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, product		#Load the long into a0
	syscall


	li $v0, 10		#Load system call to exit
	syscall


	.data

product:	.word	0,0,0
sum:	.word	0,0,0
amHappy:	.byte	0,0,0
isWorking:	.byte	0,0,0
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "value of isWorking: "
msg2:	.asciiz "\nvalue of amHappy: "
msg3:	.asciiz "\nsum = isWorking + isSick = "
msg4:	.asciiz "\nsum = sum + 1 = "
msg5:	.asciiz "\nproduct = sum * isWorking = "
