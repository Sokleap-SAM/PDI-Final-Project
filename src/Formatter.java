import java.math.BigDecimal;
import java.math.RoundingMode;

public class Formatter {
    public static double formatCurrency(double value) {
        double roundedValue = BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        return roundedValue;

    }

    public static void formatTextColor(String text, String color){
        if(color.equals("green")){
            System.out.print("\u001B[32m" + text + "\u001B[0m");
        }
        if(color.equals("yellow")){
            System.out.print("\u001B[33m" + text + "\u001B[0m");
        }
        if(color.equals("red")){
            System.out.print("\u001B[31m" + text + "\u001B[0m");
        }
        if(color.equals("blue")){
            System.out.print("\u001B[34m" + text + "\u001B[0m");
        }
        if(color.equals("pink")){
            System.out.print("\u001B[35m" + text + "\u001B[0m");
        }
    }

}
