package Controller;

import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Model.Boulder;
import Model.Bubble;
import Model.Enemy;
import Model.GameObject;
import Model.LevelComponent;
import Model.LevelMap;
import Model.Mighta;
import Model.MovableObject;
import Model.Player;
import Model.PlayerObserver;
import Model.PowerUp;
import Model.User;
import Model.UserDatabase;
import Model.Utility;
import Model.Wall;
import View.GameOverPanel;
import View.GamePanel;
import View.MainFrame;
import View.MenuPanel;
import View.PausePanel;
import View.RankPanel;
import View.RegisterPanel;
import View.ScrollLogin;
import View.StartPanel;
import View.VictoryPanel;

/**
 * GameController is the main class that manages the game flow, including user interactions,
 * level transitions, game states, and player actions.
 * It follows the Singleton pattern to ensure a single instance of the controller.
 */

public class GameController implements Runnable,PlayerObserver{
   	
	/**
     * Enumeration representing the different states the game can be in.
     */
	public enum GameState {
		START_STATE,MENU_STATE,REGISTER_STATE,LOGIN_STATE,RUNNING,PAUSE,GAME_OVER,VICTORY,RANK;
	}

	
    // Singleton instance of GameController
    private static GameController instance;
    private AudioManager audioManager;
    // Handler for key events
    private KeyHandler keyHandler;
    
    // User-related fields
    private UserDatabase db;
    private User currentUser;
    private File file;
    private Timer levelChangeTimer;
    private boolean levelChangeScheduled;
    // UI components
    private MainFrame frame;
    private StartPanel startPanel;
    private MenuPanel menuPanel;
    private RegisterPanel registerPanel;
    private ScrollLogin loginPanel;
    private GamePanel gamePanel;
    private PausePanel pausePanel;
    private GameOverPanel gameOver;
    private VictoryPanel victory;
    private RankPanel rank;
    
    // Game settings and state
    private final int FPS = 30;
    private GameState state;
    private boolean running;
    private final int MAX_LEVEL = 8;
    
    // Game progress and objects
    private int currentLevel;
    private int points;
    private Player player;
    private List<Wall> walls;
    private List<Enemy> enemies;
    private List<GameObject> objects;
    
    /**
     * Returns the singleton instance of the GameController.
     * Initializes the KeyHandler with the GameController instance.
     *
     * @return the singleton instance of GameController
     */
    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        instance.keyHandler.setGameController(instance);
        return instance;
    }
    
    /**
     * Private constructor for initializing the game controller.
     * Loads user data from the UserDatabase file and sets up initial game panels.
     */
    private GameController() {
        // Initialize the user database and load user data from file
        try {
            file = new File("res/UserDatabase.txt");
            if (!file.exists()) {
                file.createNewFile(); // Create a new file if it does not exist
            }
            db = UserDatabase.getInstance();
            db.loadUsersData(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        levelChangeTimer = new Timer();
        levelChangeScheduled = false;
        // Initialize key handler and UI components
        keyHandler = KeyHandler.getInstance();
        frame = new MainFrame();

        initializePanels();

        // Show the start panel and set initial game state
        frame.show(startPanel.getName());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        state = GameState.START_STATE;
        
        audioManager = AudioManager.getInstance();
        audioManager.play("StartMusic");
    }
    
    /**
     * Initializes and sets up all game panels including the start panel, menu, login, etc.
     */
    private void initializePanels() {
        menuPanel = new MenuPanel();
        menuPanel.addKeyListener(keyHandler);
        frame.addContainer(menuPanel, menuPanel.getName());

        registerPanel = new RegisterPanel();
        registerPanel.addKeyListener(keyHandler);
        frame.addContainer(registerPanel, registerPanel.getName());

        loginPanel = new ScrollLogin(db.getUsers());
        loginPanel.addKeyListener(keyHandler);
        frame.addContainer(loginPanel, loginPanel.getName());

        startPanel = new StartPanel();
        startPanel.addKeyListener(keyHandler);  // Add KeyListener to startPanel
        frame.addContainer(startPanel, startPanel.getName());
        
        pausePanel = new PausePanel();
        pausePanel.addKeyListener(keyHandler);
        frame.addContainer(pausePanel, pausePanel.getName());
        
        gameOver = new GameOverPanel();
        gameOver.addKeyListener(keyHandler);
        frame.addContainer(gameOver, gameOver.getName());
        
        victory = new VictoryPanel();
        victory.addKeyListener(keyHandler);
        frame.addContainer(victory, victory.getName());
        
        rank = new RankPanel(db.getUsers());
        rank.addKeyListener(keyHandler);
        frame.addContainer(rank, rank.getName());
    }

    /**
     * Gets the current user
     * @return currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Gets the current played level
     * @return currentLevel
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    /**
     * Gets the current game's point
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets all the game components (player,walls,enemies and other game objects)
     * @return list of all components
     */
    public List<GameObject> getComponents() {
        return Stream.of(
                Stream.of(player),
                walls.stream(),
                enemies.stream(),
                objects.stream()
        		)
            .flatMap(stream -> stream) // Flatten the streams
            .collect(Collectors.toList()); // Collect into a single list
    }

    /**
     * Gets the menu panel
     * @return menuPanel
     */
    public MenuPanel getMenuPanel() {
        return menuPanel;
    }
    
    /**
     * Gets the registration panel
     * @return registerPanel 
     */
    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }
    
    /**
     * Gets the login panel
     * @return loginPanel the scroll panel
     */
    public ScrollLogin getLoginPanel() {
        return loginPanel;
    }
    
    /**
     * Gets the current state of the game
     * @return state current game's state
     */
    public GameState getState() {
        return state;
    }
    
    /**
     * Sets the current user that's playing
     * @param user the user playing
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    /**
     * Retrieves the level component for a given level.
     *
     * @param level the level number
     * @return the LevelComponent for the specified level
     */
    private LevelComponent getLevelComponent(int level) {
    	switch(level) {
    		case 1:
    			return LevelMap.LEVEL_ONE.startingObjects();
    		case 2:
    			return LevelMap.LEVEL_TWO.startingObjects();
    		case 3:
    			return LevelMap.LEVEL_THREE.startingObjects();
    		case 4:
    			return LevelMap.LEVEL_FOUR.startingObjects();
    		case 5:
    			return LevelMap.LEVEL_FIVE.startingObjects();
    		case 6:
    			return LevelMap.LEVEL_SIX.startingObjects();
    		case 7:
    			return LevelMap.LEVEL_SEVEN.startingObjects();
    		case 8:
    			return LevelMap.LEVEL_EIGHT.startingObjects();
    		default:
    			throw new IllegalArgumentException("Invalid level: "+currentLevel);
    	}
    }
    
    /**
     * Starts the game by initializing the player, current level, and game objects.
     * Sets the state to RUNNING and plays background music.
     */
    public void startGame() {
        player = new Player();
        player.addPlayerObserver(instance);
        currentLevel = 1;
        LevelComponent c = getLevelComponent(currentLevel);
        walls = c.getWalls();
        enemies = c.getEnemies();
        objects = new ArrayList<GameObject>();
        
        points = 0;
        
        gamePanel = new GamePanel(this, player);
        player.addObserver(gamePanel.getPlayer());
        gamePanel.addKeyListener(keyHandler);
        frame.addContainer(gamePanel, gamePanel.getName());
        state = GameState.RUNNING;
        
        audioManager.playBackgroundMusic("GameMusic");
    }
    
    /**
     * Changes the current level in the game. If the maximum level is reached, 
     * transitions to the victory state.
     */
    public void changeLevel() {
    	currentLevel++;
    	if(currentLevel > MAX_LEVEL) {
    		audioManager.stopBackgroundMusic();
    		changePanel(GameState.VICTORY);
    		currentUser.incrementWonGames();
    		if(currentUser.getHighestScore() < points) {
    			currentUser.setHighestScore(points);
    		}
    		try {
				db.saveUsersData(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}else {
    		try {
        		repositionPlayer(20, Utility.HEIGHT - 40);
            	LevelComponent c = getLevelComponent(currentLevel);
            	walls = c.getWalls();
            	enemies = c.getEnemies();
            	objects = new ArrayList<GameObject>();
            	gamePanel.updateComponents();
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    	}
    }
    
    /**
     * Changes the game state to PAUSE if it was RUNNING
     */
    public void pause() {
        if (state == GameState.RUNNING) {
            state = GameState.PAUSE;
        }
    }

    /**
     * Changes the game state to RUNNING if it was PAUSE
     */
    public void resume() {
        if (state == GameState.PAUSE) {
            state = GameState.RUNNING;
        }
    }
    
    /**
     * Adds a new user to the list of user in the database
     * @param nickname of the new user
     * @param avatar of the new user
     */
    public void addUser(String nickname, String avatar) {
        setCurrentUser(new User(nickname, avatar));
        db.addUser(currentUser);
        try {
            db.saveUsersData(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Game loop logic
     */
    @Override
    public void run() {
        running = true;
        double timePerFrame = 1000000000.0 / FPS;
        long lastFrame = System.nanoTime();
        long now;
        
        int frames = 0;
        long lastCheck = System.currentTimeMillis();
        
        while (running) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                update();
                frame.repaint();
                lastFrame = now;
                frames++;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }
    
    /**
     * Closes the program 
     */
    public void exitGame() {
    	System.exit(0);
    }
    
    /**
     * If the player attacks, adds a Bubble to the game objects
     */
    public void playerBubble() {
    	if(player.attack()) {
            double x = 0;
            if (player.getDirection() == MovableObject.Direction.RIGHT) {
                x = player.getX() + player.getWidth();
            } else {
                x = player.getX() - player.getWidth();
            }
            objects.add(new Bubble(x, player.getY(), player.getDirection()));
            gamePanel.updateComponents();
    	}
    }
    
    /**
     * Sets the player to move right if move true
     * @param move boolean indicating if it should move
     */
    public void movePlayerRight(boolean move) {
        player.setRight(move);
    }
    
    /**
     * Sets the player to move left if move true
     * @param move boolean indicating if the player should move left
     */
    public void movePlayerLeft(boolean move) {
        player.setLeft(move);
    }

    /**
     * Sets the player to jump
     */
    public void playerJump() {
        player.setJump();
    }
    
    /**
     * For all game objects currently in the level it updates their positions,
     * controls if the enemies are attacking and, if they're to be destroyed, removes them from the list
     * @param <T> the type of game objects to check
     * @param list of games object to update
     */
    private <T extends GameObject> void updatePosition(List<T> list) {
    	// Using an iterator for bubbles
        for (Iterator<T> iterator = list.iterator(); iterator.hasNext(); ) {
            T o = iterator.next();
            if(o.isToDestroy()) {
            	if(o instanceof Enemy) {
            		int[] p = Utility.randomPosition(getComponents());
                    objects.add(PowerUp.PowerUpFactory.createRandomPowerUp(p[0],p[1]));
            	}
                if(o instanceof PowerUp) {
                	PowerUp p = (PowerUp) o;
                	points += p.getPoints();
                }
            	iterator.remove();
                gamePanel.updateComponents();
            }else {
            	if(o instanceof Enemy) {
            		Enemy e = (Enemy) o;
            		if(e.attack()) {
        				double x = 0;
        	            if (e.getDirection() == MovableObject.Direction.RIGHT) {
        	                x = e.getX() + e.getWidth();
        	            } else {
        	                x = e.getX() - e.getWidth();
        	            }
        	            if(e instanceof Mighta) objects.add(new Boulder(x, e.getY(), e.getDirection()));
        	            gamePanel.updateComponents();	
            			
            		}
            		e.move(getComponents(), player);
            	}else if(o instanceof MovableObject) {
            		MovableObject b = (MovableObject)o;
            		b.updatePosition(getComponents());
            	}
            }
        }
    }

    /**
     * If the state is RUNNING it updates the position of all components and
     * if the level is finished, it goes to the next one
     */
    public void update() {
        if (state == GameState.RUNNING) {
            player.updatePosition(getComponents());
            updatePosition(enemies);
            updatePosition(objects);

            // Check if all enemies and objects are cleared
            if (enemies.isEmpty() && !levelChangeScheduled) {
                // Schedule changeLevel to be called after 5 seconds
                levelChangeScheduled = true;
                levelChangeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        changeLevel();
                        levelChangeScheduled = false;  // Reset for future level changes
                    }
                }, 7000);  // Delay of 5 seconds (5000 milliseconds)
            }
        }
    }
    
    /**
     * It changes the currently shown panel in the frame to the intended one
     * @param panel	the game state to change to it's view
     */
    public void changePanel(GameState panel) {
        Container c;
    	audioManager.pauseBackgroundMusic();
        switch (panel) {
            case LOGIN_STATE -> {
            	loginPanel.setUsers(db.getUsers());
            	c = loginPanel;
            }
            case MENU_STATE -> c = menuPanel;
            case REGISTER_STATE -> c = registerPanel;
            case START_STATE -> c = startPanel;
            case RUNNING -> {
            	audioManager.resumeBackgroundMusic();
            	c = gamePanel;
            }
            case PAUSE -> {
            	c = pausePanel;
            }
            case GAME_OVER -> {
            	audioManager.play("GameOver");
            	audioManager.stopBackgroundMusic();
            	c = gameOver;
            }
            case VICTORY -> {
            	audioManager.play("Victory");
            	c = victory;
            }
            case RANK -> {
            	rank.setUsers(db.getUsers());
            	audioManager.pause();
            	c = rank;
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + panel);
        }
        frame.show(c.getName());
        state = panel;
    }

    /**
     * Checks if the player is dead for game over or reposition if the player was hit
     */
	@Override
	public void onLivesChange() {
		if(player.isDead()) {
			changePanel(GameState.GAME_OVER);
			currentUser.incrementLostGames();
			if(currentUser.getHighestScore() < points) {
    			currentUser.setHighestScore(points);
    		}
    		try {
				db.saveUsersData(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			if(player.isInvincible())repositionPlayer(20, Utility.HEIGHT-40);
			gamePanel.updateComponents();
		}
	}
	
	/**
	 * Private method to reposition the player
	 * @param x player x-coordinate
	 * @param y player y-coordinate
	 */
	private void repositionPlayer(double x,double y) {
		player.setY(y);
		player.setX(x);
	}

}
