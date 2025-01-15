package MVC.Controller;

import MVC.Model.dichVuModel;
import MVC.View.dichVuView;

import javax.swing.*;
import java.sql.*;

public class dichVuController {
    private Connection connection;
    private dichVuView view;

    public dichVuController(dichVuView view) throws SQLException, ClassNotFoundException {
        this.view = view;

        // Initialize database connection
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=quanLyVienPhi;encrypt=false";
        String username = "sa";
        String password = "1234567890";
        connection = DriverManager.getConnection(url, username, password);

        loadLoaiDichVu();

        // Add action listeners for buttons
        view.btnTimKiem.addActionListener(e -> searchDichVu());
        view.btnThem.addActionListener(e -> themHoacSuaDichVu());
        view.btnXoa.addActionListener(e -> xoaDichVu());
    }

    // Load các loại dịch vụ từ database lên ComboBox
    private void loadLoaiDichVu() {
        try (Statement stm = connection.createStatement()) {
            ResultSet resultSet = stm.executeQuery("SELECT DISTINCT LoaiDichVu FROM DichVu");
            while (resultSet.next()) {
                view.LoaiDichVuComboBox.addItem(resultSet.getString("LoaiDichVu"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Tìm kiếm dịch vụ dựa vào mã dịch vụ
    private void searchDichVu() {
        String maDichVu = view.MaDichVuField.getText();
        if (maDichVu.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã dịch vụ để tìm kiếm.");
            return;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM DichVu WHERE MaDichVu = ?")) {
            preparedStatement.setString(1, maDichVu);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                view.TenDichVuField.setText(resultSet.getString("TenDichVu"));
                view.LoaiDichVuComboBox.setSelectedItem(resultSet.getString("LoaiDichVu"));
                view.GiaTienDichVuField.setText(String.valueOf(resultSet.getDouble("GiaTien")));
                view.HeSoBaoHiemField.setText(String.valueOf(resultSet.getDouble("HeSoBaoHiem")));
            } else {
                JOptionPane.showMessageDialog(view, "Không tìm thấy dịch vụ với mã: " + maDichVu);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm dịch vụ.");
            ex.printStackTrace();
        }
    }

    // Thêm hoặc sửa dịch vụ
    private void themHoacSuaDichVu() {
        String maDichVu = view.MaDichVuField.getText();
        String tenDichVu = view.TenDichVuField.getText();
        String loaiDichVu = (String) view.LoaiDichVuComboBox.getSelectedItem();
        String giaTienStr = view.GiaTienDichVuField.getText();
        String heSoBaoHiemStr = view.HeSoBaoHiemField.getText();

        if (maDichVu.isEmpty() || tenDichVu.isEmpty() || loaiDichVu == null || giaTienStr.isEmpty() || heSoBaoHiemStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        try {
            double giaTien = Double.parseDouble(giaTienStr);
            double heSoBaoHiem = Double.parseDouble(heSoBaoHiemStr);

            // Kiểm tra nếu dịch vụ đã tồn tại
            try (PreparedStatement checkStatement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM DichVu WHERE MaDichVu = ?")) {
                checkStatement.setString(1, maDichVu);
                ResultSet resultSet = checkStatement.executeQuery();
                resultSet.next();

                if (resultSet.getInt(1) > 0) {
                    // Update dịch vụ
                    try (PreparedStatement updateStatement = connection.prepareStatement(
                            "UPDATE DichVu SET TenDichVu = ?, LoaiDichVu = ?, GiaTien = ?, HeSoBaoHiem = ? WHERE MaDichVu = ?")) {
                        updateStatement.setString(1, tenDichVu);
                        updateStatement.setString(2, loaiDichVu);
                        updateStatement.setDouble(3, giaTien);
                        updateStatement.setDouble(4, heSoBaoHiem);
                        updateStatement.setString(5, maDichVu);
                        updateStatement.executeUpdate();
                        JOptionPane.showMessageDialog(view, "Cập nhật dịch vụ thành công.");
                    }
                } else {
                    // Insert dịch vụ mới
                    try (PreparedStatement insertStatement = connection.prepareStatement(
                            "INSERT INTO DichVu (MaDichVu, TenDichVu, LoaiDichVu, GiaTien, HeSoBaoHiem) VALUES (?, ?, ?, ?, ?)")) {
                        insertStatement.setString(1, maDichVu);
                        insertStatement.setString(2, tenDichVu);
                        insertStatement.setString(3, loaiDichVu);
                        insertStatement.setDouble(4, giaTien);
                        insertStatement.setDouble(5, heSoBaoHiem);
                        insertStatement.executeUpdate();
                        JOptionPane.showMessageDialog(view, "Thêm dịch vụ thành công.");
                    }
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Giá tiền và hệ số bảo hiểm phải là số.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm hoặc sửa dịch vụ.");
            ex.printStackTrace();
        }
    }

    // Xóa dịch vụ
    private void xoaDichVu() {
        String maDichVu = view.MaDichVuField.getText();
        if (maDichVu.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã dịch vụ để xóa.");
            return;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM DichVu WHERE MaDichVu = ?")) {
            preparedStatement.setString(1, maDichVu);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(view, "Xóa dịch vụ thành công.");
            } else {
                JOptionPane.showMessageDialog(view, "Không tìm thấy dịch vụ với mã: " + maDichVu);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi xóa dịch vụ.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            dichVuView view = new dichVuView();
            new dichVuController(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
