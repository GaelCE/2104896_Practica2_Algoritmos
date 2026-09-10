package modelo;

public class Pila <T> {
    private T [] pila;
    private int tope=-1;

    public Pila(){
        pila=(T[])new Object[10];
    }

    public Pila(int capacidad){
        pila=(T[]) new Object[capacidad];
    }

    public void push(T objeto){
        if (llena()){
            System.out.println("DESBORDAMIENTO");
        }else {
            tope++;
            pila[tope]=objeto;
            System.out.println("Objeto ingresado");
        }
    }

    public T pull(){
        if (vacia()){
            System.out.println("PILA VACIA");
            return null;
        }else{
            T o=pila[tope];
            pila[tope]=null;
            tope--;
            return o;
        }
    }

    public boolean llena(){
        return tope==pila.length-1;
    }

    public boolean vacia(){
        return tope==-1;
    }

    public void clear(){
        while (tope!=-1){
            pila[tope]=null;
            tope--;
        }
        System.out.println("Pila vaciada");
    }

    public int getSize(){
        return tope+1;
    }

    public T verTope(){
        if (vacia()){
            System.out.println("PILA VACIA");
            return null;
        } else {
            return pila[tope];
        }
    }
}
