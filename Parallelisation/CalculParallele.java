public class CalculParallele {
	private static final int[] DONNEES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final int MUL = 1000;
	
	public static void main(String[] args) {

		long debut = System.nanoTime();
		long sommeResult = 0;

		//Crea tabl de thread
		Calcule[] tableTh = new Calcule[DONNEES.length];

		//Crea des instantcess des thread
		for (int indice = 0; indice < DONNEES.length; indice++) {
			tableTh[indice] = new Calcule(DONNEES[indice], MUL, indice);
		}

		//Start de tout les thread
		for (Calcule th : tableTh) {
			th.start();
		}

		//Attend que tout les thread se termine
		try {
			for (Calcule th : tableTh) {
				th.join();
			}
		}  catch (Exception e) {
				
		}

		//Recup la somme Result
		for (Calcule th : tableTh) {
			sommeResult += th.getResultat();
		}

		long duree = (System.nanoTime() - debut) / 1_000_000;
		System.out.println("Résultat total : " + sommeResult);
		System.out.println("Durée : " + duree + " ms");
	}
}