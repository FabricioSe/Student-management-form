import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentFormManagement extends JFrame {

	private Connection connection = null;
	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtStudentName;
	private JTextField txtFees;
	private JTable StudentInfo;
	private JDateChooser dateChooserDOB;
	private JTextField txtSearch;
	
	/***
	 * General Methods to load the table and make fields empty 
	 */
	
	private void refreshTable()
	{
		try {
			
			String query = "SELECT ID, Name, Fees, DOB FROM StudentInfo";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			StudentInfo.setModel(DbUtils.resultSetToTableModel(rs));
			
			pst.close();
			rs.close();
			
		} catch (Exception e1) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e1);
		}
	}
	
	private void refreshFields()
	{
		txtID.setText(null);
		txtStudentName.setText(null);
		txtFees.setText(null);
		dateChooserDOB.setDate(null);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentFormManagement frame = new StudentFormManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StudentFormManagement() {
		// Sqlite Connection
		connection = SqliteConnection.dbConnector();
		/////////////////////////////////////////////////
		setTitle("Student Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 743, 739);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Dashboard");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(204, 11, 276, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("International School - Montreal Canada");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_1.setBounds(129, 61, 449, 30);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Student Information");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(236, 102, 208, 22);
		contentPane.add(lblNewLabel_2);
		
		txtID = new JTextField();
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtID.setBounds(230, 152, 219, 44);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		txtStudentName = new JTextField();
		txtStudentName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtStudentName.setColumns(10);
		txtStudentName.setBounds(230, 207, 219, 44);
		contentPane.add(txtStudentName);
		
		txtFees = new JTextField();
		txtFees.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtFees.setColumns(10);
		txtFees.setBounds(230, 262, 219, 44);
		contentPane.add(txtFees);
		
		dateChooserDOB = new JDateChooser();
		dateChooserDOB.setBounds(230, 317, 219, 39);
		dateChooserDOB.setDateFormatString("dd-MMM-yyyy");
		contentPane.add(dateChooserDOB);
		
		JLabel lblNewLabel_4 = new JLabel("ID: ");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_4.setBounds(51, 158, 67, 29);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_4_1 = new JLabel("Student Name:");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_4_1.setBounds(51, 213, 144, 29);
		contentPane.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_4_2 = new JLabel("Fees:");
		lblNewLabel_4_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_4_2.setBounds(51, 268, 67, 29);
		contentPane.add(lblNewLabel_4_2);
		
		JLabel lblNewLabel_4_3 = new JLabel("Date Of Birth:");
		lblNewLabel_4_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_4_3.setBounds(51, 327, 135, 29);
		contentPane.add(lblNewLabel_4_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(51, 477, 623, 205);
		contentPane.add(scrollPane);
		
		StudentInfo = new JTable();
		StudentInfo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		StudentInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					
					int row = StudentInfo.getSelectedRow();
					String ID = (StudentInfo.getModel().getValueAt(row, 0)).toString(); //Column 0 because we are grabbing the ID
					
					String query = "SELECT * FROM StudentInfo WHERE ID='"+ ID +"'";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					
					while (rs.next()) {
						txtID.setText(rs.getString("ID"));
						txtStudentName.setText(rs.getString("Name"));
						txtFees.setText(rs.getString("Fees"));
						((JTextField)dateChooserDOB.getDateEditor().getUiComponent()).setText(rs.getString("DOB"));
					}
					
					pst.close();
					rs.close();
					
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e2);
				}
				
			}
		});
		scrollPane.setViewportView(StudentInfo);
		
		JLabel lblNewLabel_5 = new JLabel("Search Student By Name\r\n:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_5.setBounds(99, 414, 254, 22);
		contentPane.add(lblNewLabel_5);
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				refreshTable();
				
			}
		});
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					//USING THE NAME TO SEARCH
					
					String query = "SELECT ID, Name, Fees, DOB FROM StudentInfo WHERE Name= ?";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, txtSearch.getText());
					ResultSet rs = pst.executeQuery();
					
					StudentInfo.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
					
					
				} catch (Exception e4) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e4);
				}
				
			}
		});
		txtSearch.setColumns(10);
		txtSearch.setBounds(380, 406, 219, 44);
		contentPane.add(txtSearch);
		
		JButton btnAdd = new JButton("Insert...");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "INSERT INTO StudentInfo (ID, Name, Fees, DOB) VALUES (?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.setString(1, txtID.getText());
					pst.setString(2, txtStudentName.getText());
					pst.setString(3, txtFees.getText());
					pst.setString(4, ((JTextField)dateChooserDOB.getDateEditor().getUiComponent()).getText());
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Data Inserted.");			
					pst.close();
					
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
				
				refreshTable();
				refreshFields();
				
			}
		});
		btnAdd.setIcon(new ImageIcon("C:\\Users\\Peccato\\Desktop\\INFO\\LaSalle\\Courses\\S4\\Advanced_Object_Oriented_Programming\\Java_Eclispse_Builder_design\\Fabricio_Segovia_Nunez_2031178\\Asset_1\\Asset_1\\button_blue_add.png"));
		btnAdd.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		btnAdd.setForeground(SystemColor.desktop);
		btnAdd.setBackground(SystemColor.inactiveCaption);
		btnAdd.setBounds(503, 143, 171, 58);
		btnAdd.setFocusable(false);
		btnAdd.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "UPDATE StudentInfo set ID='" + txtID.getText() + "', Name='" + txtStudentName.getText() + "'"
							+ ", Fees='"+ txtFees.getText() +"', DOB='"+ ((JTextField)dateChooserDOB.getDateEditor().getUiComponent()).getText() 
							+"' WHERE ID='"+ txtID.getText() +"'";
					PreparedStatement pst = connection.prepareStatement(query);

					pst.execute();
					JOptionPane.showMessageDialog(null, "Data Updated.");			
					pst.close();
					
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, e1);
				}
				
				refreshTable();
				refreshFields();
				
			}
		});
		btnUpdate.setIcon(new ImageIcon("C:\\Users\\Peccato\\Desktop\\INFO\\LaSalle\\Courses\\S4\\Advanced_Object_Oriented_Programming\\Java_Eclispse_Builder_design\\Fabricio_Segovia_Nunez_2031178\\Asset_1\\Asset_1\\button_pink_close.png"));
		btnUpdate.setForeground(Color.BLACK);
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		btnUpdate.setFocusable(false);
		btnUpdate.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		btnUpdate.setBackground(SystemColor.inactiveCaption);
		btnUpdate.setBounds(503, 229, 171, 58);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int action = JOptionPane.showConfirmDialog(null, "Are you Sure you want to delete?", "Delete", JOptionPane.YES_NO_OPTION);
				if (action == 0) //Option == yes
				{
					try {
						
						String query = "DELETE FROM StudentInfo WHERE ID='"+ txtID.getText() +"'";
						PreparedStatement pst = connection.prepareStatement(query);

						pst.execute();
						JOptionPane.showMessageDialog(null, "Data Removed.");			
						pst.close();
						
					} catch (Exception e1) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(null, e1);
					}
					
					refreshTable();

				}
				
				refreshFields();
				
			}
		});
		btnDelete.setIcon(new ImageIcon("C:\\Users\\Peccato\\Desktop\\INFO\\LaSalle\\Courses\\S4\\Advanced_Object_Oriented_Programming\\Java_Eclispse_Builder_design\\Fabricio_Segovia_Nunez_2031178\\Asset_1\\Asset_1\\button_red_stop.png"));
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		btnDelete.setFocusable(false);
		btnDelete.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		btnDelete.setBackground(SystemColor.inactiveCaption);
		btnDelete.setBounds(503, 312, 171, 58);
		contentPane.add(btnDelete);
		refreshTable();
	}
}
