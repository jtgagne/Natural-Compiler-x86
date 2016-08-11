
	.text


	.globl main

main:

L1:	li	 $t0, 1		#Load an immediate value into the register
	sw	 $t0, var1

L3:	li	 $t0, 2		#Load an immediate value into the register
	sw	 $t0, var2

L4:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var1		#Load the long into a0
	syscall


L5:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var2		#Load the long into a0
	syscall


L6:	lw	 $t0, var1
	lw	 $t1, var2
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, sum

L7:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

L8:	li	 $v0, 1		#Load system call to print long
	ld	 $a0, sum		#Load the long into a0
	syscall

L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

sum:	.word	0,0,0
var2:	.word	0,0,0
var1:	.word	0,0,0
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "var1 is "
msg2:	.asciiz "\nvar2 is "
msg3:	.asciiz "\nThe sum of var1 + var2 is "
