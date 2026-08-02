package calculator;
import javax.swing.*;
import java.awt.*;

public class CalculatorFrame {
    private JLabel displayLabel;
    private JPanel keypadPanel;

    public CalculatorFrame(){
        JFrame frame =new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//閉じるボタンでシステム終了。

        //ラベル部分の設定
        frame.setLayout(new BorderLayout());
        displayLabel = new JLabel("0", SwingConstants.RIGHT);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 35));
        displayLabel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        frame.add(displayLabel, BorderLayout.NORTH);

        //ボタン設定
        String[] buttonLabels = {
        "7", "8", "9", "÷",
        "4", "5", "6", "×",
        "1", "2", "3", "-",
        "0", ".", "=", "+",
        "C"
        };
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        keypadPanel = new JPanel();//ボタン部分の作成
        keypadPanel.setLayout(new GridLayout(5,4,10,10));//行、列、横、縦
        
        for(String text : buttonLabels){
            JButton button =new JButton(text);
            button.setFont(buttonFont);
            keypadPanel.add(button);
        }
        frame.add(keypadPanel, BorderLayout.CENTER);//ボタンを中央配置し表示

        frame.setSize(350, 550);
        frame.setVisible(true);
    }

    //ディスプレイ表示
    public void setDisplay(String text){
        displayLabel.setText(text);
    }

    //ボタン押下時イベント
    public void bindController(CalculatorController c){
        //ボタン部品データ呼び出し
        for(Component comp : keypadPanel.getComponents()){
            if(comp instanceof JButton button){//JButtonか判定し型変換

                //イベント設定
                button.addActionListener(e -> {
                    String text = button.getText();
                    switch(text){
                        case "C" -> c.onClear();
                        case "=" -> c.onEquals();
                        case "." -> c.onDot();
                        case "+" -> c.onOperator(Operator.ADD);
                        case "-" -> c.onOperator(Operator.SUBTRACT);
                        case "×" -> c.onOperator(Operator.MULTIPLY);
                        case "÷" -> c.onOperator(Operator.DIVIDE);
                        default -> c.onDigit(text.charAt(0));
                    }
                });
            }
        }
    }

}
