public class TestSemaphore {

	public static void main (String[] args) {
		Thread[] tabTh = new Thread[7];
		ProducteurConsommateur ProdConso = new ProducteurConsommateur();
		for (int indicage = 1; indicage <= 5; indicage++) {
            tabTh[indicage-1] = new Thread(ProdConso.new Producteur(indicage));
			tabTh[indicage-1].start();
        }

        for (int indicage = 1; indicage <= 2; indicage++) {
            tabTh[indicage+4] = new Thread(ProdConso.new Consommateur(indicage));
			tabTh[indicage+4].start();
        }
	}
}