INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib



.data

sum	SWORD	?
difference	SWORD	?
num2	SWORD	?
num	SWORD	?
msg1	 BYTE '20 + 10 = ', 0 
msg2	 BYTE '30 + 10 = ', 0 
msg3	 BYTE '10 - 20 = ', 0 
msg4	 BYTE 'hello world mother fucker', 0 
msg5	 BYTE 'how is you doing today home slice?', 0 
msg6	 BYTE 'num is: ', 0 

; Function prototypes
WriteString PROTO
WriteInt PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ax, 20		;Load an immediate value into the register
	MOV	 num, ax

L3:
	MOV	 ax, 10		;Load an immediate value into the register
	MOV	 num2, ax

L4:
	MOV	 ax, num
	MOV	 bx, num2
	ADD	 ax, bx		 ; add the two registers
	MOV	 sum, ax

L5:
	MOV edx, OFFSET msg1
	CALL WriteString
 	MOVSX eax, sum
	CALL WriteInt
 	CALL Crlf
L6:
	MOV	 ax, sum
	MOV	 bx, 10		;Load an immediate value into the register
	ADD	 ax, bx		 ; add the two registers
	MOV	 sum, ax

L7:
	MOV edx, OFFSET msg2
	CALL WriteString
 	MOVSX eax, sum
	CALL WriteInt
 	CALL Crlf
L8:
	MOV	 ax, num2
	MOV	 bx, num
	SUB	 ax, bx		; subtract the two registers
	MOV	 difference, ax

L9:
	MOV edx, OFFSET msg3
	CALL WriteString
 	MOVSX eax, difference
	CALL WriteInt
 	CALL Crlf
L10:
	MOV edx, OFFSET msg4
	CALL WriteString
	CALL Crlf
L11:
	MOV edx, OFFSET msg5
	CALL WriteString
	CALL Crlf
L12:
	MOV edx, OFFSET msg6
	CALL WriteString
 	MOVSX eax, num
	CALL WriteInt
 	CALL Crlf
L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP
END main

