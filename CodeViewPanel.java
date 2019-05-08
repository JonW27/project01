package projectview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class CodeViewPanel {
	private Machine machine ;
	private Instruction instr ;
	private JScrollPane scroller ;
	private JTextField[] codeText = new JTextField[ Memory.CODE_SIZE ];
	private JTextField[] codeBinHex = new JTextField[ Memory.CODE_SIZE ] ;
	int previousColor = -1 ;
	
	public CodeViewPanel( Machine m ) {
		machine = m ;
	}
	
	public JComponent createCodeDisplay() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		Border border = BorderFactory.createTitledBorder(
		        BorderFactory.createLineBorder(Color.BLACK),
		        "Code Memory View ["+ 0 +"-"+ Memory.CODE_SIZE +"]", 	// ???
		        TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
		panel.setBorder(border);
		
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		JPanel numPanel = new JPanel();
		JPanel decimalPanel = new JPanel();
		JPanel hexPanel = new JPanel();
		JPanel textPanel = new JPanel() ;
		
		numPanel.setLayout(new GridLayout(0,1));
		decimalPanel.setLayout(new GridLayout(0,1));
		hexPanel.setLayout(new GridLayout(0,1));
		
		innerPanel.add(numPanel, BorderLayout.LINE_START); 
		innerPanel.add(textPanel, BorderLayout.CENTER); 
		innerPanel.add(hexPanel, BorderLayout.LINE_END);
		
		for(int i = 0; i < Memory.CODE_SIZE ; i++) {
			numPanel.add(new JLabel(i+": ", JLabel.RIGHT));
			codeText[i ] = new JTextField(10);
			codeBinHex[ i ] = new JTextField(12);
			textPanel.add( codeText[ i ] ); 
			hexPanel.add( codeBinHex[ i ] );
		}
		
		scroller =new JScrollPane(innerPanel);
		panel.add(scroller);
		return panel;
	}
	
}
