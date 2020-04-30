package view;

import javax.swing.JFrame;

import Listeners.MouseAction;
import model.Stadium;

public class HoloTV implements Runnable {
	private JFrame frame;
	private Stadium stadium;
	
	private GamePanel gamePanel;
	
	public HoloTV(Stadium stadium) {
		this.stadium = stadium;
	}
	
	@Override
	public void run() {
		// Create the main window
		this.frame = new JFrame("Stadium : Snow Kids VS Shadows !!!!");
		
		// Create the GamePanel and himself create and add an ArkadiaNews
		this.gamePanel = new GamePanel(stadium);
		
		// Add the panel to the frame
		this.frame.add(this.gamePanel);
		
		// When red X is clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Give it a default size and lets roll
        frame.setSize(800, 800);
        frame.setVisible(true);
	}
	
	public void addArkadiaNewsMouseListener(MouseAction mouseAction) {
		this.gamePanel.getArkadiaNews().addMouseListener(mouseAction);
	}
	
	public int getCaseSize() {
		return this.gamePanel.getArkadiaNews().getCaseSize();
	}
	
	public int getScreenWidth() {
		return this.frame.getWidth();
	}
	
	public int getScreenHeight() {
		return this.frame.getHeight();
	}
}
