package pbl4.server.translator;

import pbl4.server.utils.Utils;

import java.math.BigInteger;

public class VietnameseTranslator {
    protected static String[] mega = {
            "", "nghìn", "triệu", "tỷ",
            "nghìn tỷ", "triệu tỷ", "tỷ tỷ",
            "nghìn tỷ tỷ", "triệu tỷ tỷ", "tỷ tỷ tỷ",
            "nghìn tỷ tỷ tỷ", "triệu tỷ tỷ tỷ", "tỷ tỷ tỷ tỷ",
            "nghìn tỷ tỷ tỷ tỷ", "triệu tỷ tỷ tỷ tỷ", "tỷ tỷ tỷ tỷ tỷ" //holy sh*t ...
    };
    protected static String[] units = {"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
    protected static String[] unitsWithTens = {"", "mốt", "hai", "ba", "bốn", "lăm", "sáu", "bảy", "tám", "chín"};
    protected static String[] tens = {"", "mười", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};

    public static String translate(BigInteger number){
        StringBuilder result = new StringBuilder();
        if (number.compareTo(BigInteger.ZERO) < 0){
            result.append(" âm");
            number = number.abs();
        }

        if (number.equals(BigInteger.ZERO)){
            return "không";
        }

        int[] triplets = Utils.toTriplets(number);
        for (int i = triplets.length - 1; i >= 0; i--) {
            if (triplets[i] == 0){
                continue;
            }
            result.append(translateTriplet(triplets[i], i == triplets.length - 1));
            if (i > 0) {
                result.append(" ").append(VietnameseTranslator.mega[i]).append(" ");
            }
        }
        return result.toString();
    }

    protected static String translateTriplet(int triplet, boolean isLastTriplet){
        StringBuilder result = new StringBuilder();
        int hundreds = triplet / 100;
        int tens = (triplet % 100) / 10;
        int units = triplet % 10;

        if (hundreds > 0 || !isLastTriplet) {
            if (hundreds > 0) {
                result.append(VietnameseTranslator.units[hundreds]).append(" trăm ");
            } else {
                result.append("không trăm ");
            }
            if (tens > 0) {
                result.append(VietnameseTranslator.tens[tens]).append(" ");
            } else if (units > 0) {
                result.append("lẻ ");
            }
        } else {
            if (tens > 0) {
                result.append(VietnameseTranslator.tens[tens]).append(" ");
            }
        }
        if (units > 0){
            if (tens >= 2){
                result.append(VietnameseTranslator.unitsWithTens[units]);
            } else {
                result.append(VietnameseTranslator.units[units]);
            }
        }
        return result.toString();
    }
}
