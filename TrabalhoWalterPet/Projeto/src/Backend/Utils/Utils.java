package Backend.Utils;

import Backend.Animais.*;

public class Utils {


    //Verifica qual tipo de animal e retorna uma String com o nome da classe específica

    public String checkadorTipoPet(Animal pet){
        if (pet == null){
            return "nulo";
        }
        return pet.getClass().getSimpleName();
    }

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isEmailValido(String email) {
        if (email == null || email.isBlank()) return false;
        return email.matches(EMAIL_REGEX);
    }

    public static String validarEmail(String email) throws IllegalArgumentException {
        if (!isEmailValido(email)) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
        return email;
    }

    public static String escaparCSV(String valor) {
        if (valor == null) return "";

        // Se o valor começa com caractere de risco, prefixar com aspas
        if (valor.length() > 0 &&
                "+-=@".indexOf(valor.charAt(0)) >= 0) {
            valor = "'" + valor;
        }

        // Se contém vírgula, aspas ou quebra de linha, encapsular
        if (valor.contains(",") || valor.contains("\"")
                || valor.contains("\n") || valor.contains("\r")) {
            valor = "\"" + valor.replace("\"", "\"\"") + "\"";
        }

        return valor;
    }

    public static String gerarSalt() {
        byte[] salt = new byte[16];
        new java.security.SecureRandom().nextBytes(salt);
        return java.util.HexFormat.of().formatHex(salt);
    }

    public static String hashComSalt(String texto, String salt) {
        String textoComSalt = texto + salt;
        try {
            byte[] hashBytes = java.security.MessageDigest
                    .getInstance("SHA-256")
                    .digest(textoComSalt.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return java.util.HexFormat.of().formatHex(hashBytes);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    public static String gerarUuid() {
        return java.util.UUID.randomUUID().toString();
    }
}
