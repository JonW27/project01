
package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Memory {

	public static final int DATA_SIZE = 512;
	private int[]  data = new int[DATA_SIZE];
	public static final int CODE_SIZE =256;
	public List<Instruction> code = new ArrayList<>();
	public int changedDataIndex =-1;
	
	int[] getData(int min, int max) {
		if(min < 0 || min > DATA_SIZE - 1 || max < 0 || max > DATA_SIZE - 1) throw new DataAccessException("Index given is out of the range of the limits");
		return Arrays.copyOfRange(data, min, max);
	}
	
	int[] getData() {
		return data;
	}
	
	int getData(int index) {
		if(index < 0 || index > DATA_SIZE - 1) throw new DataAccessException("Index given is out of the range of the limits");
		return data[index];
	}
	
	void setData(int index, int value) {
		if(index < 0 || index > DATA_SIZE - 1) throw new DataAccessException("Index given is out of the range of the limits");
		data[index] = value;
		changedDataIndex = index;
	}
	
	void setChangedDataIndex(int changedDataIndex) {
		this.changedDataIndex = changedDataIndex;
	}
	
	void clearData() {
		for(int i = 0; i < data.length; i++) {
			data[i] = 0;
		}
		changedDataIndex = -1;
	}
	
	List<Instruction> getCode(){
		return code;
	}
	
	Instruction getCode(int index) {
		if(!(0 <= index ||index < code.size())) throw new CodeAccessException("Illegal access to code");
		return code.get(index);
	}
	
	public Instruction[] getCode(int min, int max) {
		// throw CodeAccessException if it is NOT true 
		// that 0 <= min <= max < code.size()
		if(!(0 <= min || min <= max || max < code.size())) throw new CodeAccessException("Illegal access to code");
			Instruction[] temp = {};
			temp = code.toArray(temp);
			return Arrays.copyOfRange(temp, min, max); 
		}
	
	void addCode(Instruction value) {
		if(code.size() < CODE_SIZE) {
			code.add(value);
		}
	}
	
	void setCode(int index, Instruction instr) {
		if(!(0 <= index || index < code.size())) throw new CodeAccessException("Illegal access to code");
		code.set(index, instr);
	}
	
	void clearCode() {
		code.clear();
	}
	
	int getProgramSize() {
		return code.size();
	}

	int getChangedDataIndex() {
		return changedDataIndex;
	}


}
