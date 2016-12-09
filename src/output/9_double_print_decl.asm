INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var1_d	REAL8	?


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


.code

	main PROC

L1:
nullL2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main BYTE 'd2 - d1 - d3 = ', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
WriteFloat	PROTO
Crlf PROTO


.code

	main PROC

L1:
L3:
L4:
L5:
	FLD	 var1_d1
	FLD	 var2_d2
	FADD		; Adds the values in ST(0) to ST(1), result in ST(0)
	FLD	 var3_d3
	FADD		; Adds the values in ST(0) to ST(1), result in ST(0)
	FSTP	 var4_sum

L6:
	FLD	 var2_d2
	FLD	 var1_d1
	FSUB		; Calculate ST(1) - ST(0)
	FLD	 var3_d3
	FSUB		; Calculate ST(1) - ST(0)
	FSTP	 var5_difference

L7:
	MOV edx, OFFSET msg1
	CALL WriteString
 	mPrintFloat <var1_d1>
 	CALL Crlf

L8:
	MOV edx, OFFSET msg2
	CALL WriteString
 	mPrintFloat <var2_d2>
 	CALL Crlf

L9:
	MOV edx, OFFSET msg3
	CALL WriteString
 	mPrintFloat <var3_d3>
 	CALL Crlf

L10:
	MOV edx, OFFSET msg4
	CALL WriteString
 	mPrintFloat <var4_sum>
 	CALL Crlf

L11:
	MOV edx, OFFSET msg5
	CALL WriteString
 	mPrintFloat <var5_difference>
 	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main