INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var2_num2	SDWORD	?
var1_num1	SDWORD	?
msg1	 BYTE 'num1 is less than num2: 10 < 533', 0 
msg2	 BYTE 'ERROR: this should not print', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 eax, 10		; Load an immediate value into the register
	MOV	 var1_num1, eax

L3:
	MOV	 eax, 533		; Load an immediate value into the register
	MOV	 var2_num2, eax

L4:	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOV eax, var1_num1
	MOV ebx, var2_num2
	PUSH ebx
	PUSH eax
	mLessThanToBool
	POP eax
	MOV	 ah, 0FFh		; Load a boolean value
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L6

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

	JMP	 L2

L6:
	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main