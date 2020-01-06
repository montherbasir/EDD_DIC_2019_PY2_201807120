package Clases;

public class ErrorU {
    private Usuario user;
    private String problem;

    public ErrorU(Usuario user, String problem) {
        this.user = user;
        this.problem = problem;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }
}
