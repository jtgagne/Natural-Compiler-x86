INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var4_isTrue	BYTE	?
var3_isFalse2	BYTE	?
var2_isFalse1	BYTE	?
var1_orWorks	BYTE	?
msg1	 BYTE 'Boolean or is working', 0 
msg2	 BYTE 'The value of orWorks: ', 0 
msg3	 BYTE 'Boolean and with immediate values also works', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ah, 0h		; Load a boolean value
	MOV	 var2_isFalse1, ah

L3:
	MOV	 ah, 0h		; Load a boolean value
	MOV	 var3_isFalse2, ah

L4:
	MOV	 ah, 0FFh		; Load a boolean value
	MOV	 var4_isTrue, ah

L5:	MOV	 bh, var2_isFalse1
	MOV	 ch, var3_isFalse2
	OR	 bh, ch
	MOV	 ch, var4_isTrue
	OR	 bh, ch
	CMP	 bh, 0FFh		; Check if true
	JNE	 L6

	MOV	 ch, 0FFh		; Load a boolean value
	MOV	 var1_orWorks, ch

L8:
	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L9:
	MOV edx, OFFSET msg2
	CALL WriteString
 	mPrintBoolean <var1_orWorks>
 	CALL Crlf

L6:	MOV	 ch, var4_isTrue
	MOV	 cl, 0FFh		; Load a boolean value
	AND	 ch, cl
	MOV	 ah, 0h		; Load a boolean value
	NOT	 ah	; execute a bitwise not on the register
	AND	 ch, ah
	CMP	 ch, 0FFh		; Check if true
	JNE	 L2

	MOV edx, OFFSET msg3
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main