package MVC.Controller;

import MVC.View.hoaDonViewBenhNhan;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class hoaDonControllerBenhNhan {
    private Connection connection;
    private hoaDonViewBenhNhan view;

    // Constructor
    public hoaDonControllerBenhNhan(hoaDonViewBenhNhan view) throws SQLException, ClassNotFoundException {
        this.view = view;

        // Load driver SQL Server
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=quanLyVienPhi;encrypt=false";
        String username = "sa";
        String password = "1234567890";
        connection = DriverManager.getConnection(url, username, password);

        // Lắng nghe sự kiện nhập mã bệnh nhân và tìm hóa đơn
        view.MaBenhNhanField.addActionListener(e -> timHoaDonTheoMaBenhNhan());

        // Lắng nghe sự kiện tìm kiếm hóa đơn khi chọn mã hóa đơn
        view.btnTimKiemHoaDon.addActionListener(e -> timHoaDon());
    }

    // Hàm tìm hóa đơn theo mã bệnh nhân
    private void timHoaDonTheoMaBenhNhan() {
        String maBenhNhan = view.MaBenhNhanField.getText().trim();
        if (maBenhNhan.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã bệnh nhân.");
            return;
        }

        try {
            String query = "SELECT maHoaDon FROM hoaDon WHERE maBenhNhan = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, maBenhNhan);
                ResultSet rs = ps.executeQuery();

                // Xóa hết các mục cũ trong comboBox
                view.MaHoaDonComboBox.removeAllItems();

                // Thêm các mã hóa đơn vào comboBox
                while (rs.next()) {
                    String maHoaDon = rs.getString("maHoaDon");
                    view.MaHoaDonComboBox.addItem(maHoaDon);
                }

                // Kiểm tra nếu không có hóa đơn nào
                if (view.MaHoaDonComboBox.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy hóa đơn cho mã bệnh nhân: " + maBenhNhan, "Thông báo", JOptionPane.WARNING_MESSAGE);
                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm hóa đơn. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm tìm và hiển thị hóa đơn
    private void timHoaDon() {
        String maHoaDon = (String) view.MaHoaDonComboBox.getSelectedItem();
        if (maHoaDon == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn mã hóa đơn.");
            return;
        }

        try {
            connection.setAutoCommit(false); // Tắt auto-commit

            // Lấy thông tin hóa đơn
            String queryGetHoaDon = "SELECT ngayLapHoaDon, maBenhNhan, tongTienDichVu, tongTienBHYT, tongTienThanhToan, hinhThucThanhToan " +
                                    "FROM hoaDon WHERE maHoaDon = ?";
            try (PreparedStatement psGetHoaDon = connection.prepareStatement(queryGetHoaDon)) {
                psGetHoaDon.setString(1, maHoaDon);
                ResultSet rsHoaDon = psGetHoaDon.executeQuery();

                if (rsHoaDon.next()) {
                    // Lấy thông tin từ ResultSet
                    String ngayLapHoaDon = rsHoaDon.getString("ngayLapHoaDon");
                    String maBenhNhan = rsHoaDon.getString("maBenhNhan");
                    String tongTienDichVu = String.valueOf(rsHoaDon.getDouble("tongTienDichVu"));
                    String tongTienBHYT = String.valueOf(rsHoaDon.getDouble("tongTienBHYT"));
                    String tongTienThanhToan = String.valueOf(rsHoaDon.getDouble("tongTienThanhToan"));
                    String hinhThucThanhToan = rsHoaDon.getString("hinhThucThanhToan");

                    // Hiển thị thông tin lên giao diện
                    view.NgayTaoHoaDonField.setText(ngayLapHoaDon);
                    view.MaBenhNhanField.setText(maBenhNhan);
                    view.TongtienDichVuField.setText(tongTienDichVu);
                    view.TongTienBHYTField.setText(tongTienBHYT);
                    view.TongTienThanhToanField.setText(tongTienThanhToan);
                    view.HinhThucThanhToanField.setText(hinhThucThanhToan);
                }
            }

            // Lấy chi tiết hóa đơn
            String queryGetChiTiet = "SELECT dichVu.maDichVu, dichVu.tenDichVu, chiTietHoaDon.soLuong, chiTietHoaDon.thanhTien, chiTietHoaDon.thanhTienBHYT " +
                                    "FROM chiTietHoaDon " +
                                    "JOIN dichVu ON chiTietHoaDon.maDichVu = dichVu.maDichVu " +
                                    "WHERE chiTietHoaDon.maHoaDon = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(queryGetChiTiet)) {
                preparedStatement.setString(1, maHoaDon);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Xóa toàn bộ dòng hiện tại trên JTable
                DefaultTableModel tableModel = (DefaultTableModel) view.table.getModel();
                tableModel.setRowCount(0);

                // Thêm dữ liệu mới vào JTable
                int stt = 1;
                while (resultSet.next()) {
                    String maDichVu = resultSet.getString("maDichVu");
                    String tenDichVu = resultSet.getString("tenDichVu");
                    int soLuong = resultSet.getInt("soLuong");
                    double thanhTien = resultSet.getDouble("thanhTien");
                    double thanhTienBHYT = resultSet.getDouble("thanhTienBHYT");

                    tableModel.addRow(new Object[]{stt++, maDichVu, tenDichVu, soLuong, thanhTien, thanhTienBHYT});
                }
            }

            connection.commit(); // Commit giao dịch khi tất cả đều thành công
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

    // Main method để chạy ứng dụng
    public static void main(String[] args) {
        try {
            hoaDonViewBenhNhan view = new hoaDonViewBenhNhan();
            new hoaDonControllerBenhNhan(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
