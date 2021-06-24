import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class buyAllList {
    private JTextArea thisIsATestTextArea;
    private JPanel panel1;
    private JLabel label;
    private JButton clipBoard;


    public buyAllList(nameNumber[] arry) {

        StringBuilder sb = new StringBuilder();

        arry = this.trimArray(arry);

        for(int i = 0; arry.length>i; i++){
            sb.append(arry[i].getName());
            sb.append(" ");
            sb.append(arry[i].getAmount());
            sb.append("\n");
        }

        thisIsATestTextArea.setText(sb.toString());
        JFrame frame = new JFrame("Buy List");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        clipBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection stringSelection = new StringSelection(sb.toString());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
    }

    public nameNumber[] trimArray(nameNumber[] inarry){
        int i =0;
        while(inarry[i] != null){
            i++;
        }
        nameNumber[] r =new nameNumber[i];

        for(int n =0; n< i;n++){
            r[n] = inarry[n];
        }
        return r;

    }

}