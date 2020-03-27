package de.kirion.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.RepaintManager;
import javax.swing.Timer;

/**
 * The game itself
 * 
 * @author Kirion1st
 *
 */
public class GamePanel extends JPanel {
	
	private Missile testMissileOne;
	private Missile testMissileTwo;
	
	private Tank testTank;
	
	public static final String IMAGE_DIR = "images/";
	
	private final Dimension prefSize = new Dimension(1180, 780);
	
	private ImageIcon backgroundImage;
	private final String[] backgroundImages = new String[] {"bg_mud.jpg", "bg_snow.jpg", "bg_sand.jpg"};
	
	private boolean gameOver = false;
	private int tanksDestroyedCounter = 0; 
	
	private Timer t;
	
	/**
	 * Constructor
	 */
	public GamePanel() {
		setFocusable(true);
		setPreferredSize(prefSize);
		
		initGame();
		startGame();
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
	}
	
	private void createGameObjects() {
		testMissileOne = new Missile(new Coordinate(200, 100), 9, Math.toRadians(45), 5);
		testMissileTwo = new Missile(new Coordinate(200, 609), 9, Math.toRadians(-45), 5);
	}
	
	private void initPlayersTank() {
		testTank = new Tank(new Coordinate(360, 260), 70, 45, Math.toRadians(270), 0);
		testTank.accelerateTank();
		testTank.turnTankLeft();
		testTank.turnCannonRight();
		testMissileOne = testTank.shoot();
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
	 * method is running every tick
	 */
	private void doOnTick() {
		tanksDestroyedCounter++;
		if (tanksDestroyedCounter > 2015) endGame();
		
		testMissileOne.makeMove();
		testMissileTwo.makeMove();
		
		testTank.makeMove();
		if (testTank.touches(testMissileTwo)) endGame();
		if (testTank.isAbleToShoot()) testMissileOne = testTank.shoot();
		
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
		
		if (isGameOver()) {
			
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
			g.setColor(Color.RED);
			g.drawString("GAME OVER!", prefSize.width / 2 -130, prefSize.height / 5);
		}
		
		testTank.paintMe(g);
		
		testMissileOne.paintMe(g);
		testMissileTwo.paintMe(g);
	}
}
