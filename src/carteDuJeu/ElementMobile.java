package carteDuJeu;

public interface ElementMobile extends ElementCarte {
    int getCasesMaxDeplacement();
    String getNom();
    boolean estPersonnage();
    void subirDegats(int degats) ;
    boolean estMort();
    int getPointsDeVie();
    int getPointsDeVieMax();
    int getForce();
    int getDexterite();
    int getInitiative();
    String getSymbole();
    boolean estElementMobile();
}
