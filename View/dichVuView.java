package MVC.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class dichVuView extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField MaDichVuField;
	public JTextField TenDichVuField;
	public JTextField GiaTienDichVuField;
	public JTextField HeSoBaoHiemField;
	public JButton btnTimKiem;
	public JButton btnThem;
	public JButton btnXoa;
	public JComboBox<String> LoaiDichVuComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dichVuView frame = new dichVuView();
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
	public dichVuView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMaDichVu = new JLabel("Mã dịch vụ");
		lblMaDichVu.setBounds(37, 88, 66, 14);
		contentPane.add(lblMaDichVu);
		
		MaDichVuField = new JTextField();
		MaDichVuField.setBounds(155, 85, 292, 20);
		contentPane.add(MaDichVuField);
		MaDichVuField.setColumns(10);
		
		JLabel lblTenDichVu = new JLabel("Tên dịch vụ");
		lblTenDichVu.setBounds(37, 137, 88, 14);
		contentPane.add(lblTenDichVu);
		
		TenDichVuField = new JTextField();
		TenDichVuField.setBounds(155, 134, 292, 20);
		contentPane.add(TenDichVuField);
		TenDichVuField.setColumns(10);
		
		GiaTienDichVuField = new JTextField();
		GiaTienDichVuField.setBounds(155, 226, 292, 20);
		contentPane.add(GiaTienDichVuField);
		GiaTienDichVuField.setColumns(10);
		
		HeSoBaoHiemField = new JTextField();
		HeSoBaoHiemField.setColumns(10);
		HeSoBaoHiemField.setBounds(155, 268, 292, 20);
		contentPane.add(HeSoBaoHiemField);
		
		btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setBounds(37, 319, 89, 23);
		contentPane.add(btnTimKiem);
		
		btnThem = new JButton("Thêm/Sửa");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnThem.setBounds(171, 319, 147, 23);
		contentPane.add(btnThem);
		
		btnXoa = new JButton("Xoá");
		btnXoa.setBounds(358, 319, 89, 23);
		contentPane.add(btnXoa);
		
		JLabel lblTenChinh = new JLabel("Quản lý dịch vụ");
		lblTenChinh.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTenChinh.setBounds(155, 11, 280, 48);
		contentPane.add(lblTenChinh);
		
		JLabel lblLoiDchV = new JLabel("Loại dịch vụ");
		lblLoiDchV.setBounds(37, 184, 106, 14);
		contentPane.add(lblLoiDchV);
		
		JLabel lblGiaTienDichVu = new JLabel("Giá tiền dịch vụ");
		lblGiaTienDichVu.setBounds(37, 229, 111, 14);
		contentPane.add(lblGiaTienDichVu);
		
		JLabel lblHeSoBaoHiem = new JLabel("Hệ số bảo hiểm");
		lblHeSoBaoHiem.setBounds(37, 271, 111, 14);
		contentPane.add(lblHeSoBaoHiem);
		
		LoaiDichVuComboBox = new JComboBox();
		LoaiDichVuComboBox.setBounds(155, 180, 292, 22);
		contentPane.add(LoaiDichVuComboBox);
		
       setVisible(true);
	}
}
