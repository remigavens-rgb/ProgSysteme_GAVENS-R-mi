public class CompteurDangereux {
    // Variable partagée MODIFIABLE - source du problème
    private static long compteurGlobal = 0;
    
    //thread qui incrémente le compteur
    static class Incrementeur implements Runnable {
        private final int nombreIncrements;
        
        public Incrementeur(int nombreIncrements) {
            this.nombreIncrements = nombreIncrements;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < nombreIncrements; i++) {
                // RACE CONDITION ICI !
                //  compteurGlobal++ n'est pas atomique et peut être arrété -> donc c'est pas sécurisé
                compteurGlobal++;
            }
        }
    }
    
    /**
     * Méthode pour lire le compteur (également non protégée)
     * @return La valeur actuelle du compteur
     */
    public static long getCompteur() {
        return compteurGlobal;
    }
    
    /**
     * Méthode pour réinitialiser le compteur
     */
    public static void resetCompteur() {
        compteurGlobal = 0;
    }
    
}