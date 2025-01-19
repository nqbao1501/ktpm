package MVC.View;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class hoaDonViewBenhNhan extends JFrame {
    private static final long serialVersionUID = 1L;
    public JPanel contentPane;

    public JButton btnTimKiemHoaDon;
    public JButton btnDong;
    public JLabel lblMaBenhNhan;
    public JLabel lblMaHoaDon;
    public JLabel lblNgayTaoHoaDon;
    public JLabel lblTongtienDichVu;
    public JLabel lblTongTienBHYT;
    public JLabel lblTongTienThanhToan;
    public JLabel lblHinhThucThanhToan;

    public JComboBox<String> MaHoaDonComboBox; // Thay thế JTextField bằng JComboBox
    public JTextField MaBenhNhanField;
    public JTextField NgayTaoHoaDonField;
    public JTextField HinhThucThanhToanField;
    public JTextField TongtienDichVuField;
    public JTextField TongTienBHYTField;
    public JTextField TongTienThanhToanField;

    public JTable table;
    public JScrollPane scrollPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                hoaDonViewBenhNhan frame = new hoaDonViewBenhNhan();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public hoaDonViewBenhNhan() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(22, 331, 713, 319);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        table.setShowHorizontalLines(false);
        table.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "STT", "Mã dịch vụ", "Tên dịch vụ", "Số lượng", "Thành tiền", "Thành tiền BHYT"
                }
        ));
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(65);
        table.getColumnModel().getColumn(2).setPreferredWidth(258);
        table.getColumnModel().getColumn(3).setPreferredWidth(59);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);

        
		lblMaBenhNhan = new JLabel("Mã bệnh nhân: ");
		lblMaBenhNhan.setBounds(22, 11, 119, 40);
		contentPane.add(lblMaBenhNhan);
		
		MaBenhNhanField = new JTextField();
		MaBenhNhanField.setColumns(10);
		MaBenhNhanField.setBounds(192, 19, 240, 28);
		contentPane.add(MaBenhNhanField);
		
        lblMaHoaDon = new JLabel("Mã hoá đơn: ");
        lblMaHoaDon.setBounds(22, 51, 119, 40);
        contentPane.add(lblMaHoaDon);

        MaHoaDonComboBox = new JComboBox<>();
        MaHoaDonComboBox.setBounds(192, 59, 240, 28);
        contentPane.add(MaHoaDonComboBox);

        lblNgayTaoHoaDon = new JLabel("Ngày tạo hoá đơn: ");
        lblNgayTaoHoaDon.setBounds(22, 89, 142, 51);
        contentPane.add(lblNgayTaoHoaDon);

        NgayTaoHoaDonField = new JTextField();
        NgayTaoHoaDonField.setBounds(192, 102, 240, 28);
        contentPane.add(NgayTaoHoaDonField);

        lblTongtienDichVu = new JLabel("Tổng tiền dịch vụ: ");
        lblTongtienDichVu.setBounds(22, 176, 142, 51);
        contentPane.add(lblTongtienDichVu);

        lblTongTienBHYT = new JLabel("Tổng tiền BHYT: ");
        lblTongTienBHYT.setBounds(22, 225, 142, 51);
        contentPane.add(lblTongTienBHYT);

        lblTongTienThanhToan = new JLabel("Tổng tiền thanh toán: ");
        lblTongTienThanhToan.setBounds(22, 269, 165, 51);
        contentPane.add(lblTongTienThanhToan);

        lblHinhThucThanhToan = new JLabel("Hình thức thanh toán: ");
        lblHinhThucThanhToan.setBounds(22, 133, 165, 51);
        contentPane.add(lblHinhThucThanhToan);

        HinhThucThanhToanField = new JTextField();
        HinhThucThanhToanField.setBounds(192, 150, 240, 28);
        contentPane.add(HinhThucThanhToanField);

        TongtienDichVuField = new JTextField();
        TongtienDichVuField.setEditable(false);
        TongtienDichVuField.setBounds(192, 193, 240, 28);
        contentPane.add(TongtienDichVuField);

        TongTienBHYTField = new JTextField();
        TongTienBHYTField.setEditable(false);
        TongTienBHYTField.setBounds(192, 242, 240, 28);
        contentPane.add(TongTienBHYTField);

        TongTienThanhToanField = new JTextField();
        TongTienThanhToanField.setEditable(false);
        TongTienThanhToanField.setBounds(192, 286, 240, 28);
        contentPane.add(TongTienThanhToanField);

        btnTimKiemHoaDon = new JButton("Tìm kiếm hoá đơn");
        btnTimKiemHoaDon.setBounds(745, 415, 119, 34);
        contentPane.add(btnTimKiemHoaDon);
        
        btnDong = new JButton("Đóng");
        btnDong.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDong.setBounds(745, 550, 119, 34);
        contentPane.add(btnDong);

        // Sự kiện đóng cửa sổ
        btnDong.addActionListener(e -> dispose());

        setVisible(true);
    }
}
