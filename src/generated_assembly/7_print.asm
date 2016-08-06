
	.text


	.globl main

main:

	li $v0,4
	la $a0, msg1
	syscall

	li $v0,4
	la $a0, msg2
	syscall

	li $v0,4
	la $a0, msg3
	syscall

	li $v0,4
	la $a0, msg4
	syscall

	li $v0,4
	la $a0, msg5
	syscall

	li $v0, 10	#Code for syscall:exit
	syscall

	.data

msg1:	.asciiz "Hello World\n"
msg2:	.asciiz "Hello World 2\n"
msg3:	.asciiz "Hello World 3\n"
msg4:	.asciiz "Hello World 4\n"
msg5:	.asciiz "Hello World 5\n"
