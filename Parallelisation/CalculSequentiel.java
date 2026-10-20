import Thread.java;

public class CalculSequentiel {
    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int multiplicateur = 1000;

    public static void main(String[] args) {
        System.out.println("=== Calcul Séquentiel ===");

        long debut = System.nanoTime();
        long sommeTotal = 0;
		int valeur;
		
		// Instantiation de la seconde boucle fior dans un thread
        Calcule th1;


        // TODO: Ce code sera remplacé par une version parallèle
        for (int i = 0; i < DONNEES.length; i++) {
            valeur = DONNEES[i];
			th1 = new Calcule(valeur, multiplicateur, i);
			
			//Statrs -> execution / appelle du thread th1
            th1.start();

			//Joins -> attend que th1 soit terminé avant de continuer le prog
			th1.join();

            sommeTotal += th1.getResults();
            System.out.println("Traitement de " + valeur + " terminé : " + th1.getResults());
        }

        long duree = (System.nanoTime() - debut) / 1_000_000;
        System.out.println("Résultat total : " + sommeTotal);
        System.out.println("Durée : " + duree + " ms");
    }
}