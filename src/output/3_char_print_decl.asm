INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib



.data

var4_difference	SWORD	?
var3_sum	SWORD	?
var2_d	BYTE	?
var1_c	BYTE	?
msg1	 BYTE 'The value of c: ', 0 
msg2	 BYTE 'The value of d: ', 0 
msg3	 BYTE 'The sum of c + d = ', 0 
msg4	 BYTE 'The difference of c - d = ', 0 

; Function prototypes
WriteString PROTO
WriteChar PROTO
Crlf PROTO
WriteInt PROTO


.code

	main PROC

L1:
	MOV	 ah, 'c'		
	MOV	 var1_c, ah

L3:
	MOV	 ah, 'd'		
	MOV	 var2_d, ah

L4:
	MOVSX	 ax, var1_c
	MOVSX	 bx, var2_d
	ADD	 ax, bx		 ; add the two registers
	MOV	 var3_sum, ax

L5:
	MOVSX	 ax, var1_c
	MOVSX	 bx, var2_d
	SUB	 ax, bx		; subtract the two registers
	MOV	 var4_difference, ax

L6:
	MOV edx, OFFSET msg1
	CALL WriteString
 	MOV al, var1_c
	CALL WriteChar
 	CALL Crlf
L7:
	MOV edx, OFFSET msg2
	CALL WriteString
 	MOV al, var2_d
	CALL WriteChar
 	CALL Crlf
L8:
	MOV edx, OFFSET msg3
	CALL WriteString
 	MOVSX eax, var3_sum
	CALL WriteInt
 	CALL Crlf
L9:
	MOV edx, OFFSET msg4
	CALL WriteString
 	MOVSX eax, var4_difference
	CALL WriteInt
 	CALL Crlf
L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP
END main

mPrintBoolean MACRO boolean_val

.data

	var_bool BYTE boolean_val
	boolTrue BYTE 'True', 0
	boolFalse BYTE 'False', 0
.code

	PUSH eax
	PUSH edx

	MOVZX eax, boolean_val
	CMP eax, 1
	JNE PrintFalse:
	MOV edx, OFFSET boolTrue
	CALL WriteString
	JMP EndPrintFalse:
	PrintFalse:
	MOV edx, OFFSET boolFalse
	CALL WriteString
	EndPrintFalse:

	POP edx
	POP eax

ENDM
