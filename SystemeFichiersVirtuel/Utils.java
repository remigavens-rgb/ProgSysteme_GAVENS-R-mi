public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {

		for (int indice = 0; indice < 4; indice++){
			byte b1 = (byte)((value >> 8*(3 - indice) & 0xFF));
			memory[offset+indice] = b1;
		}

        return 4;
    }

    public static int readInt(byte[] memory, int offset) {

		int value = 0;

		for (int indice = 0; indice < 4 ; indice++){
			int b1L = (int)((memory[offset+indice] & 0xFF) << (8*(3 - indice)));
			value |= b1L;
		}

        return value;
    }

    public static int writeShort(byte[] memory, int offset, short value) {

		for (int indice = 0; indice < 2; indice++){
			byte b0 = (byte)((value >> 8*(1 - indice) & 0xFF));
			memory[offset+indice] = b0;
		}

        return 2;
    }

    public static short readShort(byte[] memory, int offset) {

		short value = 0;

		for (int indice = 0; indice < 2 ; indice++){
			int b0L = (int)((memory[offset+indice] & 0xFF) << (8*(1 - indice)));
			value |= b0L;
		}

        return value;
    }
	
	public static int writeLong(byte[] memory, int offset, long value) {

		for (int indice = 0; indice < 8; indice++){
			byte b2 = (byte)((value >> 8*(7 - indice) & 0xFF));
			memory[offset+indice] = b2;
		}

		return 8;
	}

	public static long readLong(byte[] memory, int offset) {

		long value = 0;

		for (int indice = 0; indice < 8 ; indice++){
			long b2L = (long)(memory[offset+indice] & 0xFF) << (8*(7 - indice));
			value |= b2L;
		}

        return value;
	}

	public static int writeString(
			byte[] memory,
			int offset,
			String str,
			int maxLength ) {

		byte[] strByte = str.getBytes();

		for (int indice = 0; indice < strByte.length; indice++) {
			memory[offset+i] = strByte[indice];
		}
		for (int indice = strByte.length; indice < maxLength; indice++) {
			memory[offset+i] = 0x00;
		}

		return maxLength;
	}

	public static String readString(
			byte[] memory,
			int offset,
			int maxLength ) {
		

		String result = "";	//STUB
		for (int indice = 0; indice < maxLength && memory[offset + indice] != 0; indice++) {
			result = result + String.valueOf((char)memory[offset + indice]);
		}

		return result;
	}
}