package de.kirion.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * 
 * @author Kirion1st
 *
 */
public class ChooseTankColorDialog extends JDialog {

	private final GamePanel tankGame;
	private final Tank playersTank;
	
	private final JPanel dialogPanel;
	
	private final JButton setTurretColorButton = new JButton("Set Turret Color...");
	private final JButton setCannonColorButton = new JButton("Set Cannon Color...");
	private final JButton cancelButton = new JButton("Cancel");
	private final JButton applyButton = new JButton("Apply");
	
	private Color oldTurretColor;
	private Color oldCannonColor;
	private Color tempTurretColor;
	private Color tempCannonColor;
	
	
	public ChooseTankColorDialog(Frame frame, GamePanel game) {
		super(frame);
		setTitle("Choose your Tanks's Colors");
		setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		
		this.tankGame = game;
		playersTank = tankGame.getPlayersTank();
		
		dialogPanel = new JPanel();
		dialogPanel.add(createPreviewPanel());
		dialogPanel.add(createButtonsPanel());
		add(dialogPanel);
		
		registerButtonListeners();
		
		setPreferredSize(new Dimension(350, 330));
	}
	
	private JPanel createPreviewPanel() {
		JLabel titleLabel = new JLabel("Tank Preview"); 
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		JPanel previewPanel = new JPanel();
		previewPanel.setLayout(new BoxLayout(previewPanel, BoxLayout.Y_AXIS));
		previewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		previewPanel.add(titleLabel);
		previewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		previewPanel.add(new TankPreviewPanel(playersTank));
		
		return previewPanel;
	}
	
	private JPanel createButtonsPanel() {
		JPanel colorButtonsPanel = new JPanel();
		colorButtonsPanel.add(setTurretColorButton);
		colorButtonsPanel.add(setCannonColorButton);
		
		JPanel mainButtonsPanel = new JPanel();
		mainButtonsPanel.add(applyButton);
		mainButtonsPanel.add(cancelButton);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.add(colorButtonsPanel);
		buttonsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		buttonsPanel.add(new JSeparator());
		buttonsPanel.add(mainButtonsPanel);
		
		return buttonsPanel;
	}
	
	private void registerButtonListeners() {
		
		oldTurretColor = playersTank.getTurretColor();
		tempTurretColor = oldTurretColor;
		
		oldCannonColor = playersTank.getCannonColor();
		tempCannonColor = oldCannonColor;
		
		setTurretColorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color newTurretColor = JColorChooser.showDialog(null, "Choose Turret Color", tempTurretColor);	
				
				if (newTurretColor != null) {
					tempTurretColor = newTurretColor;
					playersTank.setTurretColor(newTurretColor);
					dialogPanel.repaint();
				}
			}
		});
		
		setCannonColorButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color newCannonColor = JColorChooser.showDialog(null, "Choose Cannon Color", tempCannonColor);	
				
				if (newCannonColor != null) {
					tempCannonColor = newCannonColor;
					playersTank.setCannonColor(newCannonColor);
					dialogPanel.repaint();
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				playersTank.setTurretColor(oldTurretColor);
				playersTank.setCannonColor(oldCannonColor);
				
				tempTurretColor = oldTurretColor;
				tempCannonColor = oldCannonColor;
				
				tankGame.continueGame();
				setVisible(false);
				}
		});
		
		applyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				oldTurretColor = playersTank.getTurretColor();
				oldCannonColor = playersTank.getCannonColor();
				
				tankGame.repaint();
				tankGame.continueGame();
				setVisible(false);
				}
		});
	}
}
