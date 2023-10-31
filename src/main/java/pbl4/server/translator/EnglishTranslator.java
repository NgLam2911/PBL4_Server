package pbl4.server.translator;

import pbl4.server.utils.Utils;

import java.math.BigInteger;

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
        StringBuilder result = new StringBuilder();
        if (number.compareTo(BigInteger.ZERO) < 0){
            result.append("minus ");
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
            result.append(translateTriplet(triplets[i]));
            if (i > 0){
                result.append(" ").append(EnglishTranslator.mega[i]).append(" ");
            }
        }
        return result.toString();
    }

    protected static String translateTriplet(int triplet){
        String result = "";
        int hundreds = triplet / 100;
        int tens = (triplet % 100) / 10;
        int units = triplet % 10;

        if (hundreds > 0){
            result += EnglishTranslator.units[hundreds] + " " + "hundred";
        }

        if (tens > 0){
            if (hundreds > 0){
                result += " and ";
            }
            if (tens == 1 && units > 0){
                result += EnglishTranslator.teens[units];
            } else {
                result += EnglishTranslator.tens[tens];
                if (units > 0){
                    result += "-" + EnglishTranslator.units[units];
                }
            }
        } else if (units > 0){
            if (hundreds > 0){
                result += " and ";
            }
            result += EnglishTranslator.units[units];
        }
        return result;
    }
}
