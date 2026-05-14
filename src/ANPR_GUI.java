import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ANPR_GUI extends JFrame {

    private JLabel imagePathLabel;

    public ANPR_GUI() {

        setTitle("ANPR System (Group 7)");
        setSize(700, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 30, 30));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(
                "<html><div style='text-align:center;'>"
                + "REGION-BASED LICENSE PLATE LOCALIZATION<br>"
                + "USING MORPHOLOGICAL AND EDGE-BASED PROCESSING"
                + "</div></html>"
        );
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel(
                "Automatic Number Plate Recognition (ANPR) Prototype"
        );
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(Box.createVerticalStrut(15));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalStrut(15));


        // Center
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JButton uploadButton = new JButton("Upload Image");
        uploadButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.setPreferredSize(new Dimension(200, 40));

        imagePathLabel = new JLabel("No image selected");
        imagePathLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        imagePathLabel.setForeground(Color.DARK_GRAY);
        imagePathLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        uploadButton.addActionListener(e -> openFileChooser());

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(uploadButton);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(imagePathLabel);
        centerPanel.add(Box.createVerticalGlue());

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240));

        JLabel footerLabel = new JLabel(
                "by Kavi Priya Balasupramaniam (A202660), Nancy Anne (A202459) & Revathi Thiyagarajan (A202248)  "
        );
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footerPanel.add(footerLabel);

        // Add Panel
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openFileChooser() {
        JFileChooser chooser = new JFileChooser(new File("images"));

        chooser.setFileFilter(
                new FileNameExtensionFilter(
                        "Image Files (*.jpg, *.jpeg, *.png)",
                        "jpg", "jpeg", "png"
                )
        );

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedImage = chooser.getSelectedFile();
            imagePathLabel.setText("Selected Image: " + selectedImage.getName());

            // Run ANPR pipeline
            Detect_number_plate.processImage(selectedImage);
        }
    }
}
