import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;

public class ProducteurConsommateur {
	private static final int TAILLE_BUFFER = 5;
	private final Integer[] buffer = new Integer[TAILLE_BUFFER];
	private int indiceBufferProducteur = 0;
	private int indiceBufferConsommateur = 0;


	// TODO: Déclarer 3 sémaphores avec les bonnes valeurs initiales
	private final Semaphore placesLibres = new Semaphore(TAILLE_BUFFER);
	private final Semaphore elementsDisponibles = new Semaphore(0);  
	private final Semaphore mutexBuffer = new Semaphore(1); // C'est comme un mutex

	class Producteur implements Runnable {
		private final int num;
		
		public Producteur(int num) {
			this.num = num;
		}
		
		public void run() {
			for (int i = 0; i < 10; i++) {
				int produit = genererProduit();

				try {
				//Attendre une place libre
				placesLibres.acquire();
				
				//Acquérir l'accès exclusif au buffer  
				mutexBuffer.acquire();
				
				//Ajouter l'élément au buffer
				buffer[indiceBufferProducteur] = produit;
				indiceBufferProducteur = (indiceBufferProducteur + 1) % TAILLE_BUFFER;
				System.out.println("Produit : " + produit);
				
				//Libérer l'accès au buffer
				mutexBuffer.release();
				
				//Signaler qu'un élément est disponible
				elementsDisponibles.release();
				
				} catch (Exception e) {}

			}
		}
		
		private int genererProduit() {
				return (int)(Math.random() * 100);
		}
	}

	class Consommateur implements Runnable {
		private final int num;
		
		public Consommateur(int num) {
			this.num = num;
		}
		
		public void run() {
			for (int i = 0; i < 7; i++) {
				try {
				//Attendre qu'un élément soit disponible
				elementsDisponibles.acquire();
				
				//Acquérir l'accès exclusif au buffer
				mutexBuffer.acquire();
				
				//Retirer un élément du buffer
				int produit = buffer[indiceBufferConsommateur];
				buffer[indiceBufferConsommateur] = null;
				indiceBufferConsommateur = (indiceBufferConsommateur + 1) % TAILLE_BUFFER;
				System.out.println("Consommé : " + produit);
				
				//Libérer l'accès au buffer
				mutexBuffer.release();
				
				//Signaler qu'une place est libre
				placesLibres.release();
				
				} catch (Exception e) {}				
			}
		}
	}

	public Integer[] getBuffer() {
		return buffer;
	}

	public static void main (String[] args) {
	}
}