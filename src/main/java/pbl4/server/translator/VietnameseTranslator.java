package pbl4.server.translator;

import pbl4.server.utils.Utils;

import java.math.BigInteger;
import java.util.ArrayList;

public class VietnameseTranslator {
    protected static String[] mega = {
            "", "nghìn", "triệu", "tỷ",
            "nghìn tỷ", "triệu tỷ", "tỷ tỷ",
            "nghìn tỷ tỷ", "triệu tỷ tỷ", "tỷ tỷ tỷ",
            "nghìn tỷ tỷ tỷ", "triệu tỷ tỷ tỷ", "tỷ tỷ tỷ tỷ",
            "nghìn tỷ tỷ tỷ tỷ", "triệu tỷ tỷ tỷ tỷ", "tỷ tỷ tỷ tỷ tỷ",
            "nghìn tỷ tỷ tỷ tỷ tỷ", "triệu tỷ tỷ tỷ tỷ tỷ", "tỷ tỷ tỷ tỷ tỷ tỷ", // 10^63
            "nghìn tỷ tỷ tỷ tỷ tỷ tỷ", "triệu tỷ tỷ tỷ tỷ tỷ tỷ", "tỷ tỷ tỷ tỷ tỷ tỷ tỷ", // 10^93
            "nghìn tỷ tỷ tỷ tỷ tỷ tỷ tỷ", "triệu tỷ tỷ tỷ tỷ tỷ tỷ tỷ", "tỷ tỷ tỷ tỷ tỷ tỷ tỷ tỷ", // 10^123
            //Holy sh*t
    };
    protected static String[] units = {"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín"};
    protected static String[] unitsWithTens = {"", "mốt", "hai", "ba", "bốn", "lăm", "sáu", "bảy", "tám", "chín"};
    protected static String[] tens = {"", "mười", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};

    public static String translate(BigInteger number){
        ArrayList<String> result = new ArrayList<>();
        if (number.compareTo(BigInteger.ZERO) < 0){
            result.add("âm");
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
            result.addAll(translateTriplet(triplets[i], i == triplets.length - 1));
            if (i > 0) {
                result.add(VietnameseTranslator.mega[i]);
            }
        }
        return String.join(" ", result);
    }

    protected static ArrayList<String> translateTriplet(int triplet, boolean isLastTriplet){
        ArrayList<String> result = new ArrayList<>();
        int hundreds = triplet / 100;
        int tens = (triplet % 100) / 10;
        int units = triplet % 10;

        if (hundreds > 0 || !isLastTriplet) {
            if (hundreds > 0) {
                result.add(VietnameseTranslator.units[hundreds]);
                result.add("trăm");
            } else {
                result.add("không trăm");
            }
            if (tens > 0) {
                result.add(VietnameseTranslator.tens[tens]);
            } else if (units > 0) {
                result.add("lẻ");
            }
        } else {
            if (tens > 0) {
                result.add(VietnameseTranslator.tens[tens]);
            }
        }
        if (units > 0){
            if (tens >= 2){
                result.add(VietnameseTranslator.unitsWithTens[units]);
            } else {
                result.add(VietnameseTranslator.units[units]);
            }
        }
        return result;
    }
}
