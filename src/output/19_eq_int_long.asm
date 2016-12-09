INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var1_a	SWORD	?
var2_b	SWORD	?
var3_c	SWORD	?
var4_l1	SDWORD	?
var5_l2	SDWORD	?
var6_l3	SDWORD	?
msg1	 BYTE 'a is not equal to b', 0 
msg2	 BYTE 'a is equal to c', 0 
msg3	 BYTE 'This should not be printing!', 0 
msg4	 BYTE 'l1 = l3', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ax, 5		; Load an immediate value into the register
	MOV	 var1_a, ax

L3:
	MOV	 ax, 6		; Load an immediate value into the register
	MOV	 var2_b, ax

L4:
	MOV	 ax, 5		; Load an immediate value into the register
	MOV	 var3_c, ax

L5:
	MOV	 eax, 10000		; Load an immediate value into the register
	MOV	 var4_l1, eax

L6:
	MOV	 eax, 20200202		; Load an immediate value into the register
	MOV	 var5_l2, eax

L7:
	MOV	 eax, 10000		; Load an immediate value into the register
	MOV	 var6_l3, eax

L8:	MOV	 ax, var1_a
	MOV	 bx, var2_b
	CMP	 ax, bx
	JE	 L9

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L9:	MOV	 ax, var1_a
	MOV	 bx, var3_c
	CMP	 ax, bx
	JNE	 L11

	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L11:	MOV	 eax, var4_l1
	MOV	 ebx, var5_l2
	CMP	 eax, ebx
	JNE	 L13

	MOV edx, OFFSET msg3
	CALL WriteString
	CALL Crlf

L13:	MOV	 eax, var4_l1
	MOV	 ebx, var6_l3
	CMP	 eax, ebx
	JNE	 L2

	MOV edx, OFFSET msg4
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main