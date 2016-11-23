INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var6_difference	SWORD	?
var5_addTwo	SWORD	?
var4_boolSum	SWORD	?
var3_isWorking	BYTE	?
var2_isFalse	BYTE	?
var1_isTrue	BYTE	?
msg1	 BYTE 'The value of isTrue: ', 0 
msg2	 BYTE 'The value of isFalse: ', 0 
msg3	 BYTE 'The sum of the boolean values is: ', 0 
msg4	 BYTE 'boolSum + 2 = ', 0 
msg5	 BYTE 'false - true - true = ', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO
WriteInt PROTO


.code

	main PROC

L1:
	MOV	 ah, 1		; Load a boolean value
	MOV	 var1_isTrue, ah

L3:
	MOV	 ah, 0		; Load a boolean value
	MOV	 var2_isFalse, ah

L4:
	MOV	 ah, 1		; Load a boolean value
	MOV	 var3_isWorking, ah

L5:
	MOV edx, OFFSET msg1
	CALL WriteString
 	mPrintBoolean <var1_isTrue>
 	CALL Crlf
L6:
	MOV edx, OFFSET msg2
	CALL WriteString
 	mPrintBoolean <var2_isFalse>
 	CALL Crlf
L7:
	MOVZX ax, var1_isTrue
	MOVZX bx, var2_isFalse
	ADD	 ax, bx		 ; add the two registers
	MOVZX bx, var3_isWorking
	ADD	 ax, bx		 ; add the two registers
	MOV	 var4_boolSum, ax

L8:
	MOV edx, OFFSET msg3
	CALL WriteString
 	MOVSX eax, var4_boolSum
	CALL WriteInt
 	CALL Crlf
L9:
	MOV	 ax, var4_boolSum
	MOV	 bx, 2		; Load an immediate value into the register
	ADD	 ax, bx		 ; add the two registers
	MOV	 var5_addTwo, ax

L10:
	MOV edx, OFFSET msg4
	CALL WriteString
 	MOVSX eax, var5_addTwo
	CALL WriteInt
 	CALL Crlf
L11:
	MOVZX ax, var2_isFalse
	MOVZX bx, var1_isTrue
	SUB	 ax, bx		; subtract the two registers
	MOVZX bx, var3_isWorking
	SUB	 ax, bx		; subtract the two registers
	MOV	 var6_difference, ax

L12:
	MOV edx, OFFSET msg5
	CALL WriteString
 	MOVSX eax, var6_difference
	CALL WriteInt
 	CALL Crlf
L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main