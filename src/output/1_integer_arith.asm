
	.text


	.globl main

main:

	li	 $t0, 1		#Load an immediate value into the register
	sw	 $t0, var1		#Store the value at the address of var1

	li	 $t0, 2		#Load an immediate value into the register
	sw	 $t0, var2		#Store the value at the address of var2

	lw	 $t0, var1
	li	 $t1, 1		#Load an immediate value into the register
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, var1		#Store the value at the address of var1

	lw	 $t0, var2
	li	 $t1, 1		#Load an immediate value into the register
	sub	 $t2, $t0, $t1		#subtract the two registers

	sw	 $t2, var2		#Store the value at the address of var2

	lw	 $t0, var1
	lw	 $t1, var2
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, sum		#Store the value at the address of sum

	lw	 $t0, var2
	lw	 $t1, var1
	sub	 $t2, $t0, $t1		#subtract the two registers

	sw	 $t2, difference		#Store the value at the address of difference

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call for to print integer
	ld	 $a0, var1		#Load the integer into a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call for to print integer
	ld	 $a0, var2		#Load the integer into a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call for to print integer
	ld	 $a0, sum		#Load the integer into a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call for to print integer
	ld	 $a0, difference		#Load the integer into a0
	syscall


	li	 $t0, 1022034		#Load an immediate value into the register
	sw	 $t0, bigNum		#Store the value at the address of bigNum

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call for to print integer
	ld	 $a0, bigNum		#Load the integer into a0
	syscall


	li $v0, 10		#Load system call to exit
	syscall


	.data

bigNum:	.word	0,0,0
difference:	.word	0,0,0
sum:	.word	0,0,0
var2:	.word	0,0,0
var1:	.word	0,0,0
msg1:	.asciiz "var1 = "
msg2:	.asciiz "\nvar2 = "
msg3:	.asciiz "\nvar1 + var2 = "
msg4:	.asciiz "\nvar2 - var1 = "
msg5:	.asciiz "\nbigNum = "
