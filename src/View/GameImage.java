package View;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * The GameImage enum defines a collection of image resources used in the game.
 * Each enum constant corresponds to a specific image file, which can be accessed
 * through the provided methods.
 */
public enum GameImage {
    /** Icon for selection. */
    SELECTION_ICON("./res/Graphics/Bubble.png"),
    /** Icon for the game title. */
    TITLE_ICON("./res/Graphics/Title.gif"),
    
    // Enemies images
    ZENCHAN_R1("./res/Graphics/Characters/ZenChanR1.png"),
    ZENCHAN_R2("./res/Graphics/Characters/ZenChanR2.png"),
    ZENCHAN_L1("./res/Graphics/Characters/ZenChanL1.png"),
    ZENCHAN_L2("./res/Graphics/Characters/ZenChanL2.png"),
    CAUGHT_ZEN1("./res/Graphics/Characters/CaughtZen1.png"),
    CAUGHT_ZEN2("./res/Graphics/Characters/CaughtZen2.png"),
    MIGHTA_R1("./res/Graphics/Characters/MightaR1.png"),
    MIGHTA_R2("./res/Graphics/Characters/MightaR2.png"),
    MIGHTA_L1("./res/Graphics/Characters/MightaL1.png"),
    MIGHTA_L2("./res/Graphics/Characters/MightaL2.png"),
    CAUGHT_MIGHTA1("./res/Graphics/Characters/CaughtMighta1.png"),
    CAUGHT_MAIGHTA2("./res/Graphics/Characters/CaughtMighta2.png"),
    BLUBBA_R1("./res/Graphics/Characters/BlubbaR1.png"),
    BLUBBA_R2("./res/Graphics/Characters/BlubbaR2.png"),
    BLUBBA_L1("./res/Graphics/Characters/BlubbaL1.png"),
    BLUBBA_L2("./res/Graphics/Characters/BlubbaL2.png"),
    CAUGHT_BLUBBA1("./res/Graphics/Characters/CaughtBlubba1.png"),
    CAUGHT_BLUBBA2("./res/Graphics/Characters/CaughtBlubba2.png"),
    
    // All wall images
    WALL_ONE("./res/Graphics/Objects/WallOne.png"),
    WALL_TWO_ONE("./res/Graphics/Objects/WallTwo1.png"),
    WALL_TWO_TWO("./res/Graphics/Objects/WallTwo2.png"),
    WALL_THREE_ONE("./res/Graphics/Objects/WallThree1.png"),
    WALL_THREE_TWO("./res/Graphics/Objects/WallThree2.png"),
    WALL_FOUR_ONE("./res/Graphics/Objects/WallFour1.png"),
    WALL_FOUR_TWO("./res/Graphics/Objects/WallFour2.png"),
    WALL_FIVE_ONE("./res/Graphics/Objects/WallFive1.png"),
    WALL_FIVE_TWO("./res/Graphics/Objects/WallFive2.png"),
    WALL_SIX_ONE("./res/Graphics/Objects/WallSix1.png"),
    WALL_SIX_TWO("./res/Graphics/Objects/WallSix2.png"),
    WALL_SEVEN_ONE("./res/Graphics/Objects/WallSeven1.png"),
    WALL_SEVEN_TWO("./res/Graphics/Objects/WallSeven2.png"),
    WALL_EIGHT_ONE("./res/Graphics/Objects/WallEight1.png"),
    WALL_EIGHT_TWO("./res/Graphics/Objects/WallEight2.png"),
    
    // Images for the idle animation
    BOB_RIGHT_ONE("./res/Graphics/Characters/BobRightI1.png"),
    BOB_RIGHT_TWO("./res/Graphics/Characters/BobRightI2.png"),
    BOB_LEFT_ONE("./res/Graphics/Characters/BobLeftI1.png"),
    BOB_LEFT_TWO("./res/Graphics/Characters/BobLeftI2.png"),

    // Images for walking animations
    BOB_MOVING_R1("./res/Graphics/Characters/BobRight1.png"),
    BOB_MOVING_R2("./res/Graphics/Characters/BobRight2.png"),
    BOB_MOVING_L1("./res/Graphics/Characters/BobLeft1.png"),
    BOB_MOVING_L2("./res/Graphics/Characters/BobLeft2.png"),

    // Images for the profile
    AVATAR_ONE("./res/Graphics/Avatar/Dino1.png"),
    AVATAR_TWO("./res/Graphics/Avatar/Dino2.png"),
    AVATAR_THREE("./res/Graphics/Avatar/Ghost.png"),
    
    // Images for all the PowerUps
    CAKE("./res/Graphics/Objects/Cake.png"),
    MUSHROOM("./res/Graphics/Objects/Mushroom.png"),
    GREEN_PEPPER("./res/Graphics/Objects/GreenPepper.png"),
    ICE_CREAM("./res/Graphics/Objects/IceCream.png"),
    GRAPE("./res/Graphics/Objects/Grape.png"),
    CARROT("./res/Graphics/Objects/Carrot.png"),
    TURNIP("./res/Graphics/Objects/Turnip.png"),
    CHERRY("./res/Graphics/Objects/Cherry.png"),
    WATERMELON("./res/Graphics/Objects/Watermelon.png"),
    HAMBURGER("./res/Graphics/Objects/Hamburger.png"), 
    
    // Bubbles
    BUBBLE1("./res/Graphics/Objects/Bubble1.png"),
    BUBBLE2("./res/Graphics/Objects/Bubble2.png"),
    BOULDER("./res/Graphics/Objects/Boulder.png");

    /** The path to the image file. */
    private final String path;

    /** The loaded image. */
    private final Image image;

    /**
     * Constructs a GameImage enum constant with the specified image path.
     *
     * @param path the path to the image file
     */
    GameImage(String path) {
        this.path = path;
        this.image = new ImageIcon(path).getImage();
    }

    /**
     * Returns the image associated with this enum constant.
     *
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Returns the path to the image file.
     *
     * @return the path to the image
     */
    public String getPath() {
        return path;
    }
}
