
public class Calcule extends Thread{
	
	
	private int valeur;
    private int multiplicateur;
    private int index;
    private int resultat;
	
	public Calcule(int valeur, int multiplicateur, int index) {
        this.valeur = valeur;
		this.multiplicateur = multiplicateur;
		this.index = index;
		resultat = 0;
    }
	 
	/*Ecriture de la seconde boucle for dans un Tread
	 * Programme Principal*/
	@Override
	public void run() {
        for (int j = 0; j < multiplicateur; j++) {
            resultat += valeur * valeur + valeur;
        }
	
	//getter recupération de resultat des calcules du tread courrant ??
	public long getResults() {
		return resultat;
	}
}