;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Print the string value corresponding to Natural boolean values
;; Input of 1 --> True
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
	CMP eax, 1
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
