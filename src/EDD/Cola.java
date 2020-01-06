package EDD;

public class Cola<T> {
    int getSize() {
        return this.lista.getSize();
    }

    Cola() {
        this.lista = new Lista<T>();
    }

    private Lista<T> lista;

    public void queue(T dato) {
        this.lista.add_first(dato);
    }

    public T dequeue() {
        int x = this.lista.getSize() - 1;
        T dato = this.lista.get_element_at(x);
        this.lista.remove_at(x);
        return dato;
    }
}
