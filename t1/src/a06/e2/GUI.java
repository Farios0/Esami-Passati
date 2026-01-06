package a06.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;

public class GUI extends JFrame {
    
    private final List<JButton> cells = new ArrayList<>();
    private final Logics logic;
    
    public GUI(int size) {
        logic = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton Fire = new JButton("Fire");
        Fire.addActionListener(e -> {
            List<String> targets = logic.updateGrid();
            for (int i = 0; i < cells.size(); i++) {
                cells.get(i).setText(targets.get(i));
            }
            if (logic.isOver()) {
                Fire.setEnabled(false);
            }
        });
        main.add(BorderLayout.SOUTH, Fire);

        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.add(jb);
                panel.add(jb);
            }
        }
        List<String> t = logic.updateGrid();
        for (int i = 0; i < cells.size(); i++) {
            cells.get(i).setText(t.get(i));
        }
        this.setVisible(true);
    }    
}
