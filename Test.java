package project;

public class Test{
    public static void main(String[] args){
	// YOU SHOULD REALLY WATCH HALT AND CATCH FIRE
	// I'VE HEARD VERY GOOD THINGS ABOUT IT
	// VERY *VERY* GOOD THINGS

	Instruction i = new Instruction((byte)0b01100110,0);
	Instruction j = new Instruction((byte)0b11000110,0);
	Instruction k = new Instruction((byte)0b00000110,0);
	Instruction l = new Instruction((byte)0b01110110,0);

	System.out.println("Should print nothing (i)...");
	Instruction.checkParity(i);
	System.out.println("Should print nothing (j)...");
	Instruction.checkParity(j);
	System.out.println("Should print nothing (k)...");
	Instruction.checkParity(k);
	System.out.println("Should throw exception (l)...");
	Instruction.checkParity(l);
    }
}
