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
            "duodecillion", "tredecillion", "quattuordecillion"
    };
    protected static String[] units = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    protected static String[] tens = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy","eighty", "ninety"};
    protected static String[] teens = {"", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen","seventeen", "eighteen", "nineteen"};
    protected static String zero = "zero";
    protected static String minus = "minus";
    protected static String hundred = "hundred";
    protected static String and = " and ";
    protected static String link = "-";

    public static String translate(BigInteger number){
        StringBuilder result = new StringBuilder();
        if (number.compareTo(BigInteger.ZERO) < 0){
            result.append(EnglishTranslator.minus).append(" ");
            number = number.abs();
        }

        if (number.equals(BigInteger.ZERO)){
            return EnglishTranslator.zero;
        }

        int[] triplets = Utils.toTriplets(number);
        for (int i = triplets.length - 1; i >= 0; i--) {
            String translatedTriplet = translateTriplet(triplets[i]);
            result.append(translatedTriplet);
            if (i > 0 && !translatedTriplet.equals("")){
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

        if (triplet == 0){
            return "";
        }

        if (hundreds > 0){
            result += EnglishTranslator.units[hundreds] + " " + EnglishTranslator.hundred;
        }

        if (tens > 0){
            if (hundreds > 0){
                result += EnglishTranslator.and;
            }
            if (tens == 1 && units > 0){
                result += EnglishTranslator.teens[units];
            } else {
                result += EnglishTranslator.tens[tens];
                if (units > 0){
                    result += EnglishTranslator.link + EnglishTranslator.units[units];
                }
            }
        } else if (units > 0){
            if (hundreds > 0){
                result += EnglishTranslator.and;
            }
            result += EnglishTranslator.units[units];
        }
        return result;
    }
}
