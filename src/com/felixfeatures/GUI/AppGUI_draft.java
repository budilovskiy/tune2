
public class AppGUI extends JFrame {
  
  public AppGUI() {
  	initComponents();
  }
  
  private void initComponents() {
  	//
  	// Create close label and set behavior
  	//
  	closeLabel = new JLabel();
  	closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
  	closeLabel.addMouseListener(new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			System.exit(0);
		}  		
  	});
  	closelabel.setBounds(0, 0, 0, 0); // set position and size
  	getContentPane().add(closelabel); // add to main frame
  	
  	//
  	// Create search TextField and set behavior
  	//
  	searchField = new JTextField();
  	searchField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font
  	searchField.setForeground(153, 153, 153);	// set font color
  	searchField.setText(DEFAULT_SEARCH_TEXT);	// set default text
  	searchField.setOpaque(false);	// transparent
  	searchField.setBorder(new java.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0)); // set empty border
  	searchField.addFocusListener(new FocusAdapter() {
  		public void focusGained(FocusEvent e) {
  			// remove default text and set black foreground color
  			if (searchField.getText().equals(DEFAULT_SEARCH_TEXT)) {
  				searchField.setForeground(0, 0, 0);
  				searchField.setText("");
  			}
  		}
  	});
  	searchField.setBounds(0, 0, 0, 0); // set position and size
  	getContentPane().add(searchField);	// add to main frame
  	
  	//
  	// Set properties of main JFrame
  	//
  	frame.setUndecorated(true);	// frame is undecorated
	// The mouse listener and mouse motion listener to make frame dragable.
  	addMouseListener(new MouseAdapter() {
  		public void mousePressed(MouseEvent e) {
  			point.x = e.getX();
  			point.y = e.getY();
  		}
	});
	addMouseMotionListener(new MouseMotionAdapter() {
		public void mouseDragged(MouseEvent e) {
			Point p = frame.getLocation();
			frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
		}
	});
	setSize(600, 300);	// set size of frame
	setLocationRelatieTo(null);	// center frame on screen
	setResizable(false);
	
  }
  
  private JLabel closeLabel; // close label
  private JTextField searchField; // search Field
  private JLabel searchLabel; // search icon
  private JTextField artistTF; // track artist info field
  private JTextArea nameTF; // track name info field
  private JTextField durationTF; // track duration info field
  private JLabel stopLabel; // stop botton
  private JLabel playLabel; // play button
  private JLabel nextLabel; // next button
  private JLabel imageLabel; // track image label
  private JLabel background; // background
  
  private static Point point = new Point(); // point to get position of frame when drag
  private static final String DEFAULT_SEARCH_TEXT = "enter tag or artist to search";
  
}
