/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enac.interaction;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuideFrame extends JFrame {
        
	// private JLabel[][] labels;
	private JLabel lbPres;
	private JLabel lbForme;
	private JLabel lbClickColor;
        private JLabel lbInstruc1;

    public JLabel getLbInstruc1() {
        return lbInstruc1;
    }

    public void setLbInstruc1(String text) {
        this.lbInstruc1.setText(text);
    }

        private PropertyChangeSupport changes = new PropertyChangeSupport(this);
        
        
        public void addPropertyChangeListener(PropertyChangeListener l)
        {changes.addPropertyChangeListener(l);}
        public void removePropertyChangeListener(PropertyChangeListener l)
        {changes.removePropertyChangeListener(l);}
        
	public GuideFrame() {
		super();
		
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(4,1));
		panel.setBackground(Color.white);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lbPres = new JLabel("Le moteur de fusion est pret, commencer par spécifier la forme avec ICar");
		lbForme = new JLabel();
		lbClickColor = new JLabel("");
                lbInstruc1=new JLabel();
		
		
		panel.add(lbPres);
                panel.add(lbForme);
		panel.add(lbInstruc1);

		panel.add(lbClickColor);
		
		
		setContentPane(panel);
		this.setSize(500, 500);
		
	}

	public JLabel getLabelHiro() {
		return lbPres;
	}

	public void setlbPres(String text) {
		this.lbPres.setText(text);
	}

	public JLabel getlbForme() {
		return lbForme;
	}

	public void setForme(String text) {
            this.lbForme.setText(text);
	}

	public JLabel getlbClickColor() {
		return lbClickColor;
	}

	public void setlbClickColor(String text) {
		this.lbClickColor.setText(text);
	}
}

