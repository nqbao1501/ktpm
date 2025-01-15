package MVC.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;


public class hoaDonView extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	
	public JButton btnLuuHoaDon;
	public JButton btnThemDichVu;
	public JButton btnXoaDichVu;	
	
	public JLabel lblMaDichVu;
	public JLabel lblSoLuong;
	public JLabel lblMaHoaDon;
	public JLabel lblMaBenhNhan;
	public JLabel lblNgayTaoHoaDon;
	public JLabel lblTongtienDichVu;
	public JLabel lblTongTienBHYT;
	public JLabel lblTongTienThanhToan;
	public JLabel lblHinhThucThanhToan;
	
	public JTextField MaDichVuField;
	public JTextField MaHoaDonField;
	public JTextField MaBenhNhanField;
	public JTextField NgayTaoHoaDonField;
	public JTextField HinhThucThanhToanField;
	public JTextField TongtienDichVuField;
	public JTextField TongTienBHYTField;
	public JTextField TongTienThanhToanField;
	
	public JTable table;

	public JScrollPane scrollPane;

	public JSpinner spinnerSoLuong;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					hoaDonView frame = new hoaDonView();
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
	public hoaDonView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblMaDichVu = new JLabel("Mã dịch vụ: ");
		lblMaDichVu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMaDichVu.setBounds(492, 11, 106, 40);
		contentPane.add(lblMaDichVu);
		
		lblSoLuong = new JLabel("Số lượng:");
		lblSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSoLuong.setBounds(492, 60, 106, 51);
		contentPane.add(lblSoLuong);
		
		MaDichVuField = new JTextField();
		MaDichVuField.setBounds(608, 19, 240, 28);
		contentPane.add(MaDichVuField);
		MaDichVuField.setColumns(10);
		
		btnThemDichVu = new JButton("Thêm / Sửa dịch vụ");
		btnThemDichVu.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnThemDichVu.setBounds(492, 175, 152, 40);
		contentPane.add(btnThemDichVu);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 331, 713, 319);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setShowHorizontalLines(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"STT", "M\u00E3 d\u1ECBch v\u1EE5", "T\u00EAn d\u1ECBch v\u1EE5", "S\u1ED1 l\u01B0\u1EE3ng", "Th\u00E0nh ti\u1EC1n", " Th\u00E0nh ti\u1EC1n BHYT"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(65);
		table.getColumnModel().getColumn(2).setPreferredWidth(258);
		table.getColumnModel().getColumn(3).setPreferredWidth(59);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		
		btnLuuHoaDon = new JButton("Lưu hoá đơn");
		btnLuuHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLuuHoaDon.setBounds(733, 461, 137, 34);
		contentPane.add(btnLuuHoaDon);
		
		lblMaHoaDon = new JLabel("Mã hoá đơn: ");
		lblMaHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMaHoaDon.setBounds(22, 11, 119, 40);
		contentPane.add(lblMaHoaDon);
		
		lblMaBenhNhan = new JLabel("Mã bệnh nhân: ");
		lblMaBenhNhan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMaBenhNhan.setBounds(22, 51, 119, 40);
		contentPane.add(lblMaBenhNhan);
		
		lblNgayTaoHoaDon = new JLabel("Ngày tạo hoá đơn: ");
		lblNgayTaoHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNgayTaoHoaDon.setBounds(22, 89, 142, 51);
		contentPane.add(lblNgayTaoHoaDon);
		
		MaHoaDonField = new JTextField();
		MaHoaDonField.setColumns(10);
		MaHoaDonField.setBounds(192, 19, 240, 28);
		contentPane.add(MaHoaDonField);
		
		MaBenhNhanField = new JTextField();
		MaBenhNhanField.setColumns(10);
		MaBenhNhanField.setBounds(192, 59, 240, 28);
		contentPane.add(MaBenhNhanField);
		
		NgayTaoHoaDonField = new JTextField();
		NgayTaoHoaDonField.setColumns(10);
		NgayTaoHoaDonField.setBounds(192, 102, 240, 28);
		contentPane.add(NgayTaoHoaDonField);
		
		spinnerSoLuong = new JSpinner();
		spinnerSoLuong.setBounds(608, 75, 80, 24);
		contentPane.add(spinnerSoLuong);
		
		btnXoaDichVu = new JButton("Xoá dịch vụ");
		btnXoaDichVu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnXoaDichVu.setBounds(696, 175, 152, 40);
		contentPane.add(btnXoaDichVu);
		
		lblTongtienDichVu = new JLabel("Tổng tiền dịch vụ: ");
		lblTongtienDichVu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTongtienDichVu.setBounds(22, 176, 142, 51);
		contentPane.add(lblTongtienDichVu);
		
		lblTongTienBHYT = new JLabel("Tổng tiền BHYT: ");
		lblTongTienBHYT.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTongTienBHYT.setBounds(22, 225, 142, 51);
		contentPane.add(lblTongTienBHYT);
		
		lblTongTienThanhToan = new JLabel("Tổng tiền thanh toán:  ");
		lblTongTienThanhToan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTongTienThanhToan.setBounds(22, 269, 165, 51);
		contentPane.add(lblTongTienThanhToan);
		
		lblHinhThucThanhToan = new JLabel("Hình thức thanh toán: ");
		lblHinhThucThanhToan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHinhThucThanhToan.setBounds(22, 133, 165, 51);
		contentPane.add(lblHinhThucThanhToan);
		
		HinhThucThanhToanField = new JTextField();
		HinhThucThanhToanField.setColumns(10);
		HinhThucThanhToanField.setBounds(192, 150, 240, 28);
		contentPane.add(HinhThucThanhToanField);
		
		TongtienDichVuField = new JTextField();
		TongtienDichVuField.setEditable(false);
		TongtienDichVuField.setColumns(10);
		TongtienDichVuField.setBounds(192, 193, 240, 28);
		contentPane.add(TongtienDichVuField);
		
		TongTienBHYTField = new JTextField();
		TongTienBHYTField.setEditable(false);
		TongTienBHYTField.setColumns(10);
		TongTienBHYTField.setBounds(192, 242, 240, 28);
		contentPane.add(TongTienBHYTField);
		
		TongTienThanhToanField = new JTextField();
		TongTienThanhToanField.setEditable(false);
		TongTienThanhToanField.setColumns(10);
		TongTienThanhToanField.setBounds(192, 286, 240, 28);
		contentPane.add(TongTienThanhToanField);

	       setVisible(true);
	}
}
