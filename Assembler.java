package project;

public interface Assembler {
	
	Set<String> noArgument = Set.of("Halt", "Nop", "Not");

	class DataPair {
		protected int address ;
		protected int value ;
		
		public DataPair( int address, int value ) {
			this.address = address; 
			this.value = value ;
		}
		
		public String toString() {
			return "DataPair (" + address + ", " + value + ")" ;
		}
	}
	
	abstract int assemble( String inputFileName, String outputFileName, StringBuilder error  ) ;
	
}
