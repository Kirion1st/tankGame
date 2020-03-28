package de.kirion.game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
 
/**
 * Main class for the Game Window
 * 
 * @author Kirion1st
 *
 */
public class GameWindow extends JFrame{
	
	private final GamePanel tankGamePanel;
	private final ChooseTankColorDialog chooseTankColorDialog;
     
    public GameWindow() {       
         
        this.tankGamePanel = new GamePanel();
        chooseTankColorDialog = new ChooseTankColorDialog(this, tankGamePanel);
                 
        registerWindowListener();
        createMenu();
        
        add(tankGamePanel);
        pack();
         
        setTitle("PanzerHQ");
        setLocation(10, 10);
        setResizable(false);
         
        setVisible(true);
    }
    
    /**
     * registers a WindowListener
     */
    private void registerWindowListener() {
    	
    	addWindowListener(new WindowAdapter() {
    		
    		@Override
    		public void windowClosing(WindowEvent e) {
    			System.exit(0);
    		}
    		
    		@Override
    		public void windowDeactivated(WindowEvent e) {
    			
    			tankGamePanel.pauseGame();
    		}
    		
    		@Override
    		public void windowActivated(WindowEvent e) {

    			tankGamePanel.continueGame();
    		}
		});
    }
    
    /**
     * Creates a Menubar
     */
    private void createMenu() {
    	
    	JMenuBar menuBar = new JMenuBar();
    	this.setJMenuBar(menuBar);
    	
    	JMenu fileMenu = new JMenu("File");
    	JMenu gameMenu = new JMenu("Game");
    	JMenu prefMenu = new JMenu("Preferences");
    	
    	menuBar.add(fileMenu);
    	menuBar.add(gameMenu);
    	menuBar.add(prefMenu);
    	
    	addFileMenuItems(fileMenu);
    	addGameMenuItems(gameMenu);
    	addPrefMenuItems(prefMenu);
    }
    
    /**
     * Adds the 'quit' item to a Menu
     * 
     * @param fileMenu the Menubar on which the item is added
     */
    private void addFileMenuItems(JMenu fileMenu) {
    	
    	JMenuItem quitItem = new JMenuItem("Quit");
    	fileMenu.add(quitItem);
    	quitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);		
			}
		});
    }
    
    /**
     * Adds three items to the Game Menu
     * 
     * @param gameMenu the Menubar on which the item is added
     */
    private void addGameMenuItems(JMenu gameMenu) {
    	
    	JMenuItem pauseItem = new JMenuItem("Pause");
    	gameMenu.add(pauseItem);
    	pauseItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tankGamePanel.pauseGame();
			}
		});
    	
    	JMenuItem continueItem = new JMenuItem("Continue");
    	gameMenu.add(continueItem);
    	continueItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tankGamePanel.continueGame();
			}
		});
    	
    	gameMenu.addSeparator();
    	
    	JMenuItem restartItem = new JMenuItem("Restart");
    	gameMenu.add(restartItem);
    	restartItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tankGamePanel.restartGame();
			}
		});
    }
    
    /**
     * Adds items to the Preferences Menu
     * 
     * @param prefMenu the Menubar on which the items are added
     */
    private void addPrefMenuItems(JMenu prefMenu) {
    	
    	JMenuItem changeColorItem = new JMenuItem("Change Tank's Colors...");
    	prefMenu.add(changeColorItem);
    	
    	changeColorItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tankGamePanel.pauseGame();
				chooseTankColorDialog.pack();
				chooseTankColorDialog.setLocation(300, 200);
				chooseTankColorDialog.setVisible(true);
			}
		});
    	
    	JMenu submenu = new JMenu("Choose Background");
    	prefMenu.add(submenu);
    	
    	JMenuItem menuItem = new JMenuItem("Muddy Waters");
    	submenu.add(menuItem);
    	menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tankGamePanel.setBackgroundImage(0);
				repaint();
			}
		});
    	
    	menuItem = new JMenuItem("Snow Area");
    	submenu.add(menuItem);
    	menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tankGamePanel.setBackgroundImage(1);
				repaint();
			}
		});
    	
    	menuItem = new JMenuItem("Sandy Falls");
    	submenu.add(menuItem);
    	menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tankGamePanel.setBackgroundImage(2);
				repaint();
			}
		});
    }
}
