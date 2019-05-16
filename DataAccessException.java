package project;

public class DataAccessException extends RuntimeException{
	public DataAccessException( ) {
		super() ;
	}
	
	public DataAccessException( String arg ) {
		super( arg ) ;
	}
}
