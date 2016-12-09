INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var1_f1	REAL8	10.3
var2_f2	REAL8	11.5
var3_f3	REAL8	11.5
msg1	 BYTE 'Testing logical comparisons with TYPE: double', 0 
msg2	 BYTE 'ERROR: f2 is not less than f3', 0 
msg3	 BYTE 'SUCCESS: less than', 0 
msg4	 BYTE 'ERROR: f2 is not greater than f3', 0 
msg5	 BYTE 'SUCCESS: greater than', 0 
msg6	 BYTE 'ERROR: f1 is not greater than or equal to f3', 0 
msg7	 BYTE 'SUCCESS: greater than or equal to', 0 
msg8	 BYTE 'SUCCESS: less than or equal to', 0 
msg9	 BYTE 'ERROR: f1 should evaluate to be less than or equal to f2', 0 
msg10	 BYTE 'ERROR: f1 is not equal to f2', 0 
msg11	 BYTE 'SUCCESS: equal to', 0 
msg12	 BYTE 'ERROR: f2 is equal to f3', 0 
msg13	 BYTE 'SUCCESS: not equal to', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
L3:
L4:
L5:
	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L6:	FLD	 var2_f2
	FLD	 var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JBE	 L9

	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

	JMP	 L7

L9:
	MOV edx, OFFSET msg3
	CALL WriteString
	CALL Crlf

L7:	FLD	 var2_f2
	FLD	 var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JAE	 L12

	MOV edx, OFFSET msg4
	CALL WriteString
	CALL Crlf

	JMP	 L10

L12:
	MOV edx, OFFSET msg5
	CALL WriteString
	CALL Crlf

L10:	FLD	 var1_f1
	FLD	 var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JB	 L15

	MOV edx, OFFSET msg6
	CALL WriteString
	CALL Crlf

	JMP	 L13

L15:
	MOV edx, OFFSET msg7
	CALL WriteString
	CALL Crlf

L13:	FLD	 var1_f1
	FLD	 var2_f2
	FCOMPP
	FNSTSW ax
	SAHF
	JNLE	 L18

	MOV edx, OFFSET msg8
	CALL WriteString
	CALL Crlf

	JMP	 L16

L18:
	MOV edx, OFFSET msg9
	CALL WriteString
	CALL Crlf

L16:	FLD	 var1_f1
	FLD	 var2_f2
	FCOMPP
	FNSTSW ax
	SAHF
	JNE	 L21

	MOV edx, OFFSET msg10
	CALL WriteString
	CALL Crlf

	JMP	 L19

L21:
	MOV edx, OFFSET msg11
	CALL WriteString
	CALL Crlf

L19:	FLD	 var2_f2
	FLD	 var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JE	 L23

	MOV edx, OFFSET msg12
	CALL WriteString
	CALL Crlf

	JMP	 L2

L23:
	MOV edx, OFFSET msg13
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main