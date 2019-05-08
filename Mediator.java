package projectview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;


import project.Machine;

public class Mediator {
	
	private Machine machine;
	private JFrame frame;
	private TimerUnit tUnit;
	private CodeViewPanel codeViewPanel;
	private MemoryViewPanel memoryViewPanel1;
	private MemoryViewPanel memoryViewPanel2;
	private MemoryViewPanel memoryViewPanel3;
	private ControlPanel controlPanel;
	private ProcessorViewPanel processorPanel;
	private States currentState = States.NOTHING_LOADED;
	
	public States getCurrentState() {
		return currentState;
	}

	
	public void setCurrentState(States currentState) {
		this.currentState = currentState;
	}


	public void step() {
		
	}
	
	public void clear() {
		
	}
	
	public void toggleAutoStep() {
		
	}
	
	public void reload() {
		
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setPeriod(int value) {
		
	}
	
	private void notify(String str) {
		codeViewPanel.update(str);
		memoryViewPanel1.update(str);
		memoryViewPanel2.update(str);
		memoryViewPanel3.update(str);
		controlPanel.update();
		processorPanel.update();
	}
	
	private void createAndShowGUI() {
		tUnit = new TimerUnit(this);
		//ioUnit = new IOUnit(this);
		//ioUnit.initialize();
		codeViewPanel = new CodeViewPanel(machine);
		memoryViewPanel1 = new MemoryViewPanel(machine, 0, 160);
		memoryViewPanel2 = new MemoryViewPanel(machine, 160, Memory.DATA_SIZE/2);
		memoryViewPanel3 = new MemoryViewPanel(machine, Memory.DATA_SIZE/2, Memory.DATA_SIZE);
		controlPanel = new ControlPanel(this);
		processorPanel = new ProcessorViewPanel(machine);
		//menuBuilder = new MenuBarBuilder(this);
		frame = new JFrame("Simulator");
		//JMenuBar bar = new JMenuBar();
		//frame.setJMenuBar(bar);
		//bar.add(menuBuilder.createFileMenu());
		//bar.add(menuBuilder.createExecuteMenu());

		Container content = frame.getContentPane(); 
		content.setLayout(new BorderLayout(1,1));
		content.setBackground(Color.BLACK);
		frame.setSize(1200,600);
		frame.add(codeViewPanel.createCodeDisplay(), BorderLayout.LINE_START);
		frame.add(processorPanel.createProcessorDisplay(),BorderLayout.PAGE_START);
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1,3));
		center.add(memoryViewPanel1.createMemoryDisplay());
		center.add(memoryViewPanel2.createMemoryDisplay());
		center.add(memoryViewPanel3.createMemoryDisplay());
		frame.add(center, BorderLayout.CENTER);
		frame.add(controlPanel.createControlDisplay(), BorderLayout.PAGE_END);
		// the next line will be commented or deleted later
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//frame.addWindowListener(WindowListenerFactory.windowClosingFactory(e -> exit()));
		frame.setLocationRelativeTo(null);
		tUnit.start();
		//currentState().enter();
		frame.setVisible(true);
		notify("");
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Mediator mediator = new Mediator();
				Machine machine = 
					new Machine(() -> 
					mediator.setCurrentState(States.PROGRAM_HALTED));
				mediator.setMachine(machine); //<<<<<CORRECTION
				mediator.createAndShowGUI();
			}
		});
	}
}
