/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enac.interaction;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuideFrame extends JFrame {

	// private JLabel[][] labels;
	private JLabel labelHiro;
	private JLabel labelKanji;
	private JLabel labelKanji_pointecone;

	public GuideFrame() {
		super();
		
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(3,2));
		panel.setBackground(Color.white);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		labelHiro = new JLabel("hiro");
		labelKanji = new JLabel("kanji");
		labelKanji_pointecone = new JLabel("kanjipointe");	
		
		
		panel.add(labelHiro);
		panel.add(labelKanji);
		panel.add(labelKanji_pointecone);
		
		labelHiro.setLocation(0, 100);
		labelKanji.setLocation(0, 200);
		labelKanji_pointecone.setLocation(0, 300);		

		setContentPane(panel);
		this.setSize(500, 500);
		
	}

	public JLabel getLabelHiro() {
		return labelHiro;
	}

	public void setLabelHiro(String text) {
		this.labelHiro.setText(text);
	}

	public JLabel getLabelKanji() {
		return labelKanji;
	}

	public void setCommand(String text) {
		this.labelKanji.setText(text);
	}

	public JLabel getLabelKanji_pointecone() {
		return labelKanji_pointecone;
	}

	public void setLabelKanji_pointecone(String text) {
		this.labelKanji_pointecone.setText(text);
	}
}

