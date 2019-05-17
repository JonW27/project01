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
	public int assemble(String f_name, String o_name, StringBuilder error){
		int retVal = 0;

		try(Scanner sc = new Scanner(new File(f_name))){
			ArrayList<String> f_contents = new ArrayList<String>();
			boolean blankLine = false;
			int firstBlankLine = 1;
			int line_number = 0; // we will not zero index our lines apparently

			// im going to presume that since readingCode is private, we need to construe our own.
			boolean readingCode = false;

			while(sc.hasNext()){
				String line = sc.nextLine();
				line_number++;
				if(!blankLine && line.trim().length() == 0){
					blankLine = true;
					firstBlankLine = line_number;
				}
				else if(blankLine && line.length() > 0){
					error.append("Your pasm file indicates EOF termination at " + firstBlankLine + " , but the file stream does not end.");
					retVal = line_number+1;
					blankLine = false;
				}
				if(line.charAt(0) == ' ' || line.charAt(0) == '\t'){
					error.append("There is whitespace at the beginning of a non-empty line.");
					retVal = line_number+1;
				}
				if(line.trim().toUpperCase().equals("DATA") && readingCode){
					if(!line.trim().equals("DATA")){
						error.append(f_name + ":" + line_number + " The program line DATA must be in uppercase.");
						retVal = line_number+1;
					} else if(line.trim().equals("DATA") && !readingCode){
						error.append(f_name + ":" + line_number + " Found DATA, but pasm file was not readng code.");
						retVal = line_number+1;
					}
					readingCode = false;
				}
				if(readingCode){
					String[] parts = line.trim().split("\\s+");
					if(!OPCODES.keySet().contains(parts[0].toUpperCase())){
						error.append(f_name + ":" + line_number + " Illegal mnemonic.");
						retVal = line_number+1;
					}
					else if(!parts[0].equals(parts[0].toUpperCase())){
						error.append(f_name + ":" + line_number + " Mnemonic must be uppercase.");
						retVal = line_number+1;
					}
					else if(Instruction.NO_ARG_MNEMONICS.contains(parts[0])) {
						if( parts.length != 1){
							error.append(f_name + ":" + line_number + " " + parts[0] + "takes no arguments");
							retVal = line_number+1;
						}
					} 
					else  { 
						// if( !Instruction.NO_ARG_MNEMONICS.contains( parts[ 0 ] )) {
						if(parts.length != 2){
							error.append(f_name + ":" + line_number + " " + parts[0] + " has an invalid length of arguments. Should be 2.");
							retVal = line_number+1;
						} else{
							if(parts[1].toUpperCase().startsWith("M")) {
								if(!Instruction.IMM_MNEMONICS.contains(parts[0])) {
									error.append(f_name + ":" + line_number + " " + parts[0] + " IMMEDIATEf arguments. Should be 2.");
									retVal = line_number+1;
								}
								parts[1] = parts[1].substring(1);
							} // then N and J
							if(parts[1].toUpperCase().startsWith("N")) {
								if(!Instruction.IMM_MNEMONICS.contains(parts[0])) {
									error.append(f_name + ":" + line_number + " " + parts[0] + " IMMEDIATEf arguments. Should be 2.");
									retVal = line_number+1;
								}
								parts[1] = parts[1].substring(1);
							}
							if(parts[1].toUpperCase().startsWith("J")) {
								if(!Instruction.IMM_MNEMONICS.contains(parts[0])) {
									error.append(f_name + ":" + line_number + " " + parts[0] + " IMMEDIATEf arguments. Should be 2.");
									retVal = line_number+1;
								}
								parts[1] = parts[1].substring(1);
							}
							try{
								int arg = Integer.parseInt(parts[1], 16);
							} catch(NumberFormatException e){
								error.append(f_name + ":" + line_number + " argument is not a hex number");
								retVal = line_number;
							}
						}
					} 
				} else { // reading data
					String[] parts = line.trim().split("\\s+");
					// give data format error in parts.length !=2
					try{
						int address = Integer.parseInt(parts[0], 16);
					} catch(NumberFormatException e){
						error.append(f_name + ":" + line_number + " data has non-numeric memory address");
						retVal = line_number;
					}
					try{
						int value = Integer.parseInt(parts[1], 16);
					} catch(NumberFormatException e){
						error.append("\n Error on line " + line_number + " : data has non-numeric memory address");
						retVal = line_number;
					}
					
				}

			}
		} catch(FileNotFoundException e){
			error.append("\nError: Unable to write the assembled program to the output file");
			retVal = -1;
		} catch(IOException e){
			error.append("\nUnexplained IO Exception");
			retVal = -1;
		} catch(Exception e){
			error.append("Could not read .pasm file");
		}

		if(retVal != 0){

			return new SimpleAssembler().assemble(f_name, f_name, error);
		}
		
		if(error.length() == 0){
			try{
				int i = new SimpleAssembler().assemble(f_name + ".pasm", f_name + ".pexe", error);
			} catch(Exception e){
			    System.out.println(error);
			}
		}
		else{
			System.out.println(error);
		}
		return retVal;
	}	
}
