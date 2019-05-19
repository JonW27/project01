package project;
// this requires SimpleAssembler to be tested and fully working

import static project.Instruction.OPCODES;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FullAssembler implements Assembler{
	// calls SimpleAssembler's assemble
	// supposed to take several days
	public int assemble(String f_name, String o_name, StringBuilder error) {
		if(error == null) 
			throw new IllegalArgumentException("Coding error: the error buffer is null");
	
		List<String> code = new ArrayList<>();
		List<String> data = new ArrayList<>();
		
		int retVal = 0;
		int lineNum = 0;
 
		try (Scanner inp = new Scanner(new File(f_name))) {
			
			boolean blankLineFound = false; 
			int firstBlankLineNum = 0; 
			boolean readingCode = true; 
		
			while(inp.hasNextLine()) {
				lineNum++;
				String line = inp.nextLine();
				System.out.println(lineNum + ": " + line); 
				
				if(line.trim().length() == 0) {
					
					if(!blankLineFound) {
						blankLineFound=true;
						
						firstBlankLineNum=lineNum;
						
					}
				}

				else {
					
					if(blankLineFound) {
						error.append("Error on line " + (firstBlankLineNum )+ ":Illegal blank line in the source file\n");
						retVal=firstBlankLineNum;
						blankLineFound=false;
					} 
					if(line.charAt(0)==' ') {
						error.append("Error on line " + lineNum + ":Line starts with illegal white space\n");
						retVal = lineNum;
					}
					if(line.trim().toUpperCase().equals("DATA") ) {
						if(!line.trim().equals("DATA")) {
							error.append("Error on line " + lineNum + ": Line does not have DATA in upper case\n");
							retVal = lineNum;
						}
						if(readingCode) {
							readingCode = false;
						}
						else{
							error.append("Error on line " + lineNum+ ": Duplicate DATA delimiter at the current line \n");
						}
					} 
				}
				if(readingCode) code.add(line.trim());
				else data.add(line.trim());

			} 
		} catch (FileNotFoundException e) {
			error.append("Unable to open the assembled file\n");
			retVal = -1;
		} 

		lineNum = 0;

		for(String line : code) {
			lineNum++;
			if(line.length() == 0) continue;

			String[] parts = line.split("\\s+");
			
			if(!Instruction.OPCODES.containsKey(parts[0].toUpperCase())) {
				error.append("Error on line " + lineNum + 
						": illegal mnemonic\n");
				retVal = lineNum;				
			}

			else{

				if(parts[0].compareTo(parts[0].toUpperCase())!=0) {
					error.append("Error on line " + lineNum + 
							": mnemonic must be upper case\n");
					retVal = lineNum;				
					parts[0]=parts[0].toUpperCase();
				}
				
				if(Instruction.NO_ARG_MNEMONICS.contains(parts[0].toUpperCase())) {
					if(parts.length > 1) {
						error.append("Error on line " + lineNum + 
								": mnemonic cannot take arguments \n");
						retVal = lineNum;
					}
				}
				
				else {
					if(parts.length > 2) {
						error.append("Error on line " + lineNum + 
								": there are too many arguments present \n");
						retVal = lineNum;
					}
					else if(parts.length<2) {
						error.append("Error on line " + lineNum + 
								": the mnemonic is missing an argument \n");
						retVal = lineNum;
					}

					else {
						
						if(parts[1].charAt(0) == 'M') {
							if(!Instruction.IMM_MNEMONICS.contains(parts[0].toUpperCase())) {
								error.append("Error on line " + lineNum + 
										": this mnemonic does not allow immediate mode\n");
								retVal = lineNum;
							}
							parts[1] = parts[1].substring(1);
						}
						else if(parts[1].charAt(0) =='N') {
							if(!Instruction.IND_MNEMONICS.contains(parts[0].toUpperCase())) {
								error.append("Error on line " + lineNum + 
										": this mnemonic does not allow indirect mode\n");
								retVal = lineNum;
							}
							parts[1] = parts[1].substring(1);
						
						} 
						else if (parts[1].charAt(0) =='J') {
							if(!Instruction.JMP_MNEMONICS.contains(parts[0].toUpperCase())) {
								error.append("Error on line " + lineNum + 
										": this mnemonic does not allow direct mode\n");
								retVal = lineNum;
							}
							parts[1] = parts[1].substring(1);

						}
						try {
							Integer.parseInt(parts[1],16); // test arg is in hex, base 16
						}
						catch(NumberFormatException e) {
							error.append("Error on line " + lineNum + 
									": argument is not a hex number\n");
							retVal = lineNum;				
						}
					}
				}
			}
		}

		for(String line : data) {
			lineNum++;
			if(line.length() == 0 || line.toUpperCase().trim().equals("DATA")) continue;

			String[] parts = line.split("\\s+");
			if(parts.length < 2) {
				error.append("Error on line " + lineNum + 
						": data entry has too few numbers\n");
				retVal = lineNum;	
								
			} 
			else if(parts.length > 2) {
				error.append("Error on line " + lineNum + 
						": data entry has too many numbers\n");
				retVal = lineNum;
				
			}
			else {
				try {
					 Integer.parseInt(parts[0],16);
				} catch(NumberFormatException e) {
					error.append("Error on line " + lineNum + 
						": data has non-numeric memory address\n");
					retVal =lineNum;				
				}
				try {
					 Integer.parseInt(parts[1],16);
				
			} catch(NumberFormatException e) {
				error.append("Error on line " + lineNum + 
					": data has non-numeric memory value \n");
				retVal = lineNum;				
			}

			}
		}
		
		if(retVal == 0) {
			new SimpleAssembler().assemble(f_name, o_name	, error);
		}
		
		else {
			System.out.println("\n" + error.toString());
		}

		return retVal;
	}
	
	public static void main(String[] args) {
		StringBuilder error = new StringBuilder();
        System.out.println("Enter the name of the file without extension: ");
        try (Scanner keyboard = new Scanner(System.in)) { 
            String filename = keyboard.nextLine();
            int i = new FullAssembler().assemble(filename + ".pasm", 
                    filename + ".pexe", error);
            System.out.println("result = " + i);
        }
    }
}
