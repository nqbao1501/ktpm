package MVC.View;

import javax.swing.*;
import java.awt.*;

public class nhanVienMenuView extends JFrame {
    private static final long serialVersionUID = 1L;

    public JButton btnQuanLyBenhNhan;
    public JButton btnQuanLyHoaDon;
    public JButton btnQuanLyDichVu;

    /**
     * Create the frame.
     */
    public nhanVienMenuView() {
        setTitle("Menu Nhân Viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Chức năng nhân viên");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(50, 20, 300, 30);
        getContentPane().add(lblTitle);

        btnQuanLyBenhNhan = new JButton("Quản lý bệnh nhân");
        btnQuanLyBenhNhan.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnQuanLyBenhNhan.setBounds(100, 70, 200, 40);
        getContentPane().add(btnQuanLyBenhNhan);

        btnQuanLyHoaDon = new JButton("Quản lý hóa đơn");
        btnQuanLyHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnQuanLyHoaDon.setBounds(100, 120, 200, 40);
        getContentPane().add(btnQuanLyHoaDon);

        btnQuanLyDichVu = new JButton("Quản lý dịch vụ");
        btnQuanLyDichVu.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnQuanLyDichVu.setBounds(100, 170, 200, 40);
        getContentPane().add(btnQuanLyDichVu);

        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                nhanVienMenuView frame = new nhanVienMenuView();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
