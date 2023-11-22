package pbl4.server.translator;

import pbl4.server.utils.Utils;

import java.math.BigInteger;
import java.util.ArrayList;

public class FrenchTranslator{

    protected static String[] mega = {
            "", "mille", "million", "milliard",
            "billion", "billiard", "trillion",
            "trilliard", "quadrillion", "quadrilliard",
            "quintillion", "quintilliard", "sextillion",
            "sextilliard", "septillion", "septilliard",
            "octillion", "octilliard", "nonillion",
            "nonilliard", "decillion", "decilliard", // 10^63
            "undecillion", "undecilliard", "duodecillion", // 10^93
            "duodecilliard", "tredecillion", "tredecilliard", // 10^123
    };
    protected static String[] units = {"", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
    protected static String[] tens = {"", "dix", "vingt", "trente", "quarante", "cinquante", "soixante", "soixante", "quatre-vingt", "quatre-vingt"};
    protected static String[] teens = {"dix", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"};

    public static String translate(BigInteger number){
        ArrayList<String> result = new ArrayList<>();
        if (number.compareTo(BigInteger.ZERO) < 0){
            result.add("moins");
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
                result.add("mille");
            }

            result.addAll(translateTriplet(triplets[i]));
            if (i > 0){
                String mega = FrenchTranslator.mega[i];
                if (!mega.equals("mille") && triplets[i] > 1) {
                    mega += "s";
                }
                result.add(mega);
            }
        }
        return String.join(" ", result);
    }

    protected static ArrayList<String> translateTriplet(int triplet){
        ArrayList<String> result = new ArrayList<>();
        int hundreds = triplet / 100;
        int tens = (triplet % 100) / 10;
        int units = triplet % 10;

        if (hundreds > 0) {
            if (hundreds == 1) {
                result.add("cent");
            } else {
                result.add(FrenchTranslator.units[hundreds]);
                if (tens == 0 && units == 0) {
                    result.add("cents");
                    return result;
                } else {
                    result.add("cent");
                }
            }
        }
        if (tens == 0 && units == 0) {
            return result;
        }
        switch (tens) {
            case 0:
                result.add(FrenchTranslator.units[units]);
                break;
            case 1:
                result.add(FrenchTranslator.teens[units]);
                break;
            case 7:
                if (units == 1) {
                    result.add(FrenchTranslator.tens[tens]);
                    result.add("et");
                    result.add(FrenchTranslator.teens[units]);
                } else {
                    String word = FrenchTranslator.tens[tens] + "-" + FrenchTranslator.teens[units];
                    result.add(word);
                }
                break;
            case 8:
                if (units == 0) {
                    result.add(FrenchTranslator.tens[tens] + "s");
                } else {
                    String word = FrenchTranslator.tens[tens] + "-" + FrenchTranslator.units[units];
                    result.add(word);
                }
                break;
            case 9:
                String word = FrenchTranslator.tens[tens] + "-" + FrenchTranslator.teens[units];
                result.add(word);
                break;
            default:
                switch (units) {
                    case 0 -> result.add(FrenchTranslator.tens[tens]);
                    case 1 -> {
                        result.add(FrenchTranslator.tens[tens]);
                        result.add("et");
                        result.add(FrenchTranslator.units[units]);
                    }
                    default -> {
                        word = FrenchTranslator.tens[tens] + "-" + FrenchTranslator.units[units];
                        result.add(word);
                    }
                }
                break;
        }
        return result;
    }
}
