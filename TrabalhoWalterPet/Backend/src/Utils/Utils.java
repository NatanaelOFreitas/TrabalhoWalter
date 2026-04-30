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
}
