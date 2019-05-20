package project;
// this requires SimpleAssembler to be tested and fully working

import static project.Instruction.OPCODES;

import java.io.File;
import java.io.FileNotFoundException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FullAssembler implements Assembler{
	// calls SimpleAssembler's assemble
	// supposed to take several days
	/**
	 * Method to assemble a file to its executable representation. 
	 * If the input has errors one or more of the errors will be reported 
	 * the StringBulder. The errors may not be the first error in 
	 * the code and will depend on the order in which instructions 
	 * are checked. There is no attempt to report all the errors.
	 * The line number of the last error that is reported 
	 * is returned as the value of the method. 
	 * A return value of 0 indicates that the code had no errors 
	 * and an output file was produced and saved. If the input or 
	 * output cannot be opened, the return value is -1.
	 * The unchecked exception IllegalArgumentException is thrown 
	 * if the error parameter is null, since it would not be 
	 * possible to provide error information about the source code.
	 * @param inputFileName the source assembly language file name
	 * @param outputFileName the file name of the executable version  
	 * of the program if the source program is correctly formatted
	 * @param error the StringBuilder to store the description 
	 * of the error or errors reported. It will be empty (length 
	 * zero) if no error is found.
	 * @return 0 if the source code is correct and the executable 
	 * is saved, -1 if the input or output files cannot be opened, 
	 * otherwise the line number of a reported error.
	 */
	@Override
	public int assemble(String inputFileName, String outputFileName, StringBuilder error) {
		if(error == null) 
			throw new IllegalArgumentException("Coding error: the error buffer is null");
	
		List<String> code = new ArrayList<>();
		List<String> data = new ArrayList<>();
		int retVal = 0;
		int lineNumber = 0;
 
		try (Scanner src = new Scanner(new File(inputFileName))) {
			
			boolean blankLineFound = false; 
			int firstBlankLineNumber = 0; 
			boolean readingCode = true; 
		
			while(src.hasNextLine()) {
				lineNumber++;
				String line = src.nextLine();
				System.out.println(lineNumber + ": " + line); 
				if(line.trim().length() == 0) {
					if(!blankLineFound) {
						blankLineFound=true;
						firstBlankLineNumber=lineNumber;	
					}
				}

				else {
				if(blankLineFound) {
					error.append("Error on line " + (firstBlankLineNumber )+ ":Illegal blank line in the source file\n");
					retVal=firstBlankLineNumber;
					blankLineFound=false;
				} 
				if(line.charAt(0)==' ') {
					error.append("Error on line " + lineNumber + ":Line starts with illegal white space\n");
					retVal = lineNumber;
				}
				if(line.trim().toUpperCase().equals("DATA") ) {
					if(!line.trim().equals("DATA")) {
						error.append("Error on line " + lineNumber + ": Line does not have DATA in upper case\n");
						retVal = lineNumber;
				}
				if(readingCode) {
					readingCode = false;
				}
				else{
					error.append("Error on line " + lineNumber+ ": Duplicate DATA delimiter at the current line \n");
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
	lineNumber = 0;
	
	for(String line : code) {
		lineNumber++;
		if(line.length() == 0) continue;
		
		String[] parts = line.split("\\s+");
			
		if(!Instruction.OPCODES.containsKey(parts[0].toUpperCase())) {
			error.append("Error on line " + lineNumber + ": illegal mnemonic\n");
			retVal = lineNumber;				
			}

			else{

				if(parts[0].compareTo(parts[0].toUpperCase())!=0) {
					error.append("Error on line " + lineNumber + ": mnemonic must be upper case\n");
					retVal = lineNumber;				
					parts[0]=parts[0].toUpperCase();
				}
				
				if(Instruction.NO_ARG_MNEMONICS.contains(parts[0].toUpperCase())) {
					
					if(parts.length > 1) {
						error.append("Error on line " + lineNumber + ": mnemonic cannot take arguments \n");
						retVal = lineNumber;
					}
				}
				
			else {
				if(parts.length > 2) {
					error.append("Error on line " + lineNumber + ": there are too many arguments present \n");
					retVal = lineNumber;
				}
				
				else if(parts.length<2) {
					error.append("Error on line " + lineNumber + ": the mnemonic is missing an argument \n");
					retVal = lineNumber;
				}

				else {
						
					if(parts[1].charAt(0) == 'M') {
						if(!Instruction.IMM_MNEMONICS.contains(parts[0].toUpperCase())) {
							error.append("Error on line " + lineNumber + ": this mnemonic does not allow immediate mode\n");
							retVal = lineNumber;
						}
						parts[1] = parts[1].substring(1);
					}
					
					else if(parts[1].charAt(0) =='N') {
						if(!Instruction.IND_MNEMONICS.contains(parts[0].toUpperCase())) {
							error.append("Error on line " + lineNumber + ": this mnemonic does not allow indirect mode\n");
							retVal = lineNumber;
						}
						parts[1] = parts[1].substring(1);
						
					}
						
					else if (parts[1].charAt(0) =='J') {
						if(!Instruction.JMP_MNEMONICS.contains(parts[0].toUpperCase())) {
							error.append("Error on line " + lineNumber + ": this mnemonic does not allow direct mode\n");
							retVal = lineNumber;
						}
						parts[1] = parts[1].substring(1);

					}
						
					try {
						Integer.parseInt(parts[1],16); // test arg is in hex, base 16
					}
					catch(NumberFormatException e) {
						error.append("Error on line " + lineNumber + ": argument is not a hex number\n");
						retVal = lineNumber;				
					}
				}
			}
		}
	}

		for(String line : data) {
			lineNumber++;
			if(line.length() == 0 || line.toUpperCase().trim().equals("DATA")) continue;
			
			String[] parts = line.split("\\s+");
			
			if(parts.length < 2) {
				error.append("Error on line " + lineNumber + ": data entry has too few numbers\n");
				retVal = lineNumber;	
								
			} 
			
			else if(parts.length > 2) {
				error.append("Error on line " + lineNumber + ": data entry has too many numbers\n");
				retVal = lineNumber;
				
			}
			else {
				try {
					 Integer.parseInt(parts[0],16);
				} catch(NumberFormatException e) {
					error.append("Error on line " + lineNumber + ": data has non-numeric memory address\n");
					retVal =lineNumber;				
				}
				try {
					 Integer.parseInt(parts[1],16);
			} catch(NumberFormatException e) {
				error.append("Error on line " + lineNumber + ": data has non-numeric memory value \n");
				retVal = lineNumber;				
			}
		}
	}
		
		if(retVal == 0) {
			new SimpleAssembler().assemble(inputFileName, outputFileName, error);
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
