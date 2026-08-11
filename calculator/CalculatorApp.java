package calculator;

public class CalculatorApp{
    public static void main(String[]args){

        /**
        *   フレーム呼び出し
        */
        CalculatorFrame frame = new CalculatorFrame();
        
        /*
        *   モデル呼び出し
        */
        CalculatorModel mobel = new CalculatorModel();

        /**
        *   コントローラー呼び出し
        */
        CalculatorController controller = new CalculatorController(mobel, frame);

        /**
        *   コントローラーとフレームの結合
        */
        frame.bindController(controller);
    }

}