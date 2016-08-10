
	.text


	.globl main

main:

	li	 $t0, 1		#Load an immediate value into the register
	sw	 $t0, var1

	li	 $t0, 2		#Load an immediate value into the register
	sw	 $t0, var2

	lw	 $t0, var1
	li	 $t1, 1		#Load an immediate value into the register
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, var1

	lw	 $t0, var2
	li	 $t1, 1		#Load an immediate value into the register
	sub	 $t2, $t0, $t1		#subtract the two registers

	sw	 $t2, var2

	lw	 $t0, var1
	lw	 $t1, var2
	add	 $t2, $t0, $t1		#add the two registers

	sw	 $t2, sum

	lw	 $t0, var2
	lw	 $t1, var1
	sub	 $t2, $t0, $t1		#subtract the two registers

	sw	 $t2, difference

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


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, sum		#Load the long into a0
	syscall


	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, difference		#Load the long into a0
	syscall


	li	 $t0, 1022034		#Load an immediate value into the register
	sw	 $t0, bigNum

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, bigNum		#Load the long into a0
	syscall


	li	 $t0, 5		#Load an immediate value into the register
	sw	 $t0, f1

	lw	 $t0, f1
	lw	 $t1, f1
	mul	 $t2, $t0, $t1		#add the two registers

	lw	 $t3, f1
	mul	 $t4, $t2, $t3		#add the two registers

	sw	 $t4, f1

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg6		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, f1		#Load the long into a0
	syscall


	lw	 $t0, f1
	li	 $t1, 5		#Load an immediate value into the register
	div	 $t2, $t0, $t1		#subtract the two registers

	sw	 $t2, f2

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg7		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, f2		#Load the long into a0
	syscall


	li $v0, 10		#Load system call to exit
	syscall


	.data

f2:	.word	0,0,0
f1:	.word	0,0,0
bigNum:	.word	0,0,0
difference:	.word	0,0,0
sum:	.word	0,0,0
var2:	.word	0,0,0
var1:	.word	0,0,0
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "var1 = "
msg2:	.asciiz "\nvar2 = "
msg3:	.asciiz "\nvar1 + var2 = "
msg4:	.asciiz "\nvar2 - var1 = "
msg5:	.asciiz "\nbigNum = "
msg6:	.asciiz "\nf1*f1*f1 = "
msg7:	.asciiz "\nf2 = f1 / 5 = "
