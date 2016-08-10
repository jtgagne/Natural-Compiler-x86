
	.text


	.globl main

main:

	li	 $t0, 2		#Load an immediate value into the register
	sw	 $t0, var1		#Store the value at the address of var1

	li	 $t0, 10		#Load an immediate value into the register
	sw	 $t0, var2		#Store the value at the address of var2

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var1		#Load the long into a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var2		#Load the long into a0
	syscall


	lw	 $t0, var1
	lw	 $t1, var2
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, var3		#Store the value at the address of var3

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var3		#Load the long into a0
	syscall


	li	 $t0, 2		#Load an immediate value into the register
	sw	 $t0, var4		#Store the value at the address of var4

	lw	 $t0, var4
	li	 $t1, 12		#Load an immediate value into the register
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, var4		#Store the value at the address of var4

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var4		#Load the long into a0
	syscall


	li	 $t0, 19923		#Load an immediate value into the register
	sw	 $t0, var5		#Store the value at the address of var5

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var5		#Load the long into a0
	syscall


	lw	 $t0, var5
	li	 $t1, 20000		#Load an immediate value into the register
	sub	 $t2, $t0, $t1		#subtract the two registers

	sw	 $t2, var5		#Store the value at the address of var5

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg6		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var5		#Load the long into a0
	syscall


	li $v0, 10		#Load system call to exit
	syscall


	.data

var5:	.word	0,0,0
var4:	.word	0,0,0
var3:	.word	0,0,0
var2:	.word	0,0,0
var1:	.word	0,0,0
msg1:	.asciiz "var1 = "
msg2:	.asciiz "\nvar2 = "
msg3:	.asciiz "\nvar3 = "
msg4:	.asciiz "\nvar4 = "
msg5:	.asciiz "\nvar5 = "
msg6:	.asciiz "\nvar5 - 20000 = "
