package com.felixfeatures.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AppGUI extends JFrame {

	public AppGUI() {
		initComponents();
	}

	private void initComponents() {
		//
		// Create close label and set behavior
		//
		closeLabel = new JLabel();
		closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // set cursor
		closeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.exit(0);
			}
		});
		closeLabel.setBounds(577, 7, 15, 15); // set absolute position and size
		getContentPane().add(closeLabel); // add to main frame

		//
		// Create search JTextField and set behavior
		//
		searchField = new JTextField();
		searchField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font
		searchField.setForeground(new Color(153, 153, 153)); // set font color
		searchField.setText(DEFAULT_SEARCH_TEXT); // set default text
		searchField.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // set cursor
		searchField.setOpaque(false); // transparent
		searchField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // set empty border
		/* focus */
		searchField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// remove default text and set black foreground color
				if (searchField.getText().equals(DEFAULT_SEARCH_TEXT)) {
					searchField.setForeground(Color.BLACK);
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
		searchField.setBounds(374, 30, 178, 22); // set absolute position and size
		getContentPane().add(searchField); // add to main frame

		//
		// Create search label and set behavior
		//
		searchLabel = new JLabel();
		searchLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // set cursor
		searchLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				artistField.setText("Searching");
				nameField.setText("please wait");
				durationField.setText("...");
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				search();
			}
		});
		searchLabel.setBounds(557, 31, 20, 20); // set absolute position and size
		getContentPane().add(searchLabel); // add to main frame

		//
		// Create artist JTextField
		//
		artistField = new JTextField();
		artistField.setEditable(false); // non-editable
		artistField.setFont(new java.awt.Font("Calibri", java.awt.Font.BOLD, 14)); // set font style
		artistField.setForeground(new Color(105, 105, 105)); // set font color
		artistField.setOpaque(false); // transparent
		artistField.setBackground(new Color(0, 0, 0, 0));
		artistField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // set empty border
		artistField.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // set cursor style
		artistField.setHorizontalAlignment(JTextField.CENTER); // centered alignment
		artistField.setBounds(305, 90, 290, 20); // set absolute position and size
		getContentPane().add(artistField); // add to main frame

		//
		// Create track name JTextArea
		//
		nameField = new JTextArea();
		nameField.setEditable(false); // non-editable
		nameField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font style
		nameField.setForeground(new Color(105, 105, 105)); // set font color
		nameField.setLineWrap(true); // wrap text
		nameField.setWrapStyleWord(true);
		nameField.setOpaque(false); // transparent
		nameField.setBackground(new Color(0, 0, 0, 0));
		nameField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // set empty border
		nameField.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // set cursor style
		nameField.setBounds(305, 110, 290, 40); // set absolute position and size
		getContentPane().add(nameField); // add to main frame

		//
		// Create duration JTextField
		//
		durationField = new JTextField();
		durationField.setEditable(false); // non-editable
		durationField.setFont(new java.awt.Font("Calibri", 0, 12)); // set font style
		durationField.setForeground(new Color(105, 105, 105)); // set font color
		durationField.setOpaque(false); // transparent
		durationField.setBackground(new Color(0, 0, 0, 0));
		durationField.setBorder(javax.swing.BorderFactory.createEmptyBorder()); // set empty border
		durationField.setCursor(new Cursor(Cursor.TEXT_CURSOR)); // set cursor style
		durationField.setHorizontalAlignment(JTextField.CENTER); // centered alignment
		durationField.setBounds(305, 150, 290, 20); // set absolute position and size
		getContentPane().add(durationField); // add to main frame

		//
		// Create stop, play and next labels and set behavior
		//

		// Create labels
		stopLabel = new JLabel();
		playLabel = new JLabel();
		nextLabel = new JLabel();
		// Set labels cursors
		stopLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		playLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		nextLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		// Create listener and use it to labels
		LabelListener listener = new LabelListener();
		stopLabel.addMouseListener(listener);
		playLabel.addMouseListener(listener);
		nextLabel.addMouseListener(listener);
		// Set absolute position and size
		stopLabel.setBounds(377, 193, 30, 30);
		playLabel.setBounds(432, 187, 40, 40);
		nextLabel.setBounds(492, 193, 30, 30);
		// Add labels to main frame
		getContentPane().add(stopLabel);
		getContentPane().add(playLabel);
		getContentPane().add(nextLabel);
		
		//
		// Create background label
		//
		background = new JLabel();
		background.setBounds(0, 0, 600, 300);
		background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/newUI.png")));
		getContentPane().add(background);

		//
		// Set properties of main JFrame
		//
		setUndecorated(true); // frame is undecorated
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
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});
		setSize(600, 300); // set size of frame
		setLocationRelativeTo(null); // center frame on screen
		setResizable(false);
		getContentPane().setLayout(null);
	}

	private void search() {
		System.out.println("Search");
	}

	private void play() {
		System.out.println("Play");
	}

	private void next() {
		System.out.println("Next");
	}

	private void stop() {
		System.out.println("Stop");
	}
	
	private void displayError(String error) {
		artistField.setText("Oops!");
		nameField.setText(error);
		durationField.setText("");
	}

	public static void main(String... args) {
		// enable Nimbus Look and Feel
		try {
			// Check if Nimbus is supported and get its classname
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			try {
				// If Nimbus is not available, set to the default Java (metal)
				// look and feel
				UIManager.setLookAndFeel(UIManager
						.getCrossPlatformLookAndFeelClassName());
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

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
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
