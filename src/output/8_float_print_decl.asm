INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var8_difference	REAL4	?
var7_sum2	REAL4	?
var6_sum	REAL4	?
var4_i	SWORD	?
var1_f	REAL4	11.2
var2_f2	REAL4	11.2
var3_f3	REAL4	23.66
var5_f4	REAL4	53.2
msg1	 BYTE 'Printing f: ', 0 
msg2	 BYTE 'Printing the sum of f + f2 + f3: ', 0 
msg3	 BYTE 'Printing the difference', 0 


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
	MOV	 ax, 10		; Load an immediate value into the register
	MOV	 var4_i, ax

L6:
L7:
	FLD	var1_f
	FLD	var2_f2
	FADD		; Adds the values in ST(0) to ST(1), result in ST(0)
	FLD	var3_f3
	FADD		; Adds the values in ST(0) to ST(1), result in ST(0)
	FSTP	 var6_sum

L8:
	FLD	var3_f3
	FLD	var5_f4
	FADD		; Adds the values in ST(0) to ST(1), result in ST(0)
	FSTP	 var7_sum2

L9:
	FLD	var6_sum
	FLD	var7_sum2
	FSUB		; Calculate ST(1) - ST(0)
	FSTP	 var8_difference

L10:
	MOV edx, OFFSET msg1
	CALL WriteString
 	mPrintFloat <var1_f>
 	CALL Crlf

L11:
	MOV edx, OFFSET msg2
	CALL WriteString
 	mPrintFloat <var6_sum>
 	CALL Crlf

L12:
	MOV edx, OFFSET msg3
	CALL WriteString
 	mPrintFloat <var8_difference>
 	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main