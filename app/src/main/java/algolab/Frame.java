package algolab;

import javax.swing.JFrame;

public class Frame extends JFrame {

    public Frame() {
        setTitle("AlgoLab");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        
        repaint();
    }

}
