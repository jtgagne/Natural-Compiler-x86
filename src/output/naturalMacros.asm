;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Print the string value corresponding to Natural boolean values
;; Input of 0FFh --> True
;; Otherwise --> False
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
mPrintBoolean MACRO value:REQ

LOCAL strTrue
LOCAL strFalse
LOCAL PrintFalse
LOCAL EndPrintFalse

.data
	strTrue BYTE 'True', 0
	strFalse BYTE 'False', 0

.code
	;; Push EAX and EDX to the stack in case they are in use
	PUSH eax
	PUSH edx

	MOVZX eax, value
	CMP eax, 0FFh
	JNE PrintFalse
	MOV edx, OFFSET strTrue
	CALL WriteString
	JMP EndPrintFalse

PrintFalse:
	MOV edx, OFFSET strFalse
	CALL WriteString

EndPrintFalse:
	POP edx
	POP eax

ENDM


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Print a value of a floating point variable
;; Input: a real variable  
;; Output: value to console
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
mPrintFloat MACRO value:REQ

LOCAL printValue
.data
	printValue REAL4 value

.code
	FLD printValue	; Load the variable into ST(0)
	CALL WriteFloat
	FSTP printValue	; Remove the value from FPU stack

ENDM
