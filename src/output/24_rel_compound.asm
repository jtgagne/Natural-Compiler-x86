INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var4_isTrue	BYTE	?
var3_num3	SWORD	?
var1_num1	SWORD	?
var2_num2	SWORD	?
msg1	 BYTE 'num1: ', 0 
msg2	 BYTE 'num2: ', 0 
msg3	 BYTE 'num3: ', 0 
msg4	 BYTE 'Evaluating: num2 < num1', 0 
msg5	 BYTE 'FAILURE', 0 
msg6	 BYTE 'SUCCESS: expression evaluated false', 0 
msg7	 BYTE 'Evaluating: num1 > num2', 0 
msg8	 BYTE 'FAILURE: expression evaluated to true', 0 
msg9	 BYTE 'SUCCESS: expression evaluated false', 0 
msg10	 BYTE 'Evaluating: num2 >= num1', 0 
msg11	 BYTE 'SUCCESS: expression evaluated to true', 0 
msg12	 BYTE 'FAILURE: expression evaluated false', 0 
msg13	 BYTE 'Evaluating: num2 >= num3', 0 
msg14	 BYTE 'SUCCESS: expression evaluated to true', 0 
msg15	 BYTE 'FAILURE: expression evaluated false', 0 
msg16	 BYTE 'Evaluating: num3 <= num1', 0 
msg17	 BYTE 'FAILURE: expression evaluated true', 0 
msg18	 BYTE 'SUCCESS: expression evaluated to false', 0 
msg19	 BYTE 'Evaluating: num2 <= num3', 0 
msg20	 BYTE 'SUCCESS: expression evaluated to true', 0 
msg21	 BYTE 'FAILURE: expression evaluated false', 0 
msg22	 BYTE 'Evaluating: num2 == num3', 0 
msg23	 BYTE 'SUCCESS: expression evaluated to true', 0 
msg24	 BYTE 'FAILURE: expression evaluated false', 0 
msg25	 BYTE 'Evaluating: num2 == num1', 0 
msg26	 BYTE 'FAILURE: expression evaluated to true', 0 
msg27	 BYTE 'SUCCESS: expression evaluated false', 0 
msg28	 BYTE 'Evaluating: num2 != num1', 0 
msg29	 BYTE 'SUCCESS: expression evaluated to true', 0 
msg30	 BYTE 'FAILURE: expression evaluated false', 0 
msg31	 BYTE 'Evaluating: num2 != num3', 0 
msg32	 BYTE 'FAILURE: expression evaluated to true', 0 
msg33	 BYTE 'SUCCESS: expression evaluated false', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
WriteInt PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ax, 10		; Load an immediate value into the register
	MOV	 var1_num1, ax

L3:
	MOV	 ax, 20		; Load an immediate value into the register
	MOV	 var2_num2, ax

L4:
	MOV	 ax, var2_num2
	MOV	 var3_num3, ax

L5:
	MOV	 ah, 0FFh		; Load a boolean value
	MOV	 var4_isTrue, ah

L6:
	MOV edx, OFFSET msg1
	CALL WriteString
 	MOVSX eax, var1_num1
	CALL WriteInt
 	CALL Crlf

L7:
	MOV edx, OFFSET msg2
	CALL WriteString
 	MOVSX eax, var2_num2
	CALL WriteInt
 	CALL Crlf

L8:
	MOV edx, OFFSET msg3
	CALL WriteString
 	MOVSX eax, var3_num3
	CALL WriteInt
 	CALL Crlf

L9:
	MOV edx, OFFSET msg4
	CALL WriteString
	CALL Crlf

L10:	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var2_num2
	MOVSX ebx, var1_num1
	PUSH ebx
	PUSH eax
	mLessThanToBool
	POP eax
	MOV	 ah, var4_isTrue
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L13

	MOV edx, OFFSET msg5
	CALL WriteString
	CALL Crlf

	JMP	 L11

L13:
	MOV edx, OFFSET msg6
	CALL WriteString
	CALL Crlf

L11:
	MOV edx, OFFSET msg7
	CALL WriteString
	CALL Crlf

L14:	MOV	 al, var4_isTrue
	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var1_num1
	MOVSX ebx, var2_num2
	PUSH ebx
	PUSH eax
	mGreaterThanToBool
	POP eax
	AND	 al, al
	MOV	 ah, 0FFh		; Load a boolean value
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L17

	MOV edx, OFFSET msg8
	CALL WriteString
	CALL Crlf

	JMP	 L15

L17:
	MOV edx, OFFSET msg9
	CALL WriteString
	CALL Crlf

L15:
	MOV edx, OFFSET msg10
	CALL WriteString
	CALL Crlf

L18:	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var2_num2
	MOVSX ebx, var1_num1
	PUSH ebx
	PUSH eax
	mGreaterThanEqualToBool
	POP eax
	MOV	 ah, var4_isTrue
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L21

	MOV edx, OFFSET msg11
	CALL WriteString
	CALL Crlf

	JMP	 L19

L21:
	MOV edx, OFFSET msg12
	CALL WriteString
	CALL Crlf

L19:
	MOV edx, OFFSET msg13
	CALL WriteString
	CALL Crlf

L22:	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var2_num2
	MOVSX ebx, var3_num3
	PUSH ebx
	PUSH eax
	mGreaterThanEqualToBool
	POP eax
	MOV	 ah, var4_isTrue
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L25

	MOV edx, OFFSET msg14
	CALL WriteString
	CALL Crlf

	JMP	 L23

L25:
	MOV edx, OFFSET msg15
	CALL WriteString
	CALL Crlf

L23:
	MOV edx, OFFSET msg16
	CALL WriteString
	CALL Crlf

L26:	MOV	 al, var4_isTrue
	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var3_num3
	MOVSX ebx, var1_num1
	PUSH ebx
	PUSH eax
	mLessThanEqualToBool
	POP eax
	AND	 al, al
	CMP	 al, 0FFh		; Check if true
	JNE	 L29

	MOV edx, OFFSET msg17
	CALL WriteString
	CALL Crlf

	JMP	 L27

L29:
	MOV edx, OFFSET msg18
	CALL WriteString
	CALL Crlf

L27:
	MOV edx, OFFSET msg19
	CALL WriteString
	CALL Crlf

L30:	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var2_num2
	MOVSX ebx, var3_num3
	PUSH ebx
	PUSH eax
	mLessThanEqualToBool
	POP eax
	MOV	 ah, var4_isTrue
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L33

	MOV edx, OFFSET msg20
	CALL WriteString
	CALL Crlf

	JMP	 L31

L33:
	MOV edx, OFFSET msg21
	CALL WriteString
	CALL Crlf

L31:
	MOV edx, OFFSET msg22
	CALL WriteString
	CALL Crlf

L34:	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var2_num2
	MOVSX ebx, var3_num3
	PUSH ebx
	PUSH eax
	mEqualToBool
	POP eax
	MOV	 ah, 0h		; Load a boolean value
	NOT	 ah	; execute a bitwise not on the register
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L37

	MOV edx, OFFSET msg23
	CALL WriteString
	CALL Crlf

	JMP	 L35

L37:
	MOV edx, OFFSET msg24
	CALL WriteString
	CALL Crlf

L35:
	MOV edx, OFFSET msg25
	CALL WriteString
	CALL Crlf

L38:	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var2_num2
	MOVSX ebx, var1_num1
	PUSH ebx
	PUSH eax
	mEqualToBool
	POP eax
	MOV	 ah, 0h		; Load a boolean value
	NOT	 ah	; execute a bitwise not on the register
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L41

	MOV edx, OFFSET msg26
	CALL WriteString
	CALL Crlf

	JMP	 L39

L41:
	MOV edx, OFFSET msg27
	CALL WriteString
	CALL Crlf

L39:
	MOV edx, OFFSET msg28
	CALL WriteString
	CALL Crlf

L42:	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var2_num2
	MOVSX ebx, var1_num1
	PUSH ebx
	PUSH eax
	mNotEqualToBool
	POP eax
	MOV	 ah, 0h		; Load a boolean value
	NOT	 ah	; execute a bitwise not on the register
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L45

	MOV edx, OFFSET msg29
	CALL WriteString
	CALL Crlf

	JMP	 L43

L45:
	MOV edx, OFFSET msg30
	CALL WriteString
	CALL Crlf

L43:
	MOV edx, OFFSET msg31
	CALL WriteString
	CALL Crlf

L46:	MOV eax, 00000000h
	MOV ebx, 00000000h
	MOVSX eax, var2_num2
	MOVSX ebx, var3_num3
	PUSH ebx
	PUSH eax
	mNotEqualToBool
	POP eax
	MOV	 ah, 0h		; Load a boolean value
	NOT	 ah	; execute a bitwise not on the register
	AND	 al, ah
	CMP	 al, 0FFh		; Check if true
	JNE	 L48

	MOV edx, OFFSET msg32
	CALL WriteString
	CALL Crlf

	JMP	 L2

L48:
	MOV edx, OFFSET msg33
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main