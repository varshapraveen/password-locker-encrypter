import java.security.SecureRandom;

public class PasswordGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final String CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SMALL_CAPS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "1234567890";
    private static final String SPECIAL_CHAR = "~!@#$%^&*(_+{}|:_[?]>=<";
    private static final String DICTIONARY = CAPS + SMALL_CAPS + NUMERIC + SPECIAL_CHAR;

    public String generatePassword(int len) {
        if (len < 8) { // Ensure minimum length of 8 for security
            throw new IllegalArgumentException("Password length must be at least 8 characters.");
        }

        StringBuilder password = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(DICTIONARY.length());
            password.append(DICTIONARY.charAt(index));
        }
        return password.toString();
    }
}
