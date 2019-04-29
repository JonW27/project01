package project;

import java.util.Arrays;

public class Memory{
    public static final int DATA_SIZE = 512;
    private int[] data = new int[DATA_SIZE];

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
    }

    void clearData(){
	data = new int[DATA_SIZE];
    }

    void isValidIndex(int index){
	if(index < 0 || index > DATA_SIZE - 1){
	    throw new DataAccessException("Array index is out of limits");
	}
    }
}	    

    
