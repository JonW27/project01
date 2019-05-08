package projectview;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;

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
		
		clearButton.addActionListener(e -> mediator.clear());
		runButton.addActionListener(e -> mediator.toggleAutoStep());
		reloadButton.addActionListener(e -> mediator.reload());
		
		JSlider slider = new JSlider(5,1000);
		slider.addChangeListener(e -> mediator.setPeriod(slider.getValue())); 
		// put a void method setPeriod(int value) in Mediator, we will complete it later
		panel.add(slider);
		
		return panel;
	}
	public void update() {
		
	}
	
}
