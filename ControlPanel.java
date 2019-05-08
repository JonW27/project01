package projectview;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ControlPanel {
	private Mediator mediator;
	private JButton stepButton = new JButton("Step");
	private JButton clearButton = new JButton("Clear");
	private JButton runButton = new JButton("Run/Pause");
	private JButton reloadButton = new JButton("Reload");
	
	public ControlPanel( Mediator mediator ) { 
		this.mediator = mediator ;
	}
	
	public JComponent createControlDisplay() {
		JPanel panel = new JPanel() ;
		panel.setLayout( new GridLayout( 1, 0 )) ;
		
		stepButton.setBackground(Color.WHITE);
		stepButton.addActionListener(e -> mediator.step());
		panel.add(stepButton);
	}
	
}
