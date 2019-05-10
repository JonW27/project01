// this requires SimpleAssembler to be tested and fully working

import java.util.ArrayList;
import java.io.EOFException;

public class FullAssembler implements Assembler{
    // calls SimpleAssembler's assemble
    // supposed to take several days
    public static void main(String[] args){
	StringBuilder error = new StringBuilder();
	System.out.println( "Enter the name of the file without extension: ") ;
	
	try(Scanner sc = new Scanner(System.in)){
	    String f_name = sc.nextLine();
	    ArrayList<String> f_contents = new ArrayList<String>();
	    boolean EOF = false;
	    int EOF_line = 1;
	    int line_number = 1; // we will not zero index our lines apparently

	    // im going to presume that since readingCode is private, we need to construe our own.
	    boolean readingCode = false;
	    
	    while(sc.hasNext()){
		String line = sc.nextLine();
		if(line.trim().length() == 0){
		    EOF = true;
		    EOF_line = line_number;
		}
		else if(EOF && line.length() > 0){
		    error.append("Your pasm file indicates EOF termination at " + EOF_line + " , but the file stream does not end.");
		}
		if(line[0] == ' ' || line[0] == '\t'){
		    error.append("There is whitespace at the beginning of a non-empty line.");
		}
		if(line.trim().toUpperCase().equals("DATA") && readingCode){
		    if(!line.trim().equals("DATA")){
			throw new RunTimeException("The program line DATA must be in uppercase.");
		    } else if(line.trim().equals("DATA") && !readingCode){
			error.append(f_name + ":" + line_number + " Found DATA, but pasm file was not readng code.");
		    }
		}
		if(readingCode){
		    parts = line.trim().split("\\s+");
		    if(!opcodes.keySet().contains(parts[0])){
			error.append(f_name + ":" + line_number + " Illegal mnemonic.");
			if(!parts[0].equals(parts[0].toUpperCase())){
			    error.append(f_name + ":" + line_number + " Mnemonic must be uppercase.");
			}
			if(noArgument.contains(parts[0]) && parts.length != 1){
			    error.append(f_name + ":" + line_number + " " + parts[0] + "takes no arguments");
			} else if(!noArgument.contains(parts[0])){
			    if(parts.length != 2){
				error.append(f_name + ":" + line_number + " " + parts[0] " has an invalid length of arguments. Should be 2.");
			    } else{
				try{
				    int arg = Integer.parseInt(parts[1], 16);
				} catch(NumberFormatException e){
				    error.append(f_name + ":" + line_number + " argument is not a hex number");
				    int retVal = line_number;
				}
				try{
				    int address = Integer.parseInt(parts[0], 16);
				} catch(NumberFormatException e){
				    error.append(f_name + ":" + line_number + " data has non-numeric memory address");
				    int retVal = line_number;
				}
				try{
				    int value = Integer.parseInt(parts[1], 16);
				} catch(NumberFormatException e){
				    error.append("\n Error on line " + (offset+i+1) + " : data has non-numeric memory address");
				    int retVal = line_number;
				}
			    }
			} 
			    
			    
		    }
		}

		
		    
		    
		f_contents.add(line);
		line_number++;
	    }
	}
	
}
