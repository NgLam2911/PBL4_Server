package pbl4.server.utils;

import java.awt.*;

public class TextFormat {

    public static final String ESCAPE = "\u001B";

    //Make a color enum with string codes

    public enum Colors { //Deprecated ???
        //This enum only contain some base colors for CLI
        BLACK(new Color(0, 0, 0)),
        RED(new Color(255, 0, 0)),
        GREEN(new Color(0, 255, 0)),
        YELLOW(new Color(255, 255, 0)),
        BLUE(new Color(0, 0, 255)),
        PURPLE(new Color(255, 0, 255)),
        CYAN(new Color(0, 255, 255)),
        WHITE(new Color(255, 255, 255)),
        GRAY(new Color(128, 128, 128));

        private final Color color;
        Colors(Color color){
            this.color = color;
        }

        public Color getColor(){
            return this.color;
        }
    }

    public enum Style {
        NONE(0),
        BOLD(1),
        ITALIC(3),
        UNDERLINE(4),
        STRIKETHROUGH(9);

        private final int style;
        Style(int style){
            this.style = style;
        }

        public int getStyleID(){
            return this.style;
        }
    }

    public static String asciiFormat(Color color, Style style){
        return ESCAPE + "[" + style.getStyleID() + ";" + "38;2;" + color.getRed() + ";" + color.getGreen() + ";" + color.getBlue() + "m";
    }

    public static String asciiFormat(Colors color, Style style){
        return asciiFormat(color.getColor(), style);
    }
}
