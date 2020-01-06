package EDD;
class Vertice{
    private String nombre;
    private Lista<Vertice> aristas;

    public Vertice(String nombre) {
        this.nombre = nombre;
        this.aristas = new Lista<Vertice>();
    }

    public void addArista(Vertice v){
        this.aristas.add_last(v);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Lista<Vertice> getAristas() {
        return aristas;
    }

    public void setAristas(Lista<Vertice> aristas) {
        this.aristas = aristas;
    }
}
public class Grafo {
    Vertice inicio;
    Lista<Vertice> vertices;

    public Grafo() {
        inicio = null;
        vertices = new Lista<Vertice>();
    }

    public void addVertice(Vertice v){
        this.vertices.add_last(v);
    }

    public Vertice getInicio() {
        return inicio;
    }

    public void setInicio(Vertice inicio) {
        this.inicio = inicio;
    }

    public Lista<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(Lista<Vertice> vertices) {
        this.vertices = vertices;
    }
}
