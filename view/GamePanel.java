package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import model.Stadium;
import model.enums.ActionType;

import patterns.Observable;
import patterns.Observer;

public class GamePanel extends JPanel implements Observable {
	private Stadium stadium;	
	private ArkadiaNews arkadiaNews;
	private JPanel gameControlPanel;
	
	private JLabel nbTurn;
	private JLabel whosturn;
	private JLabel nbPassRemaining;
	private JLabel nbMoveRemaining;
	private JButton endTurnButton;
	
	public GamePanel(Stadium stadium) {
		super(new BorderLayout());
		this.stadium = stadium;
		this.arkadiaNews = new ArkadiaNews(stadium);
		this.add(this.arkadiaNews, BorderLayout.CENTER);
		
		this.setMargin();
		
		this.gameControlPanel = new JPanel(new GridLayout(5, 0));
		
		
		this.nbTurn = new JLabel("Tour " + (stadium.getTurnIndex() + 1) + " :");
		this.gameControlPanel.add(this.nbTurn);
		
		this.whosturn = new JLabel("Joueur " + (getNbTeam() + 1) + ", � toi !");
		this.gameControlPanel.add(this.whosturn);
		
		this.nbPassRemaining = new JLabel("Passe : " + (1 - stadium.getNbPassesDone()));
		this.gameControlPanel.add(this.nbPassRemaining);
		
		this.nbMoveRemaining = new JLabel("D�placements : " + (2 - stadium.getNbMovesDone()));
		this.gameControlPanel.add(this.nbMoveRemaining);
		
		this.endTurnButton = new JButton("Fin du tour !");
		this.gameControlPanel.add(this.endTurnButton);
		endTurnButton.addActionListener(actionEvent -> notify(ActionType.END_TURN));
		
		this.add(this.gameControlPanel, BorderLayout.EAST);
	}
	
	public ArkadiaNews getArkadiaNews() {
		return this.arkadiaNews;
	}
	
	public void updateGamePanelInfos() {
		this.nbTurn.setText("Tour " + (stadium.getTurnIndex() + 1) + " :");
		this.whosturn.setText("Joueur " + (getNbTeam() + 1) + ", � toi !");
		this.nbPassRemaining.setText("Passe : " + (1 - stadium.getNbPassesDone()));
		this.nbMoveRemaining.setText("D�placements : " + (2 - stadium.getNbMovesDone()));
	}

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void notify(Object object) {
		for(Observer observer : observers) {
			observer.update(object);
		}
	}
	
	private int getNbTeam() {
		return this.stadium.getTurnIndex() % 2;
	}
	
	public void showEndGamePopUp(String teamName) {
		int input = JOptionPane.showOptionDialog(null, "The team \"" + teamName + "\" won the game!", "Game over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		
		if (input == JOptionPane.OK_OPTION || input == JOptionPane.CANCEL_OPTION || input == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
	}
	
	public void showAntiPlayPopUp(String teamName) {
		int input = JOptionPane.showOptionDialog(null, "The enemy team made an antiplay: The team \"" + teamName + "\" won the game!", "Game over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		
		if (input == JOptionPane.OK_OPTION || input == JOptionPane.CANCEL_OPTION || input == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
	}
	
	private void setMargin() {
		Border border = this.arkadiaNews.getBorder();
		Border borderMargin = new EmptyBorder(new Insets(this.getWidth()/5, this.getWidth()/5, this.getWidth()/5, 0));
		this.arkadiaNews.setBorder(border == null ? borderMargin : new CompoundBorder(borderMargin, border));
	}
}
