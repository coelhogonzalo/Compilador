package Parser;
import AnalizadorLexico.Error;
public class ErrorG extends Error{

	public ErrorG( String texto, int linea) {
		super("ERROR", texto, linea);
		System.out.println(this.toString());
	}
	public String toString(){
		return lexema+" en la linea: "+linea+"\r\n";
	}
}
