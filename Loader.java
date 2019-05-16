package project ;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Loader{
    public static String load(Machine machine, File file) throws Exception{
    	if(machine == null || file == null){
		    return null;
		}
	
		int numInstr = 0;
		boolean flag = true;
	
		try(DataInputStream input = new DataInputStream(new FileInputStream(file))){
		    while(true){
				int x = input.readInt();
				if(flag && x == -1){
				    flag = false ;
				} else if(flag){
					numInstr += 1;
					machine.addCode(new Instruction((byte)x, input.readInt()));
				} else{
					machine.setData(x, input.readInt());
				}
		    }
		} 
		
		catch(EOFException e){
		    return "" + numInstr;
		} catch(FileNotFoundException e1){
		    return("File " + file.getName() + " Not Found");
		} catch (Exception e){
		    throw e;
		}
    }

    public static void main(String[] args) throws Exception {
		Machine machine = new Machine(() -> System.exit(0));
	
		String str = Loader.load(machine, new File("factorial.pexe"));
		System.out.println( str ) ;
		int total = Integer.parseInt(str);
	
		// should see 20 lines
		
		for(int i = 0; i < total; i++){
		    System.out.println(machine.getCode(i).getText());
		}
	
		System.out.println(Arrays.toString(machine.getData(0, 20)));
	
		// should see 169 lines
		
		machine.setPC(0);
		while(true){
		    System.out.println(machine.getCode(machine.getPC()).getText());
		    machine.step();
		    System.out.println(Arrays.toString(machine.getData(0, 20)));
		}
    }
}
	
