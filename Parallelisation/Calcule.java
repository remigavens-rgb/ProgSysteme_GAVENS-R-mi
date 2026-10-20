
public class Calcule extends Thread{
	
	
	private final int valeur;
    private final int multiplicateur;
    private final int index;
    private long resultat;
	
	public Calcule(int valeur, int multiplicateur, int index) {
        this.valeur = valeur;
        this.multiplicateur = multiplicateur;
        this.index = index;
    }
	 
	/*Ecriture de la seconde boucle for dans un Tread
	 * Programme Principal*/
	@Override
	public void run() {
		resultat = 0;
        for (int j = 0; j < multiplicateur; j++) {
            resultat += valeur * valeur + valeur;
        }
	
	//getter recupération de resultat des calcules du tread courrant ??
	public long getResults() {
		return resultat;
	}
}