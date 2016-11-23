INCLUDE C:\masm32\include\masm32rt.inc
INCLUDE C:\masm32\include\Irvine32.inc
INCLUDELIB C:\masm32\lib\Irvine32.lib
INCLUDE C:\masm32\include\debug.inc
INCLUDELIB C:\masm32\lib\debug.lib



.data

var5_difference	SDWORD	?
var4_sum	SDWORD	?
var3_num3	SDWORD	?
var2_num2	SDWORD	?
var1_num1	SDWORD	?
msg1	 BYTE 'why is this not running debug mode?', 0 
msg2	 BYTE 'The first long variable: ', 0 
msg3	 BYTE 'The second long variable: ', 0 
msg4	 BYTE 'The third long variable: ', 0 
msg5	 BYTE 'The sum of the long variable: ', 0 
msg6	 BYTE 'num1 - num2 = ', 0 

; Function prototypes
WriteString PROTO
Crlf PROTO
WriteInt PROTO


.code

	main PROC

L1:
	MOV	 eax, 100000		; Load an immediate value into the register
	MOV	 var1_num1, eax

L3:
	MOV	 eax, 200000		; Load an immediate value into the register
	MOV	 var2_num2, eax

L4:
	MOV	 eax, 300000		; Load an immediate value into the register
	MOV	 var3_num3, eax

L5:
	MOV	 eax, var1_num1
	MOV	 ebx, var2_num2
	ADD	 eax, ebx		 ; add the two registers
	MOV	 ebx, var3_num3
	ADD	 eax, ebx		 ; add the two registers
	MOV	 var4_sum, eax

L6:
	MOV	 eax, var1_num1
	MOV	 ebx, var2_num2
	SUB	 eax, ebx		; subtract the two registers
	MOV	 var5_difference, eax

L7:
	MOV edx, OFFSET msg1
	CALL WriteString
	CALL Crlf
L8:
	MOV edx, OFFSET msg2
	CALL WriteString
 	MOV eax, var1_num1
	CALL WriteInt
 	CALL Crlf
L9:
	MOV edx, OFFSET msg3
	CALL WriteString
 	MOV eax, var2_num2
	CALL WriteInt
 	CALL Crlf
L10:
	MOV edx, OFFSET msg4
	CALL WriteString
 	MOV eax, var3_num3
	CALL WriteInt
 	CALL Crlf
L11:
	MOV edx, OFFSET msg5
	CALL WriteString
 	MOV eax, var4_sum
	CALL WriteInt
 	CALL Crlf
L12:
	MOV edx, OFFSET msg6
	CALL WriteString
 	MOV eax, var5_difference
	CALL WriteInt
 	CALL Crlf
L2:

	inkey
	INVOKE ExitProcess, 0
	main ENDP
END main

