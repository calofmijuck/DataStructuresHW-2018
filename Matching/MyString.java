public class MyString {
    private String data;

    public MyString(String str) {
        if(str != null && str.length() != 6) {
            throw new IllegalArgumentException("Illegal length: " + str.length());
        }
        this.data = str;
    }

    public MyString() {
        this("");
    }

    public String toString() {
        return data;
    }

    public int hashCode() {
        int hash = 0;
        for(int i = 0; i < 6; i++) {
            hash += (int) data.charAt(i);
            hash %= 100;
        }
        return hash;
    }
}
