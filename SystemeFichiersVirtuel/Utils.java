public class Utils {

    /*Methode de Decomposition int en memoire*/
    public static int writeInt(byte[] memory, int offset, int value) {
        
        for (int i=0;i< offset ;i++) {
            memory[offset + i] = (byte)((value >> 8*i) &0xFF);
        }

        return offset;
    }

    /*Methode de recomposition afin de lecture d'un int */
    public static int readInt(byte[] memory, int offset) {
        byte b1;
        for (int offsetCourant : memory[]) {
            b1 = b1 + memory[offsetCourant];    // Perte de données ????
        }

        return b1; // Erreur type byte -> int // mais byte<int donc ????
    }

    public static int writeShort(byte[] memory, int offset, short value) {

        for (int i=0;i< offset ;i++) {
            memory[offset + i] = (byte)((value >> 8*i) &0xFF);
        }

        return offset;
    }

    public static short readShort(byte[] memory, int offset) {
        byte b1;
        for (int offsetCourant : memory[]) {
            b1 = b1 + memory[offsetCourant];    // Perte de données ????
        }

        return b1; // Erreur type byte -> int // mais byte<int donc ????
    }
}