INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var1_f1	REAL4	10.3
var2_f2	REAL4	11.5
var3_f3	REAL4	11.5
msg1	 BYTE 'f1 is less than f2', 0 
msg2	 BYTE 'ERROR: f2 is not less than f3', 0 
msg3	 BYTE 'ERROR: f2 is not greater than f3', 0 
msg4	 BYTE 'f2 is greater than f1', 0 
msg5	 BYTE 'GE is working', 0 
msg6	 BYTE 'ERROR: f1 is not greater than or equal to f3', 0 
msg7	 BYTE 'ERROR: f1 is not equal to f2', 0 
msg8	 BYTE 'f2 is equal to f3', 0 
msg9	 BYTE 'f1 is not equal to f3', 0 
msg10	 BYTE 'ERROR: f2 is equal to f3', 0 


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
L5:	FLD	var1_f1
	FLD	var2_f2
	FCOMPP
	FNSTSW ax
	SAHF
	JBE	 L6

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L6:	FLD	var2_f2
	FLD	var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JBE	 L8

	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L8:	FLD	var2_f2
	FLD	var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JAE	 L10

	MOV edx, OFFSET msg3
	CALL WriteString
	CALL Crlf

L10:	FLD	var2_f2
	FLD	var1_f1
	FCOMPP
	FNSTSW ax
	SAHF
	JAE	 L12

	MOV edx, OFFSET msg4
	CALL WriteString
	CALL Crlf

L12:	FLD	var2_f2
	FLD	var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JB	 L14

	MOV edx, OFFSET msg5
	CALL WriteString
	CALL Crlf

L14:	FLD	var1_f1
	FLD	var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JB	 L16

	MOV edx, OFFSET msg6
	CALL WriteString
	CALL Crlf

L16:	FLD	var1_f1
	FLD	var2_f2
	FCOMPP
	FNSTSW ax
	SAHF
	JNE	 L18

	MOV edx, OFFSET msg7
	CALL WriteString
	CALL Crlf

L18:	FLD	var2_f2
	FLD	var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JNE	 L20

	MOV edx, OFFSET msg8
	CALL WriteString
	CALL Crlf

L20:	FLD	var1_f1
	FLD	var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JE	 L22

	MOV edx, OFFSET msg9
	CALL WriteString
	CALL Crlf

L22:	FLD	var2_f2
	FLD	var3_f3
	FCOMPP
	FNSTSW ax
	SAHF
	JE	 L2

	MOV edx, OFFSET msg10
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main