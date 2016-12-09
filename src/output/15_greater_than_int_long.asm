INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var1_num1	SWORD	?
var2_num2	SWORD	?
var3_l1	SDWORD	?
var4_l2	SDWORD	?
msg1	 BYTE 'There is an error, this should not be printing', 0 
msg2	 BYTE '20 > 10', 0 
msg3	 BYTE '1000 > 75', 0 
msg4	 BYTE 'There is an error with greater than and long types', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
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
	MOV	 eax, 1000		; Load an immediate value into the register
	MOV	 var3_l1, eax

L5:
	MOV	 eax, 75		; Load an immediate value into the register
	MOV	 var4_l2, eax

L6:	MOV	 ax, var1_num1
	MOV	 bx, var2_num2
	CMP	 ax, bx
	JNG	 L9

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

	JMP	 L7

L9:
	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L7:	MOV	 eax, var3_l1
	MOV	 ebx, var4_l2
	CMP	 eax, ebx
	JNG	 L11

	MOV edx, OFFSET msg3
	CALL WriteString
	CALL Crlf

	JMP	 L2

L11:
	MOV edx, OFFSET msg4
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main