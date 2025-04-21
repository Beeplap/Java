import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main extends JFrame {

    private DrawArea drawArea = new DrawArea();
    private JButton clearBtn = new JButton("Clear");
    private JButton colorBtn = new JButton("Pick Color");
    private JButton saveBtn = new JButton("Save");
    private JSlider brushSlider = new JSlider(1, 20, 5);
    private Color currentColor = Color.BLACK;

    public Main() {
        super("Java Paint App");

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(colorBtn);
        topPanel.add(new JLabel("Brush Size:"));
        topPanel.add(brushSlider);
        topPanel.add(clearBtn);
        topPanel.add(saveBtn);

        add(topPanel, BorderLayout.NORTH);
        add(drawArea, BorderLayout.CENTER);

        colorBtn.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(null, "Choose a Color", currentColor);
            if (chosenColor != null) {
                currentColor = chosenColor;
                drawArea.setColor(currentColor);
            }
        });

        clearBtn.addActionListener(e -> drawArea.clear());

        brushSlider.addChangeListener(e -> drawArea.setBrushSize(brushSlider.getValue()));

        saveBtn.addActionListener(e -> drawArea.saveImage());

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class DrawArea extends JPanel {
        private Image image;
        private Graphics2D g2;
        private int prevX, prevY;
        private boolean dragging = false;
        private int brushSize = 5;

        public DrawArea() {
            setDoubleBuffered(false);
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(800, 600));

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    prevX = e.getX();
                    prevY = e.getY();
                    dragging = true;
                }

                public void mouseReleased(MouseEvent e) {
                    dragging = false;
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (dragging && g2 != null) {
                        int x = e.getX();
                        int y = e.getY();
                        g2.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g2.drawLine(prevX, prevY, x, y);
                        repaint();
                        prevX = x;
                        prevY = y;
                    }
                }
            });
        }

        protected void paintComponent(Graphics g) {
            if (image == null) {
                image = createImage(getSize().width, getSize().height);
                g2 = (Graphics2D) image.getGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, getSize().width, getSize().height);
                g2.setColor(currentColor);
            }
            g.drawImage(image, 0, 0, null);
        }

        public void setColor(Color c) {
            currentColor = c;
            g2.setColor(c);
        }

        public void setBrushSize(int size) {
            brushSize = size;
        }

        public void clear() {
            g2.setPaint(Color.WHITE);
            g2.fillRect(0, 0, getSize().width, getSize().height);
            g2.setPaint(currentColor);
            repaint();
        }

        public void saveImage() {
            try {
                BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                paint(bufferedImage.getGraphics());

                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Save Image");
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    ImageIO.write(bufferedImage, "png", file);
                    JOptionPane.showMessageDialog(null, "Saved Successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error saving image: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
