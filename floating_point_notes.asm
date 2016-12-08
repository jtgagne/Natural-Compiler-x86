There are 8 floating point registers on the FPU available in 32-bit mode

ST(0) -> ST(7)

The Top of the stack will always be point to ST(0).

if 1.0 is pushed, ST(0) --> 1.
if 2.0 is then pushed, the stack is shifted and ST(0) --> 2.0 and ST(1) --> 1.0

To indicate what is a floating point instruction, the keywords start with f.

Loading / PUSH to the stack:
	FBLD operates on BCD numbers 
	FILD pushes an integer to the FPU stack
	FLD  pushes a real number from memory to the FPU stack.

FLD Notes: 
	- No immediate operands. This means FLD 0.0 would be invalid.
	- Any constant values must be stored as a constant and then it can be loaded into the stack. 
	- Cannot use any GPRs as operands. 
		- FSTSW stores the FPU status in AX 
	- Memory to memory operations are not permitted
	- FLD = Float Load 
	- Can take the following as operands 
		- mem32fp
		- mem64fp
		- mem80fp
		- ST(i)

FILD Notes:
	- Integer operands MUST be loaded in by memory
		- automatically converted into floating point in the stack
	- When popping values, any floating point value being stored into an integer memory will be rounded

FINIT Initialization of the FPU:
	- Sets the FPU control word to 037h.
		- Hides all floating point exceptions.
		- Sets rounding to the nearest even.
		- Sets calculation precision to 64 bits
	- Should be called AT THE BEGINNING OF THE PROGRAM


Floating Point Data Types:
	- QWORD --> 64 bit int 
	- TBYTE --> 80 bit int 
	- Real4 --> 32 bit real 
	- Real8 --> 64 bit real 
	- Real10 --> 80 bit real 


example:

.data 
bigVal REAL 10 1.23232323232322422333E+864
.code 
fld bigVal	; load the variable into the stack




Load Commands: 
	FLD1 --> push 1.0 to the stack
	FLDL2T --> push log base 2(10) to the stack (3.3219280948873626).
	FLDL2E --> Push the value of log base 2(e) to the stack.
	FLDPI --> Push the value of PI to the stack 
	FLDLG2 --> Push the value of log10(2) to the register stack 
	FLDLN2 --> Push the value of ln(2) to the register stack 
	FLDZ --> Pusg 0.0 to the FPU stack

FST Storing floating point numbers:
	Use the following commands to copy values off the FPU stack and store the value in memory: 
		FST mem32fp
		FST mem64fp
		FST mem80fp
		FST ST(i)
	Values are always copied from ST(0)!!!!!

FSTP Pops the values from ST(0) and accepts the same operands as FST.

consider: 
	ST(0) = 10.1 and ST(1) = 234.56
	FST dblThree 		; 10.1
	FST dblFour			; 10.1
	FSTP dblThree		; 10.1, ST(0) = 234.56
	FSTP dblFour		; 234.56 ST(0) is null. 

Floating point arithmetic:
	FCHS change sign 
	FADD Add source to destination. 
	FSUB Subtract source from destination
	FSUBR subtract destination from source 
	FMUL multiply source by destination
	FDIV divide destination by source
	FDIVR divide source by destination.

FADD (no operands): Adds ST(0) + ST(1)
	- Result is stored in ST(0).

FADD (single operand): ST(0) + OP
FADD ST(0), ST(i)
FADD ST(i), ST(0)

FSUB (no operands): Subtracts ST(0) from ST(1)


















