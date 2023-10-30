package pbl4.server.utils;

import java.math.BigInteger;

public class Utils {

    public static int[] toTriplets(BigInteger number){
        int triplets_count = number.toString().length() / 3 + (number.toString().length() % 3 == 0 ? 0 : 1);
        int[] triplets = new int[triplets_count];
        for (int i = 0; i < triplets_count; i++){
            triplets[i] = number.mod(BigInteger.valueOf(1000)).intValue();
            number = number.divide(BigInteger.valueOf(1000));
        }
        return triplets;
    }
}
