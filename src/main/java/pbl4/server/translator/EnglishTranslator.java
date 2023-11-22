package pbl4.server.translator;

import pbl4.server.utils.Utils;

import java.math.BigInteger;
import java.util.ArrayList;

public class EnglishTranslator {

    //Contain multiples of 10
    protected static String[] mega = {
            "", "thousand", "million", "billion",
            "trillion", "quadrillion", "quintillion",
            "sextillion", "septillion", "octillion",
            "nonillion", "decillion", "undecillion",
            "duodecillion", "tredecillion", "quattuordecillion",
            "quindecillion", "sexdecillion", "septendecillion",
            "octodecillion", "novemdecillion", "vigintillion", // 10^63
            "unvigintillion", "duovigintillion", "trevigintillion", // 10^93
            "quattuorvigintillion", "quinvigintillion", "sexvigintillion", // 10^123
    };
    protected static String[] units = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    protected static String[] tens = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy","eighty", "ninety"};
    protected static String[] teens = {"", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen","seventeen", "eighteen", "nineteen"};

    public static String translate(BigInteger number){
        ArrayList<String> result = new ArrayList<>();
        if (number.compareTo(BigInteger.ZERO) < 0){
            result.add("minus");
            number = number.abs();
        }

        if (number.equals(BigInteger.ZERO)){
            return "zero";
        }
        int[] triplets = Utils.toTriplets(number);
        for (int i = triplets.length - 1; i >= 0; i--) {
            if (triplets[i] == 0){
                continue;
            }
            result.addAll(translateTriplet(triplets[i]));
            if (i > 0){
                result.add(EnglishTranslator.mega[i]);
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
            result.add(EnglishTranslator.units[hundreds]);
            result.add("hundred");
        }
        if (tens == 0 && units == 0) {
            return result;
        }
        switch (tens) {
            case 0:
                result.add(EnglishTranslator.units[units]);
                break;
            case 1:
                result.add(EnglishTranslator.teens[units]);
                break;
            default:
                if (units > 0) {
                    String word = EnglishTranslator.tens[tens] + "-" + EnglishTranslator.units[units];
                    result.add(word);
                } else {
                    result.add(EnglishTranslator.tens[tens]);
                }
                break;
        }
        return result;
    }
}
