package utils;
public class ByteSlice {

    private ByteSlice() {
        // Prevent instantiation
    }

    public static byte[] slice(byte[] input, int start, int end) {
        if (input == null || start < 0 || end > input.length || start >= end) {
            return new byte[0];
        }
        byte[] output = new byte[end - start];
        System.arraycopy(input, start, output, 0, end - start);
        return output;
    }



    public static byte[] slice(byte[] input, int start) {
        if (input == null || start < 0 || start >= input.length) {
            return new byte[0];
        }
        return slice(input, start, input.length);
    }
}
