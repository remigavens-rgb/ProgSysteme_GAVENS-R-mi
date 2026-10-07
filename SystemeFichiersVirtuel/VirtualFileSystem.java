import java.util.*;

public class VirtualFileSystem {

    private MemoryManager memoryManager;
    private final Directory rootDirectory;

    public VirtualFileSystem() {
        this.memoryManager =
                new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory =
                memoryManager.getFilesystemMemory();

        for (int i = 0; i < MemoryManager.MAX_INODES; i++) {
            int offset = MemoryManager.INODE_TABLE_OFFSET + (i * Inode.INODE_SIZE);
            int storedInodeNumber = Utils.readInt(memory, offset);
            if (storedInodeNumber != i) {
                return i;
            }
        }

        return -1;
    }

    public boolean createFile(
            String directory,
            String filename) {

        int inodeNum = allocateInode();

        if (inodeNum == -1) {
            return false;
        }

        // Construire l'inode.
        Inode inode = new Inode(memoryManager, inodeNum);

        long courrant = System.currentTimeMillis();

        // L'initialiser comme fichier vide.
        int[] emptyPointers = new int[Inode.DIRECT_POINTERS];
        inode.writeToMemory(0, 0, courrant, courrant, emptyPointers, 0, (short) 0644, 1);

        try {
            rootDirectory.addEntry(filename, inodeNum);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public boolean writeFile(int inodeNum, byte[] data) {

        int blocksNeeded =
                (data.length
                + MemoryManager.BLOCK_SIZE - 1)
                / MemoryManager.BLOCK_SIZE;

        if (blocksNeeded > Inode.DIRECT_POINTERS) {
            throw new RuntimeException("Erreur : Pas assez d'espace dans l'inode pour écrire le fichier");
        }

        int[] blockPointers = new int[Inode.DIRECT_POINTERS];

        // Allouer blocksNeeded blocs.
        int allouee = 0;

        for (; allouee < blocksNeeded; allouee++) {
            int bloc = memoryManager.allocateBlock();
            if (bloc == -1) {
                break;
            }
            blockPointers[allouee] = bloc;
        }

        if (allouee < blocksNeeded) {
            for (int indice = 0; indice < allouee; indice++) {
                memoryManager.freeBlock(blockPointers[indice]);
                blockPointers[indice] = 0;
            }

            throw new RuntimeException("Erreur : Pas assez d'espace dans l'inode pour écrire le fichier");
        }



        byte[] memory = memoryManager.getFilesystemMemory();

        int bytesRemaining = data.length;

        int dataSrcOffset = 0;

        for (int indice = 0; indice < blocksNeeded; indice++) {

            int blockNo = blockPointers[indice];
            int blockOffset = blockNo * MemoryManager.BLOCK_SIZE;
            int bytesToCopy = Math.min(MemoryManager.BLOCK_SIZE, bytesRemaining - dataSrcOffset);
            System.arraycopy(data, dataSrcOffset, memory, blockOffset, bytesToCopy);
            dataSrcOffset += bytesToCopy;
        }

        //Car pas de gestion des pointeurs indirects

        Inode inode = new Inode(memoryManager, inodeNum);
        long courrant = System.currentTimeMillis();
        inode.writeToMemory(0, bytesRemaining, courrant, courrant, blockPointers, 0, (short) 0644, 1);

        return true;
    }

    public byte[] readFile(int inodeNum) {

        Inode inode = new Inode(memoryManager, inodeNum);
        int fileSize = inode.getFileSize();

        if (fileSize == 0) {
            return new byte[0];
        }

        byte[] fileData = new byte[fileSize];
        byte[] memory = memoryManager.getFilesystemMemory();
        int[] blockPointers = inode.getDirectPointers();
        int dataOffset = 0;
        int blocksToRead = (fileSize + MemoryManager.BLOCK_SIZE - 1) / MemoryManager.BLOCK_SIZE;

        //Check les bloc uses et les copient dans fileData chaques frag
        for (int indice = 0; indice < blocksToRead && dataOffset < fileSize; indice++) {

            if (blockPointers[indice] == 0) {
                break;
            }

            int blockOffset = blockPointers[indice] * MemoryManager.BLOCK_SIZE;
            int bytesToCopy = Math.min(MemoryManager.BLOCK_SIZE, fileSize - dataOffset);
            System.arraycopy(memory, blockOffset, fileData, dataOffset, bytesToCopy);
            dataOffset += bytesToCopy;
        }

        return fileData;
    }

    public boolean FileClear(String directory, String filename) {

        int inodeNum = findInodeByName(directory, filename);

        if (inodeNum == -1) { 
            return false;
        }
            
        Inode inode = new Inode(memoryManager, inodeNum);
        int fileSize = inode.getFileSize();
        int blocks = (fileSize + MemoryManager.BLOCK_SIZE - 1) / MemoryManager.BLOCK_SIZE;
        int[] blockPointers = inode.getDirectPointers();
        
        for (int indice = 0; indice < blocks && indice < blockPointers.length; indice++) {
            if (blockPointers[indice] != 0) {
                memoryManager.freeBlock(blockPointers[indice]);
            }
        }

        inode.markFree();
        
        return true;
    }
}
