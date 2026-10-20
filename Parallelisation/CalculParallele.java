public class CalculParallele {
	private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MUL = 1000;
	
	 public static void main(String[] args) {
		Calcule[] threads = new Calcule[DONNEES.length];
		int somme = 0;
		for (int i=0; i < threads; i++) {
			c[i] = new Calcule (DONNEES[i], MUL, i);
			c[i].start();
		}
		for (int i=0; i < c.length ; i++){
			c[i].join();
			somme += c[i].get();
		} 
	}
}