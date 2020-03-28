package de.kirion.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * 
 * @author Kirion1st
 *
 */
public class TankPreviewPanel extends JPanel{
	
	private final Tank playersTank;
	
	private final int panelWidth;
	private final int panelHeight;
	private final int tankWidth;
	private final int tankHeight;
	
	public TankPreviewPanel(Tank tank) {
		
		this.playersTank = tank;
		
		tankWidth = (int) playersTank.getWidth();
		tankHeight = (int) playersTank.getHeight();
		
		panelWidth = tankWidth * 4;
		panelHeight = tankHeight * 3;
		
		setPreferredSize(new Dimension(panelWidth, panelHeight));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, panelWidth - 1, panelHeight - 1);
		
		g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, panelWidth - 1, panelHeight - 1);
			
		int tankPosX = (int) playersTank.getObjectPosition().getX();
		int tankPosY = (int) playersTank.getObjectPosition().getY();
			
		g.translate(panelWidth  / 2 - tankWidth / 2 - tankPosX, panelHeight / 2 - tankHeight / 2 - tankPosY);
		playersTank.setPaintTankStatusBar(false);
		playersTank.paintMe(g);
		playersTank.setPaintTankStatusBar(true);
	}
}
