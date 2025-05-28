package carteDuJeu;

public interface ElementMobile extends ElementCarte {
    int getCasesMaxDeplacement();
    String getNom();
    boolean estPersonnage();
    public void subirDegats(int degats) ;
    public boolean estMort();
    public int getPointsDeVie();
    public int getPointsDeVieMax();
    public int getForce();
    public int getDexterite();
    public int getInitiative();
    public String getSymbole();
    public boolean estElementMobile();
}
