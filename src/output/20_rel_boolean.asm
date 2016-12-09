INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var2_isFalse	BYTE	?
var1_isTrue	BYTE	?
msg1	 BYTE 'isFalse is less than isTrue', 0 
msg2	 BYTE 'ERROR: isFalse ! > isTrue', 0 
msg3	 BYTE 'isTrue is greater than or equal to isFalse', 0 
msg4	 BYTE 'isFalse is less than or equal to isTrue', 0 
msg5	 BYTE 'ERROR: true is not equal to false', 0 
msg6	 BYTE 'true is not equal to false', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ah, 0FFh		; Load a boolean value
	MOV	 var1_isTrue, ah

L3:
	MOV	 ah, 0h		; Load a boolean value
	MOV	 var2_isFalse, ah

L4:	MOVZX ax, var2_isFalse
	MOVZX bx, var1_isTrue
	CMP	 ax, bx
	JNL	 L5

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L5:	MOVZX ax, var2_isFalse
	MOVZX bx, var1_isTrue
	CMP	 ax, bx
	JNG	 L7

	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L7:	MOVZX ax, var1_isTrue
	MOVZX bx, var2_isFalse
	CMP	 ax, bx
	JNGE	 L9

	MOV edx, OFFSET msg3
	CALL WriteString
	CALL Crlf

L9:	MOVZX ax, var2_isFalse
	MOVZX bx, var1_isTrue
	CMP	 ax, bx
	JNLE	 L11

	MOV edx, OFFSET msg4
	CALL WriteString
	CALL Crlf

L11:	MOVZX ax, var1_isTrue
	MOVZX bx, var2_isFalse
	CMP	 ax, bx
	JNE	 L13

	MOV edx, OFFSET msg5
	CALL WriteString
	CALL Crlf

L13:	MOVZX ax, var1_isTrue
	MOVZX bx, var2_isFalse
	CMP	 ax, bx
	JE	 L2

	MOV edx, OFFSET msg6
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main