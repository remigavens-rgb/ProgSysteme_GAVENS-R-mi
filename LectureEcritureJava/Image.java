
import java.io.*;

public class Image {
    private int width;
    private int height;
    // pixels[y][x][0=R,1=G,2=B]
    private int[][][] pixels;
    private byte[][][] pixelsBit;

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /**
     * Constructeur : initialise une image vide.
     */
    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[height][width][3];
        pixelsBit = new byte[height][width][3];
    }

    /**
     * Définit la couleur d'un pixel à la position (x, y)
     */
    public void setPixel(int x, int y, int r, int g, int b) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            pixels[y][x][0] = r;
            pixels[y][x][1] = g;
            pixels[y][x][2] = b;

            pixelsBit[y][x][0] = (byte) r;
            pixelsBit[y][x][1] = (byte) g;
            pixelsBit[y][x][2] = (byte) b;
        }
    }

    /**
     * Sauvegarde l'image au format texte PPM (P3)
     */
    public void save_txt(String filename) throws IOException {
		FileWriter writer = new FileWriter(filename);
		writer.write("P3\n");
		writer.write(width + " " + height + "\n");
		writer.write("255\n");

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				writer.write(pixels[y][x][0] + " ");
				writer.write(pixels[y][x][1] + " ");
				writer.write(pixels[y][x][2] + " ");
			}
			writer.write("\n");
		}
		writer.close();
		System.out.println("Image PPM créée avec succés !");
	}


    /**
     * Sauvegarde l'image au format binaire PPM (P6)
     */
    public void save_bin(String filename) throws IOException {
        FileOutputStream out = new FileOutputStream(filename);

		String header = "P6\n" + width + " " + height + "\n255\n";
		out.write(header.getBytes());

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				out.write(pixelsBit[y][x][0]); 
				out.write(pixelsBit[y][x][1]); 
				out.write(pixelsBit[y][x][2]); 
			}
		}

        out.close();
    }
}