package Utils;

import Animais.*;

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
}
