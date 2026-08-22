package calculator;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorModel {
    private BigDecimal leftOperand;
    private StringBuilder currentInput;
    private Operator pendingOp;
    private InputState state;
    private int maxDigits = 8;

    /**  
    *   エラー処理用
    */
    private FormatterUtil formaterUtill;
    private ErrorHandler errorHandler;

    /**
    *   起動時初期状態 
    */
    public CalculatorModel(){
        currentInput = new StringBuilder();
        leftOperand = BigDecimal.ZERO;
        pendingOp = Operator.NONE;
        state = InputState.READY;
        formaterUtill = new FormatterUtil();
        errorHandler = new ErrorHandler();
    }

    /**
    *   入力処理
    */
    public boolean appendDigit(char ch){
        if(state == InputState.ERROR){
            return false;
        }

        //0連続入力禁止と0の上書き 
        if(currentInput.toString().equals("0")||currentInput.toString().equals("-0")){
            if (ch == '0') {
                return false;
            }else if(ch == '.' ){

            }else{
                if(currentInput.indexOf("-") == 0){
                    currentInput.setLength(1);
                }else{
                    currentInput.setLength(0);
                }
            }
        }

        //答え表示後の数字入力
        if(state == InputState.RESULT){
            currentInput.setLength(0);
        }

        //演算子を受け入れていたら中身を0にする
        if(state == InputState.INPUT_OPERATOR){
            currentInput.setLength(0);
        }

        //文字数制御 
        int length = currentInput.length();
        if(currentInput.indexOf(".") != -1){
            length--;
        }
        if(currentInput.indexOf("-") != -1){
            length--;
        }
        if(length >= maxDigits){
            return false;
        }

        currentInput.append(ch);
        state = InputState.INPUT_NUMBER;
        return true;
    }

    /**
    *   ドット処理
    */
    public boolean appendDot(){
        if(state == InputState.ERROR){
            return false;
        }

        //.が何個あるか判定。複数あれば0以上になりfalse 
        if(currentInput.indexOf(".") >= 0){
            return false;
        }

        //最初に.押下時０追加
        if(currentInput.length() ==0){
            currentInput.append("0");
        }

        //-の後に数値入力がなければ入力不可
        if(currentInput.length() == 1 && currentInput.charAt(0) == '-'){
            return false;
        }

        //答え表示後小数点の入力不可
        if(state == InputState.RESULT){
            return false;
        }

        currentInput.append('.');
        return true;
    }

    /**
    *   演算子処理
    */
    public void inputOperator(Operator op){
        //初期状態のみーを符号として受け入れ
        if(state == InputState.READY && currentInput.length() == 0 && op == Operator.SUBTRACT){
            currentInput.append('-');
            state = InputState.INPUT_NUMBER;
            return;
        }

        if (currentInput.length() == 0||currentInput.toString().equals("-")) {
        return; 
    }

        BigDecimal inputValue = new BigDecimal(currentInput.toString());

        //数値入力なしは不可
        if(inputValue.compareTo(BigDecimal.ZERO)== 0){
            return;
        }

        //連続計算を含めた挙動
        if(pendingOp == Operator.NONE){
            leftOperand = inputValue;
            
        }else if(state == InputState.INPUT_OPERATOR){
            pendingOp = Operator.NONE;
        }else{
            leftOperand = apply(pendingOp,leftOperand,inputValue);
            String result = formaterUtill.formatForDisplay(leftOperand, maxDigits);
            currentInput.setLength(0);
            currentInput.append(result);
        }

        //演算子の受け入れ
        pendingOp = op;
        state = InputState.INPUT_OPERATOR;
    }

    /**
    *   計算処理
    */
    public BigDecimal apply(Operator op,BigDecimal left,BigDecimal right){
        switch (op) {
            case ADD:
                return left.add(right);
        
            case SUBTRACT:
                return left.subtract(right);
            
            case MULTIPLY:
                return left.multiply(right);

            case DIVIDE:
                try{
                    return left.divide(right,maxDigits,RoundingMode.DOWN);
                }catch(ArithmeticException e){
                    errorHandler.handle(e);
                    state = InputState.ERROR;
                    return BigDecimal.ZERO;
                }

            default:
                return BigDecimal.ZERO;
        }
    }

    /**
    *   イコール処理
    */
    public void equalsOp(){
        if(pendingOp == Operator.NONE){
            return;
        }
        if(state != InputState.INPUT_NUMBER){
            return;
        }

        BigDecimal rightOperand = new BigDecimal(currentInput.toString());
        leftOperand = apply(pendingOp, leftOperand, rightOperand);

        if(state == InputState.ERROR){
            getDisplayText();
            return;
        }

        String result = formaterUtill.formatForDisplay(leftOperand, maxDigits);
        currentInput.setLength(0);
        currentInput.append(result);

        pendingOp = Operator.NONE;
        state = InputState.RESULT;
    }

    /**
    *   クリア処理
    */
    public void clearAll(){
        currentInput.setLength(0);
        leftOperand = BigDecimal.ZERO;
        pendingOp = Operator.NONE;
        state = InputState.READY;
    }

    /**
    *   演算子画面表示用
    */
    private String getOperatorText(Operator op){
        switch (op) {
            case ADD:
                return "+";
            
            case SUBTRACT:
                return "-";

            case MULTIPLY:
                return "×";

            case DIVIDE:
                return "÷";
        
            default:
                return null;
        }
    }

    /**
    *   文字列変換処理
    */
    public String getDisplayText(){
        if(state == InputState.ERROR){
            return "エラー";
        }

        //数字入力後の演算子表示→左辺、演算子
        if(state == InputState.INPUT_OPERATOR){
            return formaterUtill.formatForDisplay(leftOperand, maxDigits) + getOperatorText(pendingOp);
        }

        //演算子入力済であれば→左辺、演算子、右辺
        if(pendingOp != Operator.NONE){
            return formaterUtill.formatForDisplay(leftOperand, maxDigits) + getOperatorText(pendingOp) + currentInput.toString();
        }

        if(currentInput.length() == 0){
            return "0";
        }
        return currentInput.toString();
    }
}

