import java.util.Arrays;

public class TestCompteur {
	public static void main(String[] args) {
		int nbTest = 50;
		int nbSucces = 0;
		for (int indice = 0; indice < nbTest; indice++) {
			CompteurDangereux.resetCompteur();
			CompteurDangereux.run();
			System.out.println(CompteurDangereux.getCompteur());
			if (CompteurDangereux.getCompteur() == 9*1111111) {nbSucces++;}
		}
		
		double tauxSucces = ((double)nbSucces/(double)nbTest)*100;
		
		System.out.println(nbSucces + " tests ont réussis sur " + nbTest + ", un succès de " + tauxSucces + " %");
		
	}
}