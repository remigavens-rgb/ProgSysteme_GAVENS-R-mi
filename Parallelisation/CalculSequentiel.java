import Thread.java;

public class CalculSequentiel {
    private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MUL = 1000;

    public static void main(String[] args) {
        System.out.println("=== Calcul Séquentiel ===");
        
        long debut = System.nanoTime();
        long sommeResult = 0;
        
        for (int indice = 0; indice < DONNEES.length; indice++) {
            int valeur = DONNEES[indice];
            
            
            long resultat = 0;
            for (int indice2 = 0; indice2 < MUL; indice2++) {
                resultat += valeur * valeur + valeur;
            }
            
            sommeResult += resultat;
            System.out.println("Traitement de " + valeur + " terminé : " + resultat);
        }
        
        long duree = (System.nanoTime() - debut) / 1_000_000;
        System.out.println("Résultat total : " + sommeResult);
        System.out.println("Durée : " + duree + " ms");
    }
}