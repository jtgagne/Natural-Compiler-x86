package parser;

import code_generation.Register;
import code_generation.RegisterManager;

/**
 * Created by gagnej3 on 11/15/16.
 */
public class XTest {
    public static void main(String[] args){
        RegisterManager registerManager = new RegisterManager();
        RegisterManager.setUsage(Register.AX, true);
        boolean axAvail = RegisterManager.isInUse(Register.AX);
        boolean ahAvail = RegisterManager.isInUse(Register.AH);
        boolean alAvail = RegisterManager.isInUse(Register.AL);
        System.out.printf(" %s is in use: %s\n", Register.AX.toString(), axAvail);
        System.out.printf(" %s is in use: %s\n", Register.AH.toString(), ahAvail);
        System.out.printf(" %s is in use: %s\n", Register.AL.toString(), alAvail);
        //RegisterManager.printKeys();
    }
}
