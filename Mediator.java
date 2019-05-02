package projectview;

import javax.swing.JFrame;

import project.Machine;

public class Mediator {
	
	private Machine machine;
	private JFrame frame;
	
	public void step() {
		
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
}
