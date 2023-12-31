package pbl4.server.translator;

import java.math.BigInteger;

public class Translator{
    public enum Language {
        ENGLISH,
        VIETNAMESE,
        FRENCH;
    }

    //Translator a number to a string
    //For example 1 -> "one"

    private final BigInteger number;

    public Translator(String number) throws NumberFormatException{
        this.number = new BigInteger(number);
    }

    public Translator(BigInteger number){
        this.number = number;
    }

    public BigInteger getNumber(){
        return this.number;
    }

    public String translate(Language language){
        //TODO: Handle this.
        return switch (language) {
            case ENGLISH -> EnglishTranslator.translate(this.number);
            case VIETNAMESE -> VietnameseTranslator.translate(this.number);
            case FRENCH -> FrenchTranslator.translate(this.number);
        };
    }
}
