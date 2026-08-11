package calculator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FormatterUtil {
    public String formatForDisplay(BigDecimal v, int maxDigits){
        if(v == null || v.compareTo(BigDecimal.ZERO) == 0){
            return "0";
        }

        /**
        *   有効桁数の判定
        */
        String plainString = v.stripTrailingZeros().toPlainString();
        String digitsOnly = plainString.replace(".", "").replace("-", "");
        boolean isExponential = digitsOnly.length() > maxDigits;

        /** 
        *   指数表記をおこうなうか判定
        */
        if (isExponential) {
            if (plainString.startsWith("0.") && !plainString.startsWith("0.0") && digitsOnly.length() <= (maxDigits + 1)) {
                isExponential = false;
            }
            if (plainString.startsWith("-0.") && !plainString.startsWith("-0.0") && digitsOnly.length() <= (maxDigits + 1)) {
                isExponential = false;
            }
        }

        /** 
        *   指数表記にする場合の処理
        */
        if (isExponential) {
        
        BigDecimal roundedForExp = v.round(new MathContext(maxDigits, RoundingMode.DOWN));
        DecimalFormat df = new DecimalFormat("0.0000000E0");

        return df.format(roundedForExp).toLowerCase();

        } else {
        /** 
        *   有効桁数に基づいて丸める
        */

        int effectiveDigits = maxDigits;

        if (plainString.startsWith("0.") && !plainString.startsWith("0.0")) {
            effectiveDigits = maxDigits - 1;
        } else if (plainString.startsWith("-0.") && !plainString.startsWith("-0.0")) {
            effectiveDigits = maxDigits - 1;
        }
        
        BigDecimal roundedValue = v.round(new MathContext(effectiveDigits, RoundingMode.DOWN));
        return roundedValue.stripTrailingZeros().toPlainString();
        }
    }
}
