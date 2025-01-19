package MVC.View;

import javax.swing.*;
import java.awt.*;

public class benhNhanMenuView extends JFrame {
    private static final long serialVersionUID = 1L;

    public JButton btnTimBenhNhan;
    public JButton btnTimHoaDon;
    public JButton btnTimDichVu;

    /**
     * Create the frame.
     */
    public benhNhanMenuView() {
        setTitle("Menu Nhân Viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("Chức năng bệnh nhân");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(50, 20, 300, 30);
        getContentPane().add(lblTitle);

        btnTimBenhNhan = new JButton("Tìm bệnh nhân");
        btnTimBenhNhan.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnTimBenhNhan.setBounds(100, 70, 200, 40);
        getContentPane().add(btnTimBenhNhan);

        btnTimHoaDon = new JButton("Tìm hóa đơn");
        btnTimHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnTimHoaDon.setBounds(100, 120, 200, 40);
        getContentPane().add(btnTimHoaDon);

        btnTimDichVu = new JButton("Tìm dịch vụ");
        btnTimDichVu.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnTimDichVu.setBounds(100, 170, 200, 40);
        getContentPane().add(btnTimDichVu);

        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                benhNhanMenuView frame = new benhNhanMenuView();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
