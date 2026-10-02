import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MemoryManager {

    public static final int BLOCK_SIZE = 512;
    public static final int TOTAL_MEMORY = 1024 * 1024;
    public static final int NUM_BLOCKS = TOTAL_MEMORY / BLOCK_SIZE;

    public static final int SUPERBLOCK_OFFSET = 0;
    public static final int BITMAP_OFFSET = BLOCK_SIZE;
    public static final int INODE_TABLE_OFFSET = 2 * BLOCK_SIZE;
    public static final int DATA_OFFSET = 129 * BLOCK_SIZE;

    public static final int INODE_SIZE = 128;
    public static final int INODE_TABLE_SIZE = DATA_OFFSET - INODE_TABLE_OFFSET;
    public static final int MAX_INODES = INODE_TABLE_SIZE / INODE_SIZE;

    private byte[] memory;

    public MemoryManager() {
        this.memory = new byte[TOTAL_MEMORY];
        initializeFilesystem();
    }

    private void initializeFilesystem() {
        
        writeSuperblock();

        for (int indice = 0; indice < 16; indice++ ) {
                memory[BITMAP_OFFSET + indice] = (byte)0xFF;
        }
    }

    private void writeSuperblock() {

        Utils.writeString(
                memory,
                SUPERBLOCK_OFFSET,
                "MYFS1.0",
                16);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 16,
                BLOCK_SIZE);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 20,
                TOTAL_MEMORY);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 24,
                NUM_BLOCKS);

        Utils.writeInt(
                memory,
                SUPERBLOCK_OFFSET + 28,
                MAX_INODES);
    }

    public byte[] getFilesystemMemory() {
        return memory; 
    }

    public boolean setBlockUsed(int blockNumber, boolean used) {

        if (blockNumber < 0 || blockNumber >= NUM_BLOCKS) {
            return false;
        }

        int byteIndex = blockNumber / 8;
        int bitPosition = blockNumber % 8;
        int offset = BITMAP_OFFSET + byteIndex;

        //Récup bit courrant dans le mémoire
        byte current = memory[offset];

        // Si bloc use => marqué comme use
        // si bloc non use => marqué libre
        if (used) {
            //
            current = (byte) (current | (1 << bitPosition));
        } else if (!used) {
             // met à 0 le bit à bitPosition de current
            current = (byte) (current & ~(1 << bitPosition));
        }

        //Réécriture dans la mémoire
        memory[offset] = current;

        return true;
    }
    

    public int isBlockUsed(int blockNumber) {

        if (blockNumber < 0 ||
                blockNumber >= NUM_BLOCKS) {
                return -1;
        }

        //Calcule du byteIndex
        int byteIndex = blockNumber / 8;

        //Calcule du bitPosition
        int bitPosition = blockNumber % 8;

        //Détermination de l'offset
        int offset = BITMAP_OFFSET + byteIndex;

        //lire le bit et renvoie true si le bloc est use et false si non use
        return ((memory[offset] >> bitPosition)   & 1) == 1;
    }

        public int allocateBlock() {

        for (int indice = 129; indice < NUM_BLOCKS; indice++) {
            if (!isBlockUsed(indice)) {
                setBlockUsed(indice, true);
                return indice;
            }
        }

        // si bloc non libre
        return -1; 
    }
}