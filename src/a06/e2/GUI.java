package a06.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    private final Logics logic;
    private final Map<Pair<Integer, Integer>, JButton> cells = new HashMap<>();
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        logic = new LogicsImpl(size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton fire = new JButton("Fire");
        main.add(BorderLayout.SOUTH, fire);
        fire.addActionListener(e -> {
            logic.updateCells();
            logic.getStream().forEach(p -> cells.get(p.getX()).setText(p.getY()));
        });
        
                
        for (int i=0; i<size; i++){
            for (int j=0; j < size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Pair<Integer,Integer>(j, i), jb);
                panel.add(jb);
            }
        }
        this.setVisible(true);

        logic.getStream().forEach(p -> cells.get(p.getX()).setText(p.getY()));
    }    
}
