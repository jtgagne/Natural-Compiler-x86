INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var1_num1	SWORD	?
var2_num2	SWORD	?
var4_l2	SDWORD	?
var3_l1	SDWORD	?
msg1	 BYTE '6 >= 5', 0 
msg2	 BYTE 'This should not be printing!!!', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ax, 6		; Load an immediate value into the register
	MOV	 var1_num1, ax

L3:
	MOV	 ax, 5		; Load an immediate value into the register
	MOV	 var2_num2, ax

L4:
	MOV	 eax, 100203		; Load an immediate value into the register
	MOV	 var3_l1, eax

L5:
	MOV	 eax, 10202		; Load an immediate value into the register
	MOV	 var4_l2, eax

L6:	MOV	 ax, var1_num1
	MOV	 bx, var2_num2
	CMP	 ax, bx
	JNGE	 L7

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L7:	MOV	 eax, var4_l2
	MOV	 ebx, var3_l1
	CMP	 eax, ebx
	JNGE	 L2

	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main