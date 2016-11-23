INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var3_sum	SWORD	?
var2_b	BYTE	?
var1_a	BYTE	?
msg1	 BYTE 'Enter two characters to find the sum.', 0 
msg2	 BYTE 'First char ', 0 
msg3	 BYTE 'Second char ', 0 
msg4	 BYTE 'The first char is: ', 0 
msg5	 BYTE 'The second char is: ', 0 
msg6	 BYTE 'The sum is: ', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO
ReadChar PROTO
WriteChar PROTO
WriteInt PROTO


.code

	main PROC

L1:
	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L3:	MOV edx, OFFSET msg2
	CALL WriteString
	CALL ReadChar
	MOV var1_a, al

	CALL Crlf

L4:	MOV edx, OFFSET msg3
	CALL WriteString
	CALL ReadChar
	MOV var2_b, al

	CALL Crlf

L5:
	MOVSX	 ax, var1_a
	MOVSX	 bx, var2_b
	ADD	 ax, bx		 ; add the two registers
	MOV	 var3_sum, ax

L6:
	MOV edx, OFFSET msg4
	CALL WriteString
 	MOV al, var1_a
	CALL WriteChar
 	CALL Crlf

L7:
	MOV edx, OFFSET msg5
	CALL WriteString
 	MOV al, var2_b
	CALL WriteChar
 	CALL Crlf

L8:
	MOV edx, OFFSET msg6
	CALL WriteString
 	MOVSX eax, var3_sum
	CALL WriteInt
 	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main