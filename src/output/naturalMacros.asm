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


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; This macro makes a less than comparison and returns
;; a boolean value to the stack
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
mLessThanToBool MACRO

LOCAL L1
LOCAL L2
.data

.code
	POP eax
	POP ebx
	CMP eax, ebx
	JNL L1
	MOV al, 0FFh
	JMP L2
L1:
	MOV al, 0h
L2:
	PUSH eax
ENDM

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; This macro makes a less than comparison and returns
;; a boolean value to the stack
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
mGreaterThanToBool MACRO

LOCAL L3
LOCAL L4
.data

.code
	POP eax
	POP ebx
	CMP eax, ebx
	JNG L3
	MOV al, 0FFh
	JMP L4
L3:
	MOV al, 0h
L4:
	PUSH eax
ENDM

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; This macro makes a less than comparison and returns
;; a boolean value to the stack
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
mGreaterThanEqualToBool MACRO

LOCAL L5
LOCAL L6
.data

.code
	POP eax
	POP ebx
	CMP eax, ebx
	JNGE L5
	MOV al, 0FFh
	JMP L6
L5:
	MOV al, 0h
L6:
	PUSH eax
ENDM

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; This macro makes a less than comparison and returns
;; a boolean value to the stack
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
mLessThanEqualToBool MACRO

LOCAL L7
LOCAL L8
.data

.code
	POP eax
	POP ebx
	CMP eax, ebx
	JNLE L7
	MOV al, 0FFh
	JMP L8
L7:
	MOV al, 0h
L8:
	PUSH eax
ENDM

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; This macro makes a less than comparison and returns
;; a boolean value to the stack
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
mEqualToBool MACRO

LOCAL L9
LOCAL L10
.data

.code
	POP eax
	POP ebx
	CMP eax, ebx
	JNE L9
	MOV al, 0FFh
	JMP L10
L9:
	MOV al, 0h
L10:
	PUSH eax
ENDM

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; This macro makes a less than comparison and returns
;; a boolean value to the stack
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
mNotEqualToBool MACRO

LOCAL L11
LOCAL L12
.data

.code
	POP eax
	POP ebx
	CMP eax, ebx
	JE L11
	MOV al, 0FFh
	JMP L12
L11:
	MOV al, 0h
L12:
	PUSH eax
ENDM

