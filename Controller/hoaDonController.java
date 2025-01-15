package MVC.Controller;

import MVC.Model.hoaDonModel;
import MVC.View.hoaDonView;
import MVC.Model.chiTietHoaDonModel;

import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class hoaDonController {
    private Connection connection;
    private hoaDonView view;

    public hoaDonController(hoaDonView view) throws SQLException, ClassNotFoundException {
        this.view = view;

        // Initialize database connection
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=quanLyVienPhi;encrypt=false";
        String username = "sa";
        String password = "1234567890";
        connection = DriverManager.getConnection(url, username, password);
        // Add action listeners for buttons

        view.btnThemDichVu.addActionListener(e -> themHoacSuaDichVu());
 
    }
    private void themHoacSuaDichVu() {
        String maHoaDon = view.MaHoaDonField.getText().trim();
        String maBenhNhan = view.MaBenhNhanField.getText().trim();
        String ngayTaoHoaDon = view.NgayTaoHoaDonField.getText().trim();

        if (maHoaDon.isEmpty() || maBenhNhan.isEmpty() || ngayTaoHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin hóa đơn (Mã hóa đơn, Mã bệnh nhân, Ngày tạo hóa đơn).");
            return;
        }

        String maDichVu = view.MaDichVuField.getText().trim();
        int soLuong;
        try {
            soLuong = (int) view.spinnerSoLuong.getValue();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Số lượng phải là số nguyên lớn hơn 0.", "Sai định dạng", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (maDichVu.isEmpty() || soLuong <= 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin dịch vụ (Mã dịch vụ, Số lượng phải lớn hơn 0).");
            return;
        }

        try {
            // Start a transaction by turning off auto-commit
            connection.setAutoCommit(false);

            // Check if maHoaDon exists in hoaDon table
            String queryCheckHoaDon = "SELECT COUNT(*) FROM hoaDon WHERE maHoaDon = ?";
            try (PreparedStatement psCheckHoaDon = connection.prepareStatement(queryCheckHoaDon)) {
                psCheckHoaDon.setString(1, maHoaDon);
                ResultSet rs = psCheckHoaDon.executeQuery();
                rs.next();

                if (rs.getInt(1) > 0) {
                    String queryUpdateHoaDon = "UPDATE hoaDon SET maBenhNhan = ?, ngayLapHoaDon = ? WHERE maHoaDon = ?";
                    try (PreparedStatement psUpdateHoaDon = connection.prepareStatement(queryUpdateHoaDon)) {
                        psUpdateHoaDon.setString(1, maBenhNhan);
                        psUpdateHoaDon.setString(2, ngayTaoHoaDon);
                        psUpdateHoaDon.setString(3, maHoaDon);
                        psUpdateHoaDon.executeUpdate();
                    }
                } else {
                    String queryInsertHoaDon = "INSERT INTO hoaDon (maHoaDon, maBenhNhan, ngayLapHoaDon) VALUES (?, ?, ?)";
                    try (PreparedStatement psInsertHoaDon = connection.prepareStatement(queryInsertHoaDon)) {
                        psInsertHoaDon.setString(1, maHoaDon);
                        psInsertHoaDon.setString(2, maBenhNhan);
                        psInsertHoaDon.setString(3, ngayTaoHoaDon);
                        psInsertHoaDon.executeUpdate();
                    }
                }
            }

            // Check if the service exists in the dichVu table
            String queryCheckDichVu = "SELECT giaTien, heSoBaoHiem FROM dichVu WHERE maDichVu = ?";
            try (PreparedStatement psCheckDichVu = connection.prepareStatement(queryCheckDichVu)) {
                psCheckDichVu.setString(1, maDichVu);
                ResultSet rs = psCheckDichVu.executeQuery();

                if (rs.next()) {
                    double giaTien = rs.getDouble("giaTien");
                    double heSoBaoHiem = rs.getDouble("heSoBaoHiem");

                    // Calculate the total and insurance amounts
                    double thanhTien = soLuong * giaTien;
                    double thanhTienBHYT = thanhTien * heSoBaoHiem;

                    // Chẹck if chiTietHoaDon exists already
                    String queryCheckChiTietHoaDon = "SELECT COUNT(*) FROM chiTietHoaDon WHERE maDichVu = ? and maHoaDon = ?";
                    try (PreparedStatement psCheckChiTietHoaDon = connection.prepareStatement(queryCheckChiTietHoaDon)) {
                    	psCheckChiTietHoaDon.setString(1, maDichVu);
                    	psCheckChiTietHoaDon.setString(2, maHoaDon);
                        ResultSet rsCheckChiTietHoaDon = psCheckChiTietHoaDon.executeQuery();
                        rsCheckChiTietHoaDon.next();
                        
                        if (rsCheckChiTietHoaDon.getInt(1) > 0)
                        {
                            String queryInsertChiTiet = "UPDATE chiTietHoaDon SET soLuong = ?, thanhTien = ?, thanhTienBHYT = ? WHERE maDichVu = ? and maHoaDon = ?";
                            try (PreparedStatement psInsertChiTiet = connection.prepareStatement(queryInsertChiTiet)) {
                                psInsertChiTiet.setInt(1, soLuong);
                                psInsertChiTiet.setDouble(2, thanhTien);
                                psInsertChiTiet.setDouble(3, thanhTienBHYT);
                                psInsertChiTiet.setString(4, maHoaDon);
                                psInsertChiTiet.setString(5, maDichVu);
                                psInsertChiTiet.executeUpdate();
                            }
                        	
                        }
                        else
                        {
                            String queryInsertChiTiet = "INSERT INTO chiTietHoaDon  (maHoaDon, maDichVu, soLuong, thanhTien, thanhTienBHYT) VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement psInsertChiTiet = connection.prepareStatement(queryInsertChiTiet)) {
                                psInsertChiTiet.setString(1, maHoaDon);
                                psInsertChiTiet.setString(2, maDichVu);
                                psInsertChiTiet.setInt(3, soLuong);
                                psInsertChiTiet.setDouble(4, thanhTien);
                                psInsertChiTiet.setDouble(5, thanhTienBHYT);
                                psInsertChiTiet.executeUpdate();
                            }
                        	
                        }
                    }

                    // Update JTable with the new data
                    DefaultTableModel model = (DefaultTableModel) view.table.getModel();
                    model.addRow(new Object[]{
                            model.getRowCount() + 1,  // STT
                            maDichVu,                // Mã dịch vụ
                            "",                      // Tên dịch vụ
                            soLuong,                 // Số lượng
                            thanhTien,               // Thành tiền
                            thanhTienBHYT            // Thành tiền BHYT
                    });

                    // Commit the transaction after successful operations
                    connection.commit();
                    JOptionPane.showMessageDialog(view, "Chi tiết dịch vụ đã được thêm thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "Dịch vụ không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Lỗi khi xử lý dịch vụ. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(view, "Lỗi khi xử lý hóa đơn. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        try {
        	hoaDonView view = new hoaDonView();
            new hoaDonController(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
