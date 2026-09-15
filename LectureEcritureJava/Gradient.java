public class Gradient {
    public static void main(String[] args) {
        int hauteur = 100;
        int largeur = 200;
    
        Image img = new Image(largeur, hauteur);

        // Génération du dégradé de bleu
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int bleu = x + 0; 
                img.setPixel(x, y, 0, 0, bleu);
            }
        }

        try {
            img.save_txt("Gradient.ppm");
			img.save_bin("GradientByte.ppm");
            System.out.println("Dégradé créé avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du dégradé : " + e.getMessage());
        }
    }
}
