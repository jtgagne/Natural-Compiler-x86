INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib
INCLUDE C:\masm32\naturalMacros\naturalMacros.asm


.data

var3_sum	SDWORD	?
var2_input2	SDWORD	?
var1_input1	SDWORD	?
msg1	 BYTE 'Enter two whole numbers to find the sum', 0 
msg2	 BYTE 'Number 1: ', 0 
msg3	 BYTE 'Number 2: ', 0 
msg4	 BYTE 'The sum is: ', 0 


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Function prototypes
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
WriteString PROTO
Crlf PROTO
ReadInt PROTO
WriteInt PROTO


.code

	main PROC

L1:
	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf

L3:	MOV edx, OFFSET msg2
	CALL WriteString
	CALL ReadInt
	MOV var1_input1, eax

L4:	MOV edx, OFFSET msg3
	CALL WriteString
	CALL ReadInt
	MOV var2_input2, eax

L5:
	MOV	 eax, var1_input1
	MOV	 ebx, var2_input2
	ADD	 eax, ebx		 ; add the two registers
	MOV	 var3_sum, eax

L6:
	MOV edx, OFFSET msg4
	CALL WriteString
 	MOV eax, var3_sum
	CALL WriteInt
 	CALL Crlf

L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP


END main