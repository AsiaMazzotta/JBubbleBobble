package View;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * RegisterPanel is a JPanel that provides a user interface for user registration,
 * allowing users to select an avatar and enter a nickname.
 */
public class RegisterPanel extends BasePanel {

    private static final long serialVersionUID = 1892837373941012176L;

    private int selectedImage;
    private ImageLabel[] images;
    private ImageLabel currentImageLabel; // To hold the currently displayed image
    private JTextField nickname;

    /**
     * Constructs a RegisterPanel with avatar selection and nickname input.
     */
    public RegisterPanel() {
        setName("Register");

        selectedImage = 0;
        images = new ImageLabel[3];

        // Initialize avatar images
        images[0] = new ImageLabel(new ImageIcon(GameImage.AVATAR_ONE.getImage().getScaledInstance((int)(22*1.5), (int)(22*1.5), Image.SCALE_SMOOTH)), GameImage.AVATAR_ONE.getPath());
        images[1] = new ImageLabel(new ImageIcon(GameImage.AVATAR_TWO.getImage().getScaledInstance((int)(22*1.5), (int)(22*1.5), Image.SCALE_SMOOTH)), GameImage.AVATAR_TWO.getPath());
        images[2] = new ImageLabel(new ImageIcon(GameImage.AVATAR_THREE.getImage().getScaledInstance((int)(22*1.5), (int)(22*1.5), Image.SCALE_SMOOTH)), GameImage.AVATAR_THREE.getPath());

        JLabel info = new JLabel("Press ARROWS to change");
        info.setFont(GameFont.CUSTOM_FONT);
        info.setForeground(Color.WHITE);

        nickname = new JTextField(4);
        PlainDocument doc = (PlainDocument) nickname.getDocument();
        doc.setDocumentFilter(new CharacterLimitFilter(5)); // Example limit of 4 characters

        JLabel text = new JLabel("Press ENTER to confirm");
        text.setFont(GameFont.CUSTOM_FONT);
        text.setForeground(Color.WHITE);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);

        gbc.gridy = 0;
        gbc.gridx = 1;
        currentImageLabel = images[selectedImage];
        add(currentImageLabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        add(info, gbc);

        gbc.gridy = 2;
        add(nickname, gbc);

        gbc.gridy = 3;
        add(text, gbc);
    }

    /**
     * Moves the selection up through the available images.
     */
    public void moveSelectionUp() {
        selectedImage = (selectedImage - 1 + images.length) % images.length;
        updateSelection();
    }

    /**
     * Moves the selection down through the available images.
     */
    public void moveSelectionDown() {
        selectedImage = (selectedImage + 1) % images.length;
        updateSelection();
    }

    /**
     * Updates the currently displayed image based on the selected image.
     */
    private void updateSelection() {
        // Remove the current image label
        this.remove(currentImageLabel);

        // Update the current image label to the newly selected image
        currentImageLabel = images[selectedImage];

        // Re-add the current image label at the correct position
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;
        gbc.gridx = 1;
        this.add(currentImageLabel, gbc);

        // Refresh the panel to display the new image
        this.revalidate();
        this.repaint();
    }

    /**
     * Gets the nickname text field.
     *
     * @return the JTextField for nickname input.
     */
    public JTextField getNicknameField() {
        return nickname;
    }

    /**
     * Gets the entered nickname text.
     *
     * @return the nickname as a String.
     */
    public String getNickname() {
        return nickname.getText();
    }

    /**
     * Gets the file path of the currently selected image.
     *
     * @return the current image path as a String.
     */
    public String getCurrentImagePath() {
        return currentImageLabel.getImagePath();
    }
    
    /**
     * CharacterLimitFilter limits the number of characters in a JTextField.
     */
    public class CharacterLimitFilter extends DocumentFilter {
        private final int maxCharacters;

        /**
         * Constructs a CharacterLimitFilter with a specified maximum number of characters.
         *
         * @param maxCharacters the maximum number of characters allowed.
         */
        public CharacterLimitFilter(int maxCharacters) {
            this.maxCharacters = maxCharacters;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws BadLocationException {
            if (fb.getDocument().getLength() + string.length() <= maxCharacters) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws BadLocationException {
            if (fb.getDocument().getLength() - length + text.length() <= maxCharacters) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }
    
    /**
     * ImageLabel is a JLabel that contains an image and its associated path.
     */
    public class ImageLabel extends JLabel {
        
    	private static final long serialVersionUID = -5831077181462362448L;
		private String imagePath;

        /**
         * Constructs an ImageLabel with a given icon and image path.
         *
         * @param icon      the ImageIcon to be displayed.
         * @param imagePath the path of the image as a String.
         */
        public ImageLabel(ImageIcon icon, String imagePath) {
            super(icon);
            this.imagePath = imagePath;
        }

        /**
         * Gets the image path associated with this ImageLabel.
         *
         * @return the image path as a String.
         */
        public String getImagePath() {
            return imagePath;
        }
    }
}
