INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var1_a	BYTE	?
var3_c	BYTE	?
var2_b	BYTE	?
var4_c2	BYTE	?
msg1	 BYTE 'a < c', 0 
msg2	 BYTE 'c > b', 0 
msg3	 BYTE 'c == c', 0 
msg4	 BYTE 'a is not equal to c', 0 
msg5	 BYTE 'ERROR: this should not be printed!', 0 
msg6	 BYTE 'a <= c', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO


.code

	main PROC

L1:
	MOV	 ah, 'a'		
	MOV	 var1_a, ah

L3:
	MOV	 ah, 'b'		
	MOV	 var2_b, ah

L4:
	MOV	 ah, 'c'		
	MOV	 var3_c, ah

L5:
	MOV	 ah, 'c'		
	MOV	 var4_c2, ah

L6:	MOVSX	 ax, var1_a
	MOVSX	 bx, var3_c
	CMP	 ax, bx
	JNL	 L7

	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L7:	MOVSX	 ax, var3_c
	MOVSX	 bx, var2_b
	CMP	 ax, bx
	JNG	 L9

	MOV edx, OFFSET msg2
	CALL WriteString
	CALL Crlf

L9:	MOVSX	 ax, var3_c
	MOVSX	 bx, var4_c2
	CMP	 ax, bx
	JNE	 L11

	MOV edx, OFFSET msg3
	CALL WriteString
	CALL Crlf

L11:	MOVSX	 ax, var1_a
	MOVSX	 bx, var3_c
	CMP	 ax, bx
	JE	 L13

	MOV edx, OFFSET msg4
	CALL WriteString
	CALL Crlf

L13:	MOVSX	 ax, var1_a
	MOVSX	 bx, var2_b
	CMP	 ax, bx
	JNGE	 L15

	MOV edx, OFFSET msg5
	CALL WriteString
	CALL Crlf

L15:	MOVSX	 ax, var1_a
	MOVSX	 bx, var3_c
	CMP	 ax, bx
	JNLE	 L2

	MOV edx, OFFSET msg6
	CALL WriteString
	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main