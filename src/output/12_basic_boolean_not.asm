INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var2_isGrouchy	BYTE	?
var1_isHappy	BYTE	?
msg1	 BYTE 'Cheer up!', 0 
msg2	 BYTE 'Today is nice', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ah, 0h		; Load a boolean value
	MOV	 var1_isHappy, ah

L3:
	MOV	 ah, 0FFh		; Load a boolean value
	MOV	 var2_isGrouchy, ah

L4:	MOV	 bl, var1_isHappy
	NOT	 bl	; execute a bitwise not on the register
	MOV	 bh, var2_isGrouchy
	AND	 bl, bh
	CMP	 bl, 0FFh
	JNE	 L5


	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L5:	MOV	 ah, var1_isHappy
	NOT	 ah	; execute a bitwise not on the register

	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main