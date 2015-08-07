
public class AppGUI extends JFrame {
  
  public AppGUI() {
  	initComponents();
  }
  
  private void initComponents() {
  	
  	// Create close label and set
  	
  	// set properties of main JFrame
  	frame.setUndecorated(true);	// frame is undecorated
  	setLocationRelatieTo(null);	// center frame on screen
  	addMouseListener(new MouseAdapter() {
  		public void mousePressed(MouseEvent e) {
  			point.x = e.getX();
  			point.y = e.getY();
  		}
	});
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
