/**
 * Project: 3613_lab10_start
 * File: LabFrame.java
 * Date: Jun 22, 2016
 * Time: 11:46:41 AM
 */
package a00973641.lab.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import a00973641.lab.decode.Decoder;
import a00973641.lab.loadclass.AClassLoader;

/**
 * @author Mara
 *
 */
public class LabFrame extends JFrame {

	// user input components.
	private JTextField passwordTextField;
	private JTextField fileNameTextField;

	public LabFrame() {
		// initialize main frame
		setSize(new Dimension(400, 120));
		setTitle("Lab09");

		// construct top panel
		JPanel topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		topPanel.setLayout(new BorderLayout());

		// panel where password and file name labels will be placed
		JPanel labelsPanel = new JPanel();
		labelsPanel.setLayout(new GridLayout(2, 1));
		JLabel passwordLabel = new JLabel(" Password: ");
		JLabel fileNameLabel = new JLabel(" File Name: ");
		labelsPanel.add(fileNameLabel);
		labelsPanel.add(passwordLabel);
		topPanel.add(labelsPanel, BorderLayout.WEST);

		// panel where password and file name textfields will be placed
		JPanel textFieldsPanel = new JPanel();
		textFieldsPanel.setLayout(new GridLayout(2, 1));
		passwordTextField = new JPasswordField();
		fileNameTextField = new JTextField();
		textFieldsPanel.add(fileNameTextField);
		textFieldsPanel.add(passwordTextField);
		topPanel.add(textFieldsPanel, BorderLayout.CENTER);

		// construct bottom panel
		JPanel bottomPanel = new JPanel();

		// create decrypt button
		JButton decryptButton = new JButton("Read from File and Decrypt");
		decryptButton.addActionListener(

				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent event) {
						Decoder decoder = new Decoder();
						byte[] classBytes = decoder.readAndDecrypt(fileNameTextField.getText(),
								passwordTextField.getText());
						AClassLoader loader = new AClassLoader();
						loader.runClass(classBytes);
					}
				});
		bottomPanel.add(decryptButton);

		// initialize main frame window
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(topPanel, BorderLayout.NORTH);
		contentPane.add(bottomPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
