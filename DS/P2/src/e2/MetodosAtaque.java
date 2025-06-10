package e2;

public interface MetodosAtaque {
    int usosFireball = 3;
    int usosWand = 5;
    int usosSword = 10;
    int getDmg();
    int getManaNecesario();
    int getUsos();
    void reducirUsos();
}