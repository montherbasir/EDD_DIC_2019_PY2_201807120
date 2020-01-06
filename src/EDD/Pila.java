package EDD;

public class Pila<T> {
    private Lista<T> lista;

    public int getSize() {
        return this.lista.getSize();
    }

    public Pila() {
        this.lista = new Lista<T>();
    }

    public void push(T dato) {
        this.lista.add_first(dato);
    }

    public T pop() {
        T dato = this.lista.get_element_at(0);
        this.lista.remove_at(0);
        return dato;
    }

    public T peek() {
        return this.lista.get_element_at(0);
    }
}
