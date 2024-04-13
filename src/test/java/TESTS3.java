public class TESTS3 {
    public static void main(String[] args) {
        StringBuilder buffer = new StringBuilder("AAAAAAAAAAAAAAA HELO");
        buffer.replace(buffer.length()-1, buffer.length(), "");
        System.out.println(buffer);
    }
}
