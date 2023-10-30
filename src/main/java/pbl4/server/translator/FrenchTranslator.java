package pbl4.server.translator;

import pbl4.server.utils.Utils;

import java.math.BigInteger;

public class FrenchTranslator{

    protected static String[] mega = {
            "", "mille", "million", "milliard",
            "billion", "billiard", "trillion",
            "trilliard", "quadrillion", "quadrilliard",
            "quintillion", "quintilliard"
    };
    protected static String[] units = {"", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
    protected static String[] tens = {"", "dix", "vingt", "trente", "quarante", "cinquante", "soixante", "soixante", "quatre-vingt", "quatre-vingt"};
    protected static String[] teens = {"dix", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"};

    public static String translate(BigInteger number){
        StringBuilder result = new StringBuilder();
        if (number.compareTo(BigInteger.ZERO) < 0){
            result.append("moins ");
            number = number.abs();
        }

        if (number.equals(BigInteger.ZERO)){
            return "zéro";
        }

        int[] triplets = Utils.toTriplets(number);
        for (int i = triplets.length - 1; i >= 0; i--) {
            if (triplets[i] == 0){
                continue;
            }
            if (triplets[i] == 1 && i == 1){ //Special case for 1000
                result.append("mille");
            }

            result.append(translateTriplet(triplets[i]));
            if (i > 0){
                String mega = FrenchTranslator.mega[i];
                if (!mega.equals("mille") && triplets[i] > 1) {
                    mega += "s";
                }
                result.append(" ").append(mega).append(" ");
            }
        }
        return result.toString();
    }

    protected static String translateTriplet(int triplet){
        StringBuilder result = new StringBuilder();
        int hundreds = triplet / 100;
        int tens = (triplet % 100) / 10;
        int units = triplet % 10;

        if (hundreds > 0) {
            if (hundreds == 1) {
                result.append(" cent ");
            } else {
                result.append(FrenchTranslator.units[hundreds]);
                if (tens == 0 && units == 0) {
                    result.append(" cents");
                    return result.toString();
                } else {
                    result.append(" cent ");
                }
            }
        }
        if (tens == 0 && units == 0) {
            return result.toString();
        }
        switch (tens) {
            case 0:
                result.append(FrenchTranslator.units[units]);
                break;
            case 1:
                result.append(FrenchTranslator.teens[units]);
                break;
            case 7:
                if (units == 1) {
                    result.append(FrenchTranslator.tens[tens]);
                    result.append(" et ");
                    result.append(FrenchTranslator.teens[units]);
                } else {
                    String word = FrenchTranslator.tens[tens] + "-" + FrenchTranslator.teens[units];
                    result.append(word);
                }
                break;
            case 8:
                if (units == 0) {
                    result.append(FrenchTranslator.tens[tens]).append("s");
                } else {
                    String word = FrenchTranslator.tens[tens] + "-" + FrenchTranslator.units[units];
                    result.append(word);
                }
                break;
            case 9:
                String word = FrenchTranslator.tens[tens] + "-" + FrenchTranslator.teens[units];
                result.append(word);
                break;
            default:
                switch (units) {
                    case 0 -> result.append(FrenchTranslator.tens[tens]);
                    case 1 -> {
                        result.append(FrenchTranslator.tens[tens]);
                        result.append(" et ");
                        result.append(FrenchTranslator.units[units]);
                    }
                    default -> {
                        word = FrenchTranslator.tens[tens] + "-" + FrenchTranslator.units[units];
                        result.append(word);
                    }
                }
                break;
        }
        return result.toString();
    }
}
