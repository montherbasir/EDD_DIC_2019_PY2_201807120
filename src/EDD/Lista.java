package EDD;

class Nodo<T> {
    private Nodo<T> next;
    private T dato;

    public Nodo(T x) {
        next = null;
        dato = x;
    }

    public Nodo<T> getNext() {
        return next;
    }

    public void setNext(Nodo<T> n) {
        next = n;
    }

    public T getDato() {
        return dato;
    }

}

public class Lista<T> {
    public int getSize() {
        return size;
    }

    private boolean isEmpty() {
        return size == 0;
    }

    private int size;
    private Nodo<T> first;
    private Nodo<T> last;

    public void add_first(T dato) {
        Nodo<T> n = new Nodo<T>(dato);
        if (this.isEmpty()) {
            this.first = n;
            this.last = n;
            this.size++;
        } else {
            n.setNext(this.first);
            this.first = n;
            this.size++;
        }
    }

    public void add_last(T dato) {
        if (this.isEmpty()) {
            this.add_first(dato);
        } else {
            Nodo<T> n = new Nodo<T>(dato);
            this.last.setNext(n);
            this.last = n;
            this.size++;
        }
    }

    public T get_element_at(int index) {
        if (index >= 0 && index < size) {
            Nodo<T> iterador = this.first;
            int x = 0;
            while (iterador != null) {
                if (index == x) {
                    return iterador.getDato();
                }
                iterador = iterador.getNext();
                x++;
            }
        }
        return null;
    }

    public void remove_at(int index) {
        if (index >= 0 && index < this.size) {

            if (index == 0) {
                if (this.size == 1) {
                    this.first = null;
                } else {
                    Nodo<T> aux = this.first.getNext();
                    this.first = null;
                    this.first = aux;
                }
            } else {
                Nodo<T> aux = this.first;
                int i = 0;
                while (aux != null) {
                    if (i == index - 1) {
                        break;
                    }
                    aux = aux.getNext();
                    i++;
                }
                assert aux != null;
                if (aux.getNext() == last) {
                    aux.setNext(null);
                    last = aux;
                } else {
                    aux.setNext(aux.getNext().getNext());
                }
            }
            this.size--;
        }
    }
}
