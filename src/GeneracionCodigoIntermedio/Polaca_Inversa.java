package GeneracionCodigoIntermedio; //prueba

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import AnalizadorLexico.Token;

public class Polaca_Inversa {
    private ArrayList<StringBuilder> PI = null;
    private Stack<Integer> pila = null;
    private Stack<Integer> saltoIncond = null;
    private Stack<Integer> saltoFuncs = null;
    private HashMap<String, tamFuncion> funcs;
    private String nombreFunActual;

    public Polaca_Inversa() {
        PI = new ArrayList<>();
        pila = new Stack<>();
        saltoIncond = new Stack<>();
        funcs = new HashMap<>();

        nombreFunActual = null;
    }

    public void put(String l) {
        PI.add(new StringBuilder(l));
    }

    public void putAt(String l, int index) {
        PI.add(index, new StringBuilder(l));
    }

    public void setSaltoIncond(){
        saltoIncond.add(PI.size());
    }
    
    public void saltoIncond(){
    	PI.add(new StringBuilder("Label" + saltoIncond.pop()));
    	PI.add(new StringBuilder("BT"));
    }
    
    public void bifurcacion() {
        pila.add(PI.size());
        //System.out.println("La bifurcacion apilo "+PI.size());
        PI.add(new StringBuilder("WHERE"));
        PI.add(new StringBuilder("B"));
    }
    
    public void bifurcacionElse() {
        pila.add(PI.size());
        PI.add(new StringBuilder("WHERE"));
        PI.add(new StringBuilder("BT"));
    }
    
    public void inicioFuncion(String nombreFun) {
    	tamFuncion tf = new tamFuncion();
    	tf.setInicio(PI.size());
    	nombreFunActual = nombreFun;
    	funcs.put(nombreFun, tf);
        PI.add(new StringBuilder(nombreFun));
        PI.add(new StringBuilder("inicio_funcion"));
    }
    
    public void finFuncion() {
    	tamFuncion tf = funcs.get(nombreFunActual);
    	tf.setFin(PI.size());
    	PI.add(new StringBuilder("return"));
    }
    
    public void jumpToFun(String fun){
    	tamFuncion tf = funcs.get(fun);
    	if (tf != null){
            PI.add(new StringBuilder(fun)); //esto en assembler deberia de ser un CALL para usar un RET y ya tener la pos
            PI.add(new StringBuilder("CALL"));
        }
    	else {
            PI.add(new StringBuilder(fun)); //aca entra por si no esta definida la funcion
            PI.add(new StringBuilder("CALL"));
    	}
    }

   // public void apilar() {
   //     pila.add(PI.size());
   //     PI.add(new StringBuilder("WHERE"));
   // }

    public ArrayList<StringBuilder> getPI(){
        ArrayList<StringBuilder> PIOut = new ArrayList<>(PI);
        return PIOut;
    }

    public void desapilar() {
        int pop = pila.pop();
        StringBuilder v = PI.get(pop);
        //System.out.println("Desapile esto "+v.toString());
        v.setLength(0);
        v.append("Label" + PI.size());
        //PI.add(new StringBuilder("Label" + pop));
    }
    public void desapilarElse() {
        int pop = pila.pop();
        StringBuilder v = PI.get(pop);
        v.setLength(0);
        v.append("Label" + (PI.size() + 2));
        //PI.add(new StringBuilder("Label" + pop));
    }
    public void printContent(){//PARA DEBUG
    	System.out.println(this.PI);
    }

    public void paramFun(String param){
        PI.add(PI.size()-2, new StringBuilder(param));
    }
    /*public void apiloBranch(){
    	pila.add(PI.size());
    	PI.add(new Token("Branch", -1, "Branch"));
    }
    
    public void actualizoBranch(int dondeSalta){
    	PI.get(pila.pop()).nro = dondeSalta;
    }*/
    
    private class tamFuncion{
    	int inicio, fin;
    	public tamFuncion(){
    		inicio = 0;
    		fin = 0;
    	}
    	public void setInicio(int i){
    		inicio = i;
    	}
    	public void setFin(int f){
    		fin = f;
    	}
    	public int getInicio(){
    		return inicio;
    	}
    	public int getFin(){
    		return fin;
    	}
    }
}


