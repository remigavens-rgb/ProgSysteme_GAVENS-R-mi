public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;

    public Inode( MemoryManager memoryManager, int inodeNumber) {

        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
    }

    public int getInodeOffset() {
		
        int inodeOff = MemoryManager.INODE_TABLE_OFFSET + (inodeNumber * INODE_SIZE);
		 
        return inodeOff;
    }

    public int getFileType() {
		
        byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset() + 4;
		
        return Utils.readInt(memory, offset);
    }

    public int getFileSize() {
		
        byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset() + 8;
		
        return Utils.readInt(memory, offset);
    }

    public int[] getDirectPointers() {

        byte[] memory = memoryManager.getFilesystemMemory();
        int[] pointers = new int[DIRECT_POINTERS];

		int debutDirectPointeur = getInodeOffset() + 28;
		
        for (int indice = 0; indice < DIRECT_POINTERS; indice++) {
            pointers[indice] = Utils.readInt(memory, debutDirectPointeur + indice * 4);
        }

        return pointers;
    }

    public void writeToMemory(
        int fileType,
        int fileSize,
        long creationTime,
        long modificationTime,
        int[] directPointers,
        int indirectPointer,
        short permissions,
        int linkCount) {

        byte[] memory = memoryManager.getFilesystemMemory();
        int offset = getInodeOffset();

        // 1. Numéro d'inode
        offset += Utils.writeInt(memory, offset, inodeNumber);

        // 2. Type
        offset += Utils.writeInt(memory, offset, fileType);

        // 3. Taille
        offset += Utils.writeInt(memory, offset, fileSize);

        // 4. Création
        offset += Utils.writeLong(memory, offset, creationTime);

        // 5. Modification dela date
        offset += Utils.writeLong(memory, offset, modificationTime);

        // 6. 10 pointeurs directs
        for (int indice = 0; i < DIRECT_POINTERS; indice++) {
            int pointeur = (directPointers != null && indice < directPointers.length) ? directPointers[indice] : 0;
            offset += Utils.writeInt(memory, offset, pointeur);
        }

        // 7. Pointeur indirect
        offset += Utils.writeInt(memory, offset, indirectPointer);

        // 8. Permissions
        offset += Utils.writeShort(memory, offset, permissions);

        // 9. Nombre de liens
        offset += Utils.writeShort(memory, offset, (short) linkCount);
    }
}