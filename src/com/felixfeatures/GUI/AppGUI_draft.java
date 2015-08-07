
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
  	getContentPane().add(closelabel);
  	
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
  
}