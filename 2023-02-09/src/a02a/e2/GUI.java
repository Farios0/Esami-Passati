package a02a.e2;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<Pair<Integer, Integer>, JButton> cells = new HashMap<>();
    private final Logics logic;
    
    public GUI(int size) {
        logic = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.entrySet().stream().filter(i -> i.getValue().equals(button)).map(Map.Entry::getKey).findFirst().get();
                cells.get(position).setText("B");
                for (var pos : logic.getBCells(position)) {
                    cells.get(pos).setEnabled(false);
                }
                if (logic.isItOver()) {
                    cells.entrySet().stream().map(Map.Entry::getValue).forEach(i -> {
                        i.setEnabled(true);
                        i.setText("");
                    });
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(new Pair<>(i, j), jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    
}
