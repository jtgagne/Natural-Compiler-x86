
	.text


	.globl main

main:

L1:	li	 $t0, 5		#Load an immediate value into the register
	sw	 $t0, var1

L3:	li	 $t0, 10		#Load an immediate value into the register
	sw	 $t0, var2

L4:	la	 $a0, CONST5		 #Load an immediate value to register
	l.s	 $f2, 0($a0)		 #Load the value at the address
	s.s	 $f2, f1

L5:	la	 $a0, CONST6		 #Load an immediate value to register
	l.s	 $f2, 0($a0)		 #Load the value at the address
	s.s	 $f2, f2

L6:	lb	 $t0, BOOL_TRUE		#Load a boolean value
	sb	 $t0, isGood

L7:	lw	 $t0, var1
	lw	 $t1, var2
	slt	 $s0, $t0, $t1
	beqz	 $s0, L8

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg1		#Load the String to be printed
	syscall

L8:	lb	 $t2, isGood
	lb	 $t1, BOOL_TRUE
	bne	 $t1, $t2, L10

	beqz	 $t2, L10

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg2		#Load the String to be printed
	syscall

L10:	lw	 $t0, var1
	lw	 $t1, var2
	sgt	 $s0, $t0, $t1
	beqz	 $s0, L12

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg3		#Load the String to be printed
	syscall

L12:	lw	 $t0, var1
	sw	 $t0, var3

L14:	lw	 $t0, var1
	lw	 $t1, var3
	sge	 $s0, $t0, $t1
	beqz	 $s0, L15

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg4		#Load the String to be printed
	syscall

L15:	l.s	 $f2, f1
	l.s	 $f3, f2
	c.le.s	 $f2, $f3
	bc1f	 0, L17

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg5		#Load the String to be printed
	syscall

L19:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg6		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, f1		#Load the float from f12 to f1
	syscall


L20:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg7		#Load the String to be printed
	syscall

	li	 $v0, 2		#Load system call to print float
	l.s	 $f12, f2		#Load the float from f12 to f2
	syscall


L17:	lw	 $t0, var2
	lw	 $t1, var3
	sgt	 $s0, $t0, $t1
	beqz	 $s0, L21

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg8		#Load the String to be printed
	syscall

L23:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg9		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var2		#Load the long into a0
	syscall


L24:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg10		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var3		#Load the long into a0
	syscall


L21:	lw	 $t0, var3
	lw	 $t1, var1
	seq	 $s0, $t0, $t1
	beqz	 $s0, L2

	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg11		#Load the String to be printed
	syscall

L26:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg12		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var3		#Load the long into a0
	syscall


L27:	li	 $v0,4		#Load the system call to print a string
	la	 $a0, msg13		#Load the String to be printed
	syscall

	li	 $v0, 1		#Load system call to print long
	ld	 $a0, var1		#Load the long into a0
	syscall


L2:	li $v0, 10		#Load system call to exit
	syscall


	.data

var1:	.word	0,0,0
var2:	.word	0,0,0
isGood:	.byte	0,0,0
var3:	.word	0,0,0
f1:	.float	0,0,0
f2:	.float	0,0,0
CONST5:	.float	12.330000
CONST6:	.float	33.200001
BOOL_TRUE:	.byte	1
BOOL_FALSE:	.byte	0
BOOL_TRUE_STR:	.asciiz	"true"
BOOL_FALSE_STR:	.asciiz	"false"
msg1:	.asciiz "var1 is less than var2"
msg2:	.asciiz "\n\nWe outchea"
msg3:	.asciiz "This should not print"
msg4:	.asciiz "\nvar1 equals var3"
msg5:	.asciiz "\nf1 less than or equal to f2"
msg6:	.asciiz "\nf1 = "
msg7:	.asciiz "\nf2 = "
msg8:	.asciiz "\n\nvar2 is greater than var3"
msg9:	.asciiz "\nvar2 = "
msg10:	.asciiz "\nvar3 = "
msg11:	.asciiz "\n\nvar1 = var3"
msg12:	.asciiz "\nvar3 = "
msg13:	.asciiz "\nvar1 = "
