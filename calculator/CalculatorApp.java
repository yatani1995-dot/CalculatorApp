package calculator;

public class CalculatorApp{
    public static void main(String[]args){

        //フレーム呼び出し
        CalculatorFrame frame = new CalculatorFrame();
        //
        CalculatorModel mobel = new CalculatorModel();
        //
        CalculatorController controller = new CalculatorController(mobel, frame);

        frame.bindController(controller);
    }

}