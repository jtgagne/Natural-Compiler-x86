INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var1_isWorking	BYTE	?
var2_isWorking2	BYTE	?
msg1	 BYTE 'Error with if statements', 0 
msg2	 BYTE 'if-else statement is working', 0 
msg3	 BYTE 'Falls through on true', 0 
msg4	 BYTE 'ERROR. Not skipping else if evaluated to true!', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ah, 0h		; Load a boolean value
	MOV	 var1_isWorking, ah

L3:	MOV	 al, var1_isWorking
	CMP	 al, 0FFh		; Check if true
	JNE	 L6

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

	JMP	 L4

L6:
	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L4:
	MOV	 ah, 0FFh		; Load a boolean value
	MOV	 var2_isWorking2, ah

L7:	MOV	 al, var2_isWorking2
	CMP	 al, 0FFh		; Check if true
	JNE	 L9

	MOV edx, OFFSET msg3
	CALL WriteString
	CALL Crlf

	JMP	 L2

L9:
	MOV edx, OFFSET msg4
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main