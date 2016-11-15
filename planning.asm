BYTE --> 1 bytes
WORD --> 2 bytes
DWORD --> 4 bytes
REAL4 --> 4 bytes

Supported:
	int 	--> SWORD
	long	--> SDWORD
	float	--> REAL4
	double 	--> REAL8
	char 	--> Byte
	boolean --> Byte 

int identifier
--> identifier SDWORD ?

int identifier is 5
--> identifier SDWORD 5


based on datatypes...

if an int (16-bit)
--> AX, BX, CX, DX, SI, DI, BP, SP.

long
--> EAX, EBX, ECX, EDX, ESI, EDI, EBP, ESP

is in use:
EAX contains AX. AX contains AH + AL
EBX contains BX. BX contains BH + BL
ECX contains CX. CX contains CH + CL
EDX contains DX. DX contains DH + DL
ESI contains SI
EDI contains DI
EBP contains BP
ESP contains SP

int a is 5
int b is 6
int sum is a + b

; data section
var_a	WORD 5
var_b	WORD 6
var_sum	WORD ?

MOV AX, var_a
MOV BX, var_b
ADD ax, bx 
MOV var_sum, ax

Its all about the end nodes. 

Constant --> refers to an end node.
	- Has a token type
	- Has a type
	- Has an assigned register

Tokens:
	Char 
	Num
	Phrase 
	Print 
	Real 
	Word
	Phrase 












