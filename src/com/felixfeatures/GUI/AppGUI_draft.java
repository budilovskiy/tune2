
public class AppGUI extends JFrame {
  
  public AppGUI() {
  	initComponents();
  }
  
  private void initComponents() {
  	//
  	// Create close label and set behavior
  	//
  	closeLabel = new JLabel();
  	closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));	// set cursor
  	closeLabel.addMouseListener(new MouseAdapter() {
  		@Override
		public void mouseReleased(MouseEvent e) {
			System.exit(0);
		}  		
  	});
  	closelabel.setBounds(570, 30, 25, 25); // set absolute position and size
  	getContentPane().add(closelabel); // add to main frame
  	
  	//
  	// Create search JTextField and set behavior
  	//
  	searchField = new JTextField();
  	searchField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font
  	searchField.setForeground(153, 153, 153);	// set font color
  	searchField.setText(DEFAULT_SEARCH_TEXT);	// set default text
  	searchField.setCursor(new Cursor(Cursor.TEXT_CURSOR));	// set cursor
  	searchField.setOpaque(false);	// transparent
  	searchField.setBorder(new java.swing.BorderFactory.createEmptyBorder()); // set empty border
  	/* focus */
  	searchField.addFocusListener(new FocusAdapter() {
  		@Override
  		public void focusGained(FocusEvent e) {
  			// remove default text and set black foreground color
  			if (searchField.getText().equals(DEFAULT_SEARCH_TEXT)) {
  				searchField.setForeground(0, 0, 0);
  				searchField.setText("");
  			}
  		}
  	});
  	/* action */
  	searchField.addActionListener(new ActionListener() {
  		@Override
  		public void actionPerformed(ActionEvent e) {
  			search();
  		}
  	});
  	searchField.setBounds(350, 30, 100, 20); // set absolute position and size
  	getContentPane().add(searchField);	// add to main frame
  	
  	//
  	// Create search label and set behavior
  	//
  	searchLabel = new JLabel();
  	searchLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));	// set cursor
  	searchLabel.addMouseListener(new MouseAdapter() {
  		@Override
		public void mouseReleased(MouseEvent e) {
			search();
		}  		
  	});
  	searchLabel.setBounds(450, 30, 20, 20); // set absolute position and size
  	getContentPane().add(searchLabel); // add to main frame
  	
  	//
  	// Create artist JTextField
  	//
  	artistField = new JTextField();
  	artistField.setEditable(false);	// non-editable
  	artistField.setFont(new java.awt.Font("Calibri", java.awt.Font.BOLD, 14)); // set font style
  	artistField.setForeground(105, 105, 105);	// set font color
  	artistField.setOpaque(false);	// transparent
  	artistField.setBackground(new Color(0,0,0,0));
  	artistField.setBorder(new java.swing.BorderFactory.createEmptyBorder()); // set empty border
  	artistField.setCursor(new Cursor(Cursor.TEXT_CURSOR));	// set coursor style
  	artistField.setHorizontalAlignment(JTextField.CENTER);	// centered alignment
  	artistField.setBounds(305, 80, 290, 20); // set absolute position and size
  	getContentPane().add(artistField); // add to main frame
  	
  	//
  	// Create track name JTextArea
  	//
  	nameField = new JTextArea();
  	nameField.setEditable(false);	// non-editable
  	nameField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font style
  	nameField.setForeground(105, 105, 105);	// set font color
  	nameField.setLineWrap(true);	// wrap text
  	nameField.setWrapStyleWord(true);
  	nameField.setOpaque(false);	// transparent
  	nameField.setBackground(new Color(0,0,0,0));
  	nameField.setBorder(new java.swing.BorderFactory.createEmptyBorder()); // set empty border
  	nameField.setCursor(new Cursor(Cursor.TEXT_CURSOR));	// set coursor style
  	nameField.setHorizontalAlignment(JTextField.CENTER);	// centered alignment
  	nameField.setBounds(305, 100, 290, 40); // set absolute position and size
  	getContentPane().add(nameField); // add to main frame
  	
  	//
  	// Create duration JTextField
  	//
  	durationField = new JTextField();
  	durationField.setEditable(false);	// non-editable
  	durationField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font style
  	durationField.setForeground(105, 105, 105);	// set font color
  	durationField.setOpaque(false);	// transparent
  	durationField.setBackground(new Color(0,0,0,0));
  	durationField.setBorder(new java.swing.BorderFactory.createEmptyBorder()); // set empty border
  	durationField.setCursor(new Cursor(Cursor.TEXT_CURSOR));	// set coursor style
  	durationField.setHorizontalAlignment(JTextField.CENTER);	// centered alignment
  	durationField.setBounds(305, 140, 290, 20); // set absolute position and size
  	getContentPane().add(durationField); // add to main frame
  	
  	//
  	// Create stop, play and next labels and set behavior
  	//
  	
  	// Create labels
  	stopLabel = new JLabel();
  	playLabel = new JLabel();
  	nextLabel = new JLabel();
  	// Set lables cursors
  	stopLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
  	playLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
  	nextLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
  	// Create listener and use it to labels
  	LabelListener listener = new LabelListener();
  	stopLabel.addMouseListener(listener);
  	playLabel.addMouseListener(listener);
  	nextLabel.addMouseListener(listener);
  	// Set absolute position and size
  	stopLabel.setBounds(400, 180, 50, 50);
  	playLabel.setBounds(450, 180, 50, 50);
  	nextLabel.setBounds(500, 180, 50, 50);
  	// Add labels to main frame
  	getContentPane().add(stopLabel);
  	getContentPane().add(playLabel);
  	getContentPane().add(nextLabel);
  	
  	//
  	// Set properties of main JFrame
  	//
  	frame.setUndecorated(true);	// frame is undecorated
	// The mouse listener and mouse motion listener to make frame dragable.
  	addMouseListener(new MouseAdapter() {
  		@Override
  		public void mousePressed(MouseEvent e) {
  			point.x = e.getX();
  			point.y = e.getY();
  		}
	});
	addMouseMotionListener(new MouseMotionAdapter() {
		@Override
		public void mouseDragged(MouseEvent e) {
			Point p = frame.getLocation();
			frame.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
		}
	});
	setSize(600, 300);	// set size of frame
	setLocationRelatieTo(null);	// center frame on screen
	setResizable(false);
  }
  
  private void search() {
  	
  }
  
  private void play() {
  	
  }
  
  private void next() {
  	
  }
  
  private void stop() {
  	
  }
  
  public static void main(String... args) {
  	// enable Nimbus Look and Feel
  	try {
  		// Check if Nimbus is supported and get its classname
  		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
  			if ("Nimbus".equals(info.getName())) {
  				javax.swing.UIManager.setLookAndFeel(info.getClassName());
  				break;
  			}
  		}	
  	} catch (Exception e) {
  		try {
			// If Nimbus is not available, set to the default Java (metal) look and feel
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
  	}
  	
  	final AppGUI frame = new AppGUI();
  	/* Create and display the form */
  	java.awt.EventQueue.invokeLater(new Runnable() {
		@Override
		public void run() {
			frame.setVisible(true);
		}
  	});
  	
  }
  
  private JLabel closeLabel; // close label
  private JTextField searchField; // search Field
  private JLabel searchLabel; // search icon
  private JTextField artistField; // track artist info field
  private JTextArea nameField; // track name info field
  private JTextField durationField; // track duration info field
  private JLabel stopLabel; // stop button
  private JLabel playLabel; // play button
  private JLabel nextLabel; // next button
  private JLabel imageLabel; // track image label
  private JLabel background; // background
  
  private static Point point = new Point(); // point to get position of frame when drag
  private static final String DEFAULT_SEARCH_TEXT = "enter tag or artist to search";
  
}

private class LabelListener implements MouseListener {
	@Override
	public void mouseReleased(MouseEvent e) {
		// determine which button has fired the event
		JLabel source = (JLabel) e.getSource();
		if (source == stopLabel) {
			stop();
		} else if (source == playLabel) {
			play();
		} else {
			next();
		}
	}
}
