INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var4_doesntSmoke	BYTE	?
var3_doesntDrink	BYTE	?
var2_getsSleep	BYTE	?
var1_eatsWell	BYTE	?
var5_isHealthy	BYTE	?
var6_isNotHealthy	BYTE	?
msg1	 BYTE 'You are healthy', 0 
msg2	 BYTE 'You are not healthy!', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ah, 1		; Load a boolean value
	MOV	 var1_eatsWell, ah

L3:
	MOV	 ah, 1		; Load a boolean value
	MOV	 var2_getsSleep, ah

L4:
	MOV	 ah, 1		; Load a boolean value
	MOV	 var3_doesntDrink, ah

L5:
	MOV	 ah, 1		; Load a boolean value
	MOV	 var4_doesntSmoke, ah

L6:
	MOV	 ah, 0		; Load a boolean value
	MOV	 var5_isHealthy, ah

L7:
	MOV	 ah, 1		; Load a boolean value
	MOV	 var6_isNotHealthy, ah

L8:	MOV	 ch, var1_eatsWell
	MOV	 cl, var2_getsSleep
	AND	ch, cl
	CMP	ch, 1
	JNE	L9

	MOV	 ah, var3_doesntDrink
	AND	ch, ah
	CMP	ch, 1
	JNE	L9

	MOV	 ah, var4_doesntSmoke
	AND	ch, ah
	CMP	ch, 1
	JNE	L9


	MOV	 ah, 1		; Load a boolean value
	MOV	 var5_isHealthy, ah

L11:
	MOV	 ah, 0		; Load a boolean value
	MOV	 var6_isNotHealthy, ah

L9:	MOV	 ah, var5_isHealthy
	CMP	ah, 1		; Check if true
	JNE	L12

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L12:	MOV	 ah, var6_isNotHealthy
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