package project;

public class DivideByZeroException extends RuntimeException{
	public DivideByZeroException() {
		super() ;
	}
	
	public DivideByZeroException(String arg) {
		super( arg ) ;
	}
}
