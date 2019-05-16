package project;

public class IllegalInstructionException extends RuntimeException{
	public IllegalInstructionException() {
		super() ;
	}
	
	public IllegalInstructionException( String arg) {
		super( arg ) ;
	}
}
