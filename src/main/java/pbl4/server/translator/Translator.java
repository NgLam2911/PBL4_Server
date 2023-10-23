package pbl4.server.translator;

import java.math.BigInteger;

public class Translator{
    public enum Language {
        ENGLISH,
        VIETNAMESE,
        FRENCH
    }

    //Translator a number to a string
    //For example 1 -> "one"

    private final long number;

    public Translator(String number) throws NumberFormatException{
        this.number = Long.parseLong(number);
    }

    public Translator(long number){
        this.number = number;
    }

    public long getNumber(){
        return this.number;
    }

    public String translate(Language language){
        //TODO: Handle this.
        switch (language){
            case ENGLISH:
                return EnglishTranslator.translate(this.number);
            case VIETNAMESE:
                return VietnameseTranslator.translate(this.number);
            case FRENCH:
                return FrenchTranslator.translate(this.number);
        }
        return "";
    }
}
