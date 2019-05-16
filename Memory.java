package project;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Memory{
    public static final int DATA_SIZE = 512;
    public static final int CODE_SIZE = 256;
    
    private int[] data = new int[DATA_SIZE];
    private List<Instruction> code = new ArrayList<>();
    private int changedDataIndex = -1;

    int[] getData(int min, int max){
	if(min < 0 || max > DATA_SIZE - 1){
	    throw new DataAccessException("Array index is out of limits");
	}
	return Arrays.copyOfRange(data, min, max);
    }

    int[] getData(){
	return data;
    }

    int getData(int index){
	isValidIndex(index);
	return data[index];
    }

    void setData(int index, int value){
	isValidIndex(index);
	data[index] = value;
	changedDataIndex = index;
    }

    int getChangedDataIndex(){
	return changedDataIndex;
    }

    List<Instruction> getCode(){
	return code;
    }

    Instruction getCode(int index){
	isValidCodeIndex(index);
	return code.get(index);
    }

    public Instruction[] getCode(int min, int max){
	// isValidCodeIndex(index); // index is not a local var
	if( 0 > min || min > max || max >= code.size() ) {
		throw new CodeAccessException( "Illegal access to code." ) ;
	}
    Instruction[] temp = {};
	temp = code.toArray(temp);
	return Arrays.copyOfRange(temp, min, max);
    }

    void addCode(Instruction value){
	if(code.size() >= CODE_SIZE){
	    return;
	} else{
	    code.add(value);
	}
    }

    void setCode(int index, Instruction instr){
	isValidIndex(index);
	code.set(index, instr);
    }

    void clearCode(){
	code.clear();
    }

    int getProgramSize(){
	return code.size();
    }

    void clearData(){
	data = new int[DATA_SIZE];
	changedDataIndex = -1;
    }

    void isValidIndex(int index){
	if(index < 0 || index > DATA_SIZE - 1){
	    throw new DataAccessException("Array index is out of limits");
	}
    }

    void isValidCodeIndex(int index){
	if(0 > index || code.size() <= index){
	    throw new CodeAccessException("Illegal access to code.");
	}
    }
}	    

    
