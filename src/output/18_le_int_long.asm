INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var1_num1	SWORD	?
var2_num2	SWORD	?
var3_num3	SWORD	?
var4_l1	SDWORD	?
var5_l2	SDWORD	?
var6_l3	SDWORD	?
msg1	 BYTE 'num1: ', 0 
msg2	 BYTE 'num2: ', 0 
msg3	 BYTE 'num1 <= num2', 0 
msg4	 BYTE 'num2: ', 0 
msg5	 BYTE 'num3: ', 0 
msg6	 BYTE 'num2 <= num3', 0 
msg7	 BYTE 'l1: ', 0 
msg8	 BYTE 'l2: ', 0 
msg9	 BYTE 'l1 <= l2', 0 
msg10	 BYTE 'l1: ', 0 
msg11	 BYTE 'l3: ', 0 
msg12	 BYTE 'l1 <= l3', 0 
msg13	 BYTE 'This should not be printing!!!', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
WriteInt PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ax, 5		; Load an immediate value into the register
	MOV	 var1_num1, ax

L3:
	MOV	 ax, 6		; Load an immediate value into the register
	MOV	 var2_num2, ax

L4:
	MOV	 ax, 6		; Load an immediate value into the register
	MOV	 var3_num3, ax

L5:
	MOV	 eax, 10233		; Load an immediate value into the register
	MOV	 var4_l1, eax

L6:
	MOV	 eax, 102020202		; Load an immediate value into the register
	MOV	 var5_l2, eax

L7:
	MOV	 eax, 10233		; Load an immediate value into the register
	MOV	 var6_l3, eax

L8:	MOV	 ax, var1_num1
	MOV	 bx, var2_num2
	CMP	 ax, bx
	JNLE	 L9

	MOV edx, OFFSET msg1
	CALL WriteString
 	MOVSX eax, var1_num1
	CALL WriteInt
 	CALL Crlf

L11:
	MOV edx, OFFSET msg2
	CALL WriteString
 	MOVSX eax, var2_num2
	CALL WriteInt
 	CALL Crlf

L12:
	MOV edx, OFFSET msg3
	CALL WriteString
	CALL Crlf

L9:	MOV	 ax, var2_num2
	MOV	 bx, var3_num3
	CMP	 ax, bx
	JNLE	 L13

	MOV edx, OFFSET msg4
	CALL WriteString
 	MOVSX eax, var2_num2
	CALL WriteInt
 	CALL Crlf

L15:
	MOV edx, OFFSET msg5
	CALL WriteString
 	MOVSX eax, var3_num3
	CALL WriteInt
 	CALL Crlf

L16:
	MOV edx, OFFSET msg6
	CALL WriteString
	CALL Crlf

L13:	MOV	 eax, var4_l1
	MOV	 ebx, var5_l2
	CMP	 eax, ebx
	JNLE	 L17

	MOV edx, OFFSET msg7
	CALL WriteString
 	PUSH eax
	MOV eax, var4_l1
	CALL WriteInt
	POP eax
 	CALL Crlf

L19:
	MOV edx, OFFSET msg8
	CALL WriteString
 	PUSH eax
	MOV eax, var5_l2
	CALL WriteInt
	POP eax
 	CALL Crlf

L20:
	MOV edx, OFFSET msg9
	CALL WriteString
	CALL Crlf

L17:	MOV	 eax, var4_l1
	MOV	 ebx, var6_l3
	CMP	 eax, ebx
	JNLE	 L21

	MOV edx, OFFSET msg10
	CALL WriteString
 	PUSH eax
	MOV eax, var4_l1
	CALL WriteInt
	POP eax
 	CALL Crlf

L23:
	MOV edx, OFFSET msg11
	CALL WriteString
 	PUSH eax
	MOV eax, var6_l3
	CALL WriteInt
	POP eax
 	CALL Crlf

L24:
	MOV edx, OFFSET msg12
	CALL WriteString
	CALL Crlf

L21:	MOV	 eax, var5_l2
	MOV	 ebx, var4_l1
	CMP	 eax, ebx
	JNLE	 L2

	MOV edx, OFFSET msg13
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main