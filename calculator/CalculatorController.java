package calculator;
public class CalculatorController {
    private CalculatorModel model;
    private CalculatorFrame view;

    public CalculatorController(CalculatorModel model, CalculatorFrame view) {
        this.model = model;
        this.view = view;
    }
    
    /**
    *   数字ボタンの受け入れ
    */
    public void onDigit(char ch){
        model.appendDigit(ch);
        view.setDisplay(model.getDisplayText());
    }

    /**
    *   小数点の受け入れ
    */
    public void onDot(){
        model.appendDot();
        view.setDisplay(model.getDisplayText());

    }

    /**
    *   演算子の受け入れ
    */
    public void onOperator(Operator op){
        model.inputOperator(op);
        view.setDisplay(model.getDisplayText());
    }

    /**
    *   =の受け入れ
    */
    public void onEquals(){
        model.equalsOp();
        view.setDisplay(model.getDisplayText());
    }

    /**
    *   Cの受け入れ
    */
    public void onClear(){
        model.clearAll();
        view.setDisplay(model.getDisplayText());
    }

}
