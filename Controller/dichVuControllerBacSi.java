package MVC.Controller;

import MVC.Model.dichVuModel;
import MVC.View.dichVuViewBacSi;

import javax.swing.*;
import java.sql.*;

public class dichVuControllerBacSi {
    private Connection connection;
    private dichVuViewBacSi view;

    public dichVuControllerBacSi(dichVuViewBacSi view) throws SQLException, ClassNotFoundException {
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

 
    public static void main(String[] args) {
        try {
        	dichVuViewBacSi view = new dichVuViewBacSi();
            new dichVuControllerBacSi(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
