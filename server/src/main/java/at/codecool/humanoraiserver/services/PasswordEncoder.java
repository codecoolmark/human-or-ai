package at.codecool.humanoraiserver.services;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {
    // Configuration taken from:
    // https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#argon2id
    private static final int SALT_LEN = 16;
    private static final int HASH_LEN = 32;
    private static final int PARALLELISM = 1;
    private static final int MEMORY = 19456;
    private static final int ITERATIONS = 2;

    private final Argon2PasswordEncoder encoder;

    public PasswordEncoder() {
        this.encoder = new Argon2PasswordEncoder(
                SALT_LEN,
                HASH_LEN,
                PARALLELISM,
                MEMORY,
                ITERATIONS);
    }

    public String encode(String raw) {
        return this.encoder.encode(raw);
    }

    public boolean isMatch(String raw, String encoded) {
        return this.encoder.matches(raw, encoded);
    }
}
