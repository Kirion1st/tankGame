package de.kirion.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The game itself
 * 
 * @author Kirion1st
 *
 */
public class GamePanel extends JPanel {
		
	public static final String IMAGE_DIR = "images/";
	
	private final Dimension prefSize = new Dimension(1180, 780);
	
	private ImageIcon backgroundImage;
	private final String[] backgroundImages = new String[] {"bg_mud.jpg", "bg_snow.jpg", "bg_sand.jpg"};
	
	private boolean gameOver = false;
	private int tanksDestroyedCounter = 0; 
	
	private Timer t;
	
	private Tank playersTank = null;
	private EnemyTank enemyTank = null;
	
	private List<Missile> missiles;
	
	/**
	 * Constructor
	 */
	public GamePanel() {
		setFocusable(true);
		setPreferredSize(prefSize);
		
		initGame();
		startGame();
	}
	
	
	public Tank getPlayersTank() {
		return playersTank;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	/**
	 * Initialize the game
	 */
	private void initGame() {
		setBackgroundImage(1);
		createGameObjects();
		
		initPlayersTank();
		
		t = new Timer(20, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				doOnTick();
				
			}
		});
		
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				switch(e.getKeyCode()) {
					case VK_SPACE:
						if(playersTank.isAbleToShoot()) {
							missiles.add(playersTank.shoot());
						}
						break;
						
					case VK_DOWN:
					case VK_UP:
						playersTank.stopTank();
						break;
					
					case VK_LEFT:
					case VK_RIGHT: 
						playersTank.stopTurningTank();
						break;
					
					case VK_W: 
					case VK_E: 
						playersTank.stopTurningCannon();
						break;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			
				switch (e.getKeyCode()) {
					case VK_LEFT: 
						playersTank.turnTankLeft();
						break;
					case VK_RIGHT: 
						playersTank.turnTankRight();
						break;
						
					case VK_UP:
						playersTank.accelerateTank();
						break;
					case VK_DOWN: 
						playersTank.decelerateTank();
						break;
						
					case VK_W: 
						playersTank.turnCannonLeft();
						break;
					case VK_E:
						playersTank.turnCannonRight();
						break;
					}
			}
		});
	}
	
	private void createGameObjects() {
		if (playersTank == null) {
			playersTank = new Tank(new Coordinate(900, 150), 70, 45, Math.toRadians(180), 0);
		}
		initPlayersTank();
		
		missiles = new LinkedList<>();
		enemyTank = new EnemyTank(new Coordinate(40, 600), 80, 50, Math.toRadians(-20), 0, playersTank);
	}
	
	private void initPlayersTank() {
		playersTank.setObjectPosition(new Coordinate(900, 150));
		playersTank.setMovingAngle(Math.toRadians(180));
		playersTank.setAngleCannon(0);
		playersTank.setEnergy(10);
	}
	
	/**
	 * sets an Image as Background
	 * 
	 * @param imageNumber Number of image
	 */
	public void setBackgroundImage(int imageNumber) {
		String imagePath = IMAGE_DIR + backgroundImages[imageNumber];
		URL imageURL = getClass().getResource(imagePath);
		backgroundImage = new ImageIcon(imageURL);
	}
	
	private void startGame() {
		t.start();
	}
	
	public void pauseGame() {
		t.stop();
	}
	
	public void continueGame() {
		if (!isGameOver()) t.start();
	}
	
	public void restartGame() {
		tanksDestroyedCounter = 0;
		setGameOver(false);
		createGameObjects();
		startGame();
	}
	
	private void endGame() {
		setGameOver(true);
		pauseGame();
	}
	
	/**
	 * This method is running every tick and calculates all movements and hits of Tanks and Missiles
	 */
	private void doOnTick() {
		for (Iterator<Missile> itMissiles = missiles.iterator(); itMissiles.hasNext();) {
			Missile missile = itMissiles.next();
			missile.makeMove();
			if (missile.getRange() <= 0) itMissiles.remove();
			
			if (playersTank.touches(missile) && missile.getRange() > 1) {
				if(playersTank.getEnergy() > 1) {
					playersTank.setEnergy(playersTank.getEnergy() - 1);
				}
				else {
					playersTank.setEnergy(0);
					endGame();
				}
				itMissiles.remove();
			}
			
			if (enemyTank.touches(missile) && missile.getRange() > 1) {
				if(enemyTank.getEnergy() > 1) {
					enemyTank.setEnergy(enemyTank.getEnergy() - 1);
				}
				else {
					double xStart = Math.random() * prefSize.width / 3;
					double yStart = prefSize.height * 1.05;
					double enemyWidth = 70 + Math.random() * 15;
					double enemyHeight = 40 + Math.random() * 15;
					double angleStart = -80 + Math.random() * 60;
					
					enemyTank = new EnemyTank(new Coordinate(xStart, yStart), enemyWidth, enemyHeight, Math.toRadians(angleStart), 0, playersTank);
					tanksDestroyedCounter++;
				}
				itMissiles.remove();
			}
		}
		
		playersTank.makeMove();
		
		enemyTank.makeMove();
		if (enemyTank.isTargetLocked() && enemyTank.isAbleToShoot()) missiles.add(enemyTank.shoot());
		
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		backgroundImage.paintIcon(null, g, 0, 0);
		
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 19));
		g.setColor(Color.BLUE);
		g.drawString("Tanks destroyed: " + tanksDestroyedCounter, 22, prefSize.height - 5);
		
		for (Missile missile:missiles) {
			missile.paintMe(g);
		}
		
		playersTank.paintMe(g);
		enemyTank.paintMe(g);
		
		if (isGameOver()) {
			
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
			g.setColor(Color.RED);
			g.drawString("GAME OVER!", prefSize.width / 2 -130, prefSize.height / 5);
		}
	}	
}
