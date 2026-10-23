import java.util.Arrays;

public class TestMutex {
	public static void main(String[] args) {
		int nbTest = 50;
		int nbSucces = 0;
		for (int indice = 0; indice < nbTest; indice++) {
			CompteurSecurise.resetCompteur();
			CompteurSecurise.run();
			System.out.println(CompteurSecurise.getCompteur());
			if (CompteurSecurise.getCompteur() == 9*1111111) {nbSucces++;}
		}
		
		double tauxSucces = ((double)nbSucces/(double)nbTest)*100;
		
		System.out.println(nbSucces + " tests ont réussis sur " + nbTest + ", un succès de " + tauxSucces + " %");
		
	}
}