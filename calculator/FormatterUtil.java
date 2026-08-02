package calculator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class FormatterUtil {
    public String formatForDisplay(BigDecimal v, int maxDigits){
        if(v == null){
            return "0";
        }
        if(v.compareTo(BigDecimal.ZERO) == 0){
            return "0";
        }

        //有効数字８桁に丸め
        BigDecimal roundedValue = v.round(new MathContext(maxDigits, RoundingMode.DOWN));

        //通常表記の文字列
        String plainString = roundedValue.toPlainString();

        //.-除く桁数取得
        String digitsOnly = plainString.replace(".", "").replace("-", "");

        //有効数字が８桁を超える場合は指数表記にする
        if(digitsOnly.length() > maxDigits){
            String engineeringString = roundedValue.toString().toLowerCase();
            return engineeringString;
        }else{
            return plainString;
        }
    }
}
