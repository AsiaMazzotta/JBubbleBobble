package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.GameController;
import Model.Blubba;
import Model.Boulder;
import Model.Bubble;
import Model.Enemy;
import Model.GameObject;
import Model.Mighta;
import Model.MovableObject.Direction;
import Model.Player;
import Model.PowerUp;
import Model.Utility;
import Model.Wall;
import Model.ZenChan;

/**
 * The GamePanel class is responsible for rendering the game UI components,
 * including the player view, score, level, lives, and the game objects.
 * It extends BasePanel to provide additional game-specific functionality.
 */
public class GamePanel extends BasePanel {

    private static final long serialVersionUID = -9092929548223997795L;
    private PlayerView player;
    private List<GameObject> components;
    private GameController gc;
    private Player p;
    private JLabel points;
    private JLabel level;
    private JLabel lives;

    private Image[] zenChanLeft;
    private Image[] zenChanRight;
    private Image[] mightaLeft;
    private Image[] mightaRight;
    private Image[] blubbaLeft;
    private Image[] blubbaRight;
    private Image[] bubble;
    private Image[] caughtZen;
    private Image[] caughtMighta;
    private Image[] caughtBlubba;
    private int currentImageIndex;

    /**
     * Constructs a new GamePanel with the specified GameController and Player.
     *
     * @param controller The GameController that manages the game logic.
     * @param p The Player object representing the current player.
     */
    public GamePanel(GameController controller, Player p) {
        initializeImages();
        this.p = p;
        gc = controller;
        setName("GamePanel");
        components = gc.getComponents();
        player = new PlayerView(p);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Lives label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 50, 0, 50); // Adjust insets for spacing
        lives = new JLabel();
        lives.setFont(GameFont.CUSTOM_FONT);
        lives.setForeground(Color.WHITE);
        lives.setText(String.format("%02d", p.getLives()));
        add(lives, gbc);

        // Points label
        gbc.gridx = 1;
        points = new JLabel();
        points.setFont(GameFont.CUSTOM_FONT);
        points.setForeground(Color.WHITE);
        points.setText(String.format("%06d", gc.getPoints()));
        add(points, gbc);

        // Level label
        gbc.gridx = 2;
        level = new JLabel();
        level.setText("LEVEL " + gc.getCurrentLevel());
        level.setFont(GameFont.CUSTOM_FONT);
        level.setForeground(Color.WHITE);
        add(level, gbc);

        // LevelPanel
        gbc.gridx = 0; // Span all columns
        gbc.gridy = 1;
        gbc.gridwidth = 3; // Make it span all three columns
        gbc.insets = new Insets(20, 0, 0, 0); // Space between labels and level panel
        LevelPanel l = new LevelPanel(player, components);
        add(l, gbc);

        currentImageIndex = (currentImageIndex + 1) % zenChanLeft.length;
    }

    /**
     * Initializes the images used for various game objects.
     */
    private void initializeImages() {
        zenChanLeft = new Image[]{
            GameImage.ZENCHAN_L1.getImage(),
            GameImage.ZENCHAN_L2.getImage()
        };
        zenChanRight = new Image[]{
            GameImage.ZENCHAN_R1.getImage(),
            GameImage.ZENCHAN_R2.getImage()
        };
        mightaLeft = new Image[]{
            GameImage.MIGHTA_L1.getImage(),
            GameImage.MIGHTA_L2.getImage()
        };
        mightaRight = new Image[]{
            GameImage.MIGHTA_R1.getImage(),
            GameImage.MIGHTA_R2.getImage()
        };
        blubbaLeft = new Image[]{
            GameImage.BLUBBA_L1.getImage(),
            GameImage.BLUBBA_L2.getImage()
        };
        blubbaRight = new Image[]{
            GameImage.BLUBBA_R1.getImage(),
            GameImage.BLUBBA_R2.getImage()
        };
        bubble = new Image[]{
            GameImage.BUBBLE1.getImage(),
            GameImage.BUBBLE2.getImage()
        };
        caughtBlubba = new Image[]{
            GameImage.CAUGHT_BLUBBA1.getImage(),
            GameImage.CAUGHT_BLUBBA2.getImage()
        };
        caughtZen = new Image[]{
            GameImage.CAUGHT_ZEN1.getImage(),
            GameImage.CAUGHT_ZEN2.getImage()
        };
        caughtMighta = new Image[]{
            GameImage.CAUGHT_MIGHTA1.getImage(),
            GameImage.CAUGHT_MAIGHTA2.getImage()
        };
    }

    /**
     * Updates the displayed components on the game panel, including
     * lives, points, and level.
     */
    public void updateComponents() {
        level.setText("LEVEL " + gc.getCurrentLevel());
        points.setText(String.format("%06d", gc.getPoints()));
        lives.setText(String.format("%02d", p.getLives()));
        components = gc.getComponents();
    }

    /**
     * Returns the PlayerView associated with this GamePanel.
     *
     * @return The PlayerView object representing the player.
     */
    public PlayerView getPlayer() {
        return player;
    }

    /**
     * The LevelPanel class is responsible for rendering the level view,
     * including the player and the game objects.
     */
    public class LevelPanel extends JPanel {

        private static final long serialVersionUID = 8883913567463544816L;

        /**
         * Constructs a new LevelPanel with the specified PlayerView and list of GameObjects.
         *
         * @param player The PlayerView associated with this LevelPanel.
         * @param components The list of GameObjects to be displayed.
         */
        public LevelPanel(PlayerView player, List<GameObject> components) {
            setLayout(new GridBagLayout()); // Use GridBagLayout for alignment
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(Utility.WIDTH, Utility.HEIGHT));
            add(player); // Add player view to center it
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            currentImageIndex = (currentImageIndex + 1) % zenChanLeft.length;

            for (GameObject obj : components) {
                if (!obj.isVisible()) continue;

                if (obj instanceof Wall) {
                    drawWall(g, (Wall) obj);
                } else if (obj instanceof ZenChan) {
                    drawZenChan(g, (ZenChan) obj);
                } else if (obj instanceof Mighta) {
                    drawMighta(g, (Mighta) obj);
                } else if (obj instanceof Bubble) {
                    drawBubble(g, (Bubble) obj);
                } else if (obj instanceof Blubba) {
                    drawBlubba(g, (Blubba) obj);
                } else if (obj instanceof PowerUp) {
                    drawPowerUp(g, (PowerUp) obj);
                } else if (obj instanceof Boulder) {
                    drawBoulder(g, (Boulder) obj);
                }
            }
        }

        /**
         * Draws the specified Wall object on the provided Graphics context.
         *
         * @param g The Graphics context on which to draw.
         * @param w The Wall object to draw.
         */
        private void drawWall(Graphics g, Wall w) {
            Image image = null;
            switch (w.getType()) {
                case TYPE_ONE -> image = GameImage.WALL_ONE.getImage();
                case TYPE_TWO -> image = (w.getX() == 0 || w.getX() == Utility.WIDTH - w.getWidth())
                    ? GameImage.WALL_TWO_ONE.getImage()
                    : GameImage.WALL_TWO_TWO.getImage();
                case TYPE_THREE -> image = (w.getX() == 0 || w.getX() == Utility.WIDTH - w.getWidth())
                    ? GameImage.WALL_THREE_ONE.getImage()
                    : GameImage.WALL_THREE_TWO.getImage();
                case TYPE_FOUR -> image = (w.getX() == 0 || w.getX() == Utility.WIDTH - w.getWidth())
                    ? GameImage.WALL_FOUR_ONE.getImage()
                    : GameImage.WALL_FOUR_TWO.getImage();
                case TYPE_FIVE -> image = (w.getX() == 0 || w.getX() == Utility.WIDTH - w.getWidth())
                    ? GameImage.WALL_FIVE_ONE.getImage()
                    : GameImage.WALL_FIVE_TWO.getImage();
                case TYPE_SIX -> image = (w.getX() == 0 || w.getX() == Utility.WIDTH - w.getWidth())
                    ? GameImage.WALL_SIX_ONE.getImage()
                    : GameImage.WALL_SIX_TWO.getImage();
                case TYPE_SEVEN -> image = (w.getX() == 0 || w.getX() == Utility.WIDTH - w.getWidth())
                    ? GameImage.WALL_SEVEN_ONE.getImage()
                    : GameImage.WALL_SEVEN_TWO.getImage();
                case TYPE_EIGHT -> image = (w.getX() == 0 || w.getX() == Utility.WIDTH - w.getWidth())
                    ? GameImage.WALL_EIGHT_ONE.getImage()
                    : GameImage.WALL_EIGHT_TWO.getImage();
            }
            g.drawImage(image, (int) w.getX(), (int) w.getY(), (int) w.getWidth(), (int) w.getHeight(), null);
        }

        /**
         * Draws the specified ZenChan object on the provided Graphics context.
         *
         * @param g The Graphics context on which to draw.
         * @param zenChan The ZenChan object to draw.
         */
        private void drawZenChan(Graphics g, ZenChan zenChan) {
            Image i = (zenChan.getDirection() == Direction.LEFT)
                ? zenChanLeft[currentImageIndex]
                : zenChanRight[currentImageIndex];
            g.drawImage(i, (int) zenChan.getX(), (int) zenChan.getY(), (int) zenChan.getWidth(), (int) zenChan.getHeight(), null);
        }

        /**
         * Draws the specified Mighta object on the provided Graphics context.
         *
         * @param g The Graphics context on which to draw.
         * @param m The Mighta object to draw.
         */
        private void drawMighta(Graphics g, Mighta m) {
            Image i = (m.getDirection() == Direction.LEFT)
                ? mightaLeft[currentImageIndex]
                : mightaRight[currentImageIndex];
            g.drawImage(i, (int) m.getX(), (int) m.getY(), (int) m.getWidth(), (int) m.getHeight(), null);
        }

        /**
         * Draws the specified Bubble object on the provided Graphics context.
         *
         * @param g The Graphics context on which to draw.
         * @param b The Bubble object to draw.
         */
        private void drawBubble(Graphics g, Bubble b) {
            Image i;
            Enemy e = b.getCaughtEnemy();
            if (e != null) {
                if (e instanceof ZenChan) i = caughtZen[currentImageIndex];
                else if (e instanceof Mighta) i = caughtMighta[currentImageIndex];
                else if (e instanceof Blubba) i = caughtBlubba[currentImageIndex];
                else i = bubble[currentImageIndex];
            } else {
                i = bubble[currentImageIndex];
            }
            g.drawImage(i, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight(), null);
        }

        /**
         * Draws the specified Blubba object on the provided Graphics context.
         *
         * @param g The Graphics context on which to draw.
         * @param b The Blubba object to draw.
         */
        private void drawBlubba(Graphics g, Blubba b) {
            Image i = (b.getDirection() == Direction.LEFT)
                ? blubbaLeft[currentImageIndex]
                : blubbaRight[currentImageIndex];
            g.drawImage(i, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight(), null);
        }

        /**
         * Draws the specified PowerUp object on the provided Graphics context.
         *
         * @param g The Graphics context on which to draw.
         * @param p The PowerUp object to draw.
         */
        private void drawPowerUp(Graphics g, PowerUp p) {
            Image i = switch (p.getType()) {
                case CAKE -> GameImage.CAKE.getImage();
                case CARROT -> GameImage.CARROT.getImage();
                case CHERRY -> GameImage.CHERRY.getImage();
                case GRAPE -> GameImage.GRAPE.getImage();
                case GREEN_PEPPER -> GameImage.GREEN_PEPPER.getImage();
                case HAMBURGER -> GameImage.HAMBURGER.getImage();
                case ICE_CREAM -> GameImage.ICE_CREAM.getImage();
                case MUSHROOM -> GameImage.MUSHROOM.getImage();
                case TURNIP -> GameImage.TURNIP.getImage();
                case WATERMELON -> GameImage.WATERMELON.getImage();
            };
            g.drawImage(i, (int) p.getX(), (int) p.getY(), (int) p.getWidth(), (int) p.getHeight(), null);
        }

        /**
         * Draws the specified Boulder object on the provided Graphics context.
         *
         * @param g The Graphics context on which to draw.
         * @param b The Boulder object to draw.
         */
        private void drawBoulder(Graphics g, Boulder b) {
            Image i = GameImage.BOULDER.getImage();
            g.drawImage(i, (int) b.getX(), (int) b.getY(), (int) b.getWidth(), (int) b.getHeight(), null);
        }
    }
}
