INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var2_isGrouchy	BYTE	?
var1_isHappy	BYTE	?
msg1	 BYTE 'What is the matter?', 0 
msg2	 BYTE 'Such happiness', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ah, 1		; Load a boolean value
	MOV	 var1_isHappy, ah

L3:
	MOV	 ah, 0		; Load a boolean value
	MOV	 var2_isGrouchy, ah

L4:	MOV	 ah, var2_isGrouchy
	CMP	ah, 1		; Check if true
	JNE	L5

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L5:	MOV	 ah, var1_isHappy
	CMP	ah, 1		; Check if true
	JNE	L2

	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main