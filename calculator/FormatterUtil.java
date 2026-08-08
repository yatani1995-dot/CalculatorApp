package calculator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FormatterUtil {
    public String formatForDisplay(BigDecimal v, int maxDigits){
        if(v == null){
            return "0";
        }
        if(v.compareTo(BigDecimal.ZERO) == 0){
            return "0";
        }

        /**
        *   不要な0の削除と有効数字のカウント
        */
        String plainString = v.stripTrailingZeros().toPlainString();
        String digitsOnly = plainString.replace(".", "").replace("-", "");

        /**
        *   有効数字に頭の０が含まれないため調整
        */
        int effectiveDigits = maxDigits;
        if(digitsOnly.startsWith("0")){
            effectiveDigits = maxDigits - 1;
        }

        /**
        *  ８桁に丸め
        */
        BigDecimal roundedValue = v.round(new MathContext(effectiveDigits, RoundingMode.DOWN));
        String result = roundedValue.stripTrailingZeros().toPlainString();


        /**
        *   有効数字が８桁を超える場合は指数表記にする
        */
        if(digitsOnly.length() > maxDigits){
            DecimalFormat DecimalFormat = new DecimalFormat("0.0000000E0");
            String engineeringString = DecimalFormat.format(roundedValue).toLowerCase();
            return engineeringString;
        }else{
            return result;
        }
    }
}
