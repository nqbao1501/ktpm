package MVC.controller;

import MVC.view.benhNhanView;
import MVC.view.suaBenhNhanView;
import MVC.view.themBenhNhanView;
import javax.swing.*;
import java.sql.*;

public class benhNhanController {
    private Connection connection;
    private benhNhanView view;

    public benhNhanController(benhNhanView view) throws SQLException, ClassNotFoundException {
        this.view = view;

        // Initialize database connection
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=quanLiVienPhi;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "Hoangtien";
        connection = DriverManager.getConnection(url, username, password);

        loadPatientIDs();

        view.searchButton.addActionListener(e -> searchPatient());
        view.addButton.addActionListener(e -> {
            // Mở giao diện thêm bệnh nhân mới
            themBenhNhanView themBenhNhanView = new themBenhNhanView();

            // Khi nhấn Lưu, thêm bệnh nhân vào cơ sở dữ liệu
            themBenhNhanView.saveButton.addActionListener(ev -> {
                addPatientToDatabase(themBenhNhanView);
            });

            // Khi nhấn Hủy, đóng cửa sổ thêm bệnh nhân
            themBenhNhanView.cancelButton.addActionListener(ev -> {
                themBenhNhanView.dispose();
            });
        });
        view.updateButton.addActionListener(e -> {
            String selectedID = (String) view.maBenhNhanBox.getSelectedItem();
            if (selectedID != null) {
                updatePatient(selectedID); // Pass selected patient ID
            } else {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn bệnh nhân cần cập nhật.");
            }
        });
        view.deleteButton.addActionListener(e -> deletePatient());
    }

    private void loadPatientIDs() {
        try (Statement stm = connection.createStatement()) {
            ResultSet resultSet = stm.executeQuery("SELECT maBenhNhan FROM BenhNhan");
            while (resultSet.next()) {
                view.maBenhNhanBox.addItem(resultSet.getString("maBenhNhan"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // hàm tìm kiếm
    private void searchPatient() {
        // Tìm kiếm bệnh nhân theo ID
        String selectedID = (String) view.maBenhNhanBox.getSelectedItem();
        if (selectedID != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM BenhNhan WHERE maBenhNhan = ?")) {
                preparedStatement.setString(1, selectedID);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    view.soCCCDField.setText(resultSet.getString("soCCCD"));
                    view.hoVaTenField.setText(resultSet.getString("hoVaTen"));
                    view.tuoiField.setText(String.valueOf(resultSet.getInt("tuoi")));
                    view.gioiTinhField.setText(resultSet.getString("gioiTinh"));
                    view.soDienThoaiField.setText(resultSet.getString("soDienThoai"));
                    view.soDienThoaiKhanCapField.setText(resultSet.getString("soDienThoaiKhanCap"));
                    view.soBHYTField.setText(resultSet.getString("soBHYT"));
                    view.trangThaiDieuTriField.setText(resultSet.getString("trangThaiDieuTri"));
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy bệnh nhân.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi trong quá trình tìm kiếm.");
                ex.printStackTrace();
            }
        }
    }

    // hàm thêm bệnh nhân
    private void addPatientToDatabase(themBenhNhanView themBenhNhanView) {
        try {
            // Kiểm tra các giá trị nhập vào
            if (themBenhNhanView.soCCCDField.getText().isEmpty() ||
                themBenhNhanView.hoVaTenField.getText().isEmpty() ||
                themBenhNhanView.tuoiField.getText().isEmpty() ||
                themBenhNhanView.gioiTinhField.getText().isEmpty() ||
                themBenhNhanView.soDienThoaiField.getText().isEmpty() ||
                themBenhNhanView.soDienThoaiKhanCapField.getText().isEmpty() ||
                themBenhNhanView.soBHYTField.getText().isEmpty() ||
                themBenhNhanView.trangThaiDieuTriField.getText().isEmpty()) {

                JOptionPane.showMessageDialog(themBenhNhanView, "Vui lòng điền đầy đủ thông tin.");
                return;
            }

            // Lấy giá trị max maBenhNhan để tự động tăng
            int newMaBenhNhan = getNextMaBenhNhan();

            // Câu lệnh SQL để thêm bệnh nhân vào cơ sở dữ liệu
            String query = "INSERT INTO BenhNhan (maBenhNhan, soCCCD, hoVaTen, tuoi, gioiTinh, soDienThoai, soDienThoaiKhanCap, soBHYT, trangThaiDieuTri) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, newMaBenhNhan);
                preparedStatement.setString(2, themBenhNhanView.soCCCDField.getText());
                preparedStatement.setString(3, themBenhNhanView.hoVaTenField.getText());
                preparedStatement.setInt(4, Integer.parseInt(themBenhNhanView.tuoiField.getText()));
                preparedStatement.setString(5, themBenhNhanView.gioiTinhField.getText());
                preparedStatement.setString(6, themBenhNhanView.soDienThoaiField.getText());
                preparedStatement.setString(7, themBenhNhanView.soDienThoaiKhanCapField.getText());
                preparedStatement.setString(8, themBenhNhanView.soBHYTField.getText());
                preparedStatement.setString(9, themBenhNhanView.trangThaiDieuTriField.getText());

                // Thực thi câu lệnh SQL
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(themBenhNhanView, "Thêm bệnh nhân thành công.");
                themBenhNhanView.dispose();  // Đóng cửa sổ thêm bệnh nhân
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(themBenhNhanView, "Lỗi khi thêm bệnh nhân: " + ex.getMessage());
                ex.printStackTrace();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(themBenhNhanView, "Lỗi không xác định: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int getNextMaBenhNhan() throws SQLException {
        int nextID = 1;  // Giá trị mặc định nếu không có dữ liệu
        String query = "SELECT MAX(maBenhNhan) AS maxID FROM BenhNhan";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                nextID = resultSet.getInt("maxID") + 1;
            }
        }
        return nextID;
    }

    // hàm sửa bệnh nhân
    private void updatePatient(String maBenhNhan) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM BenhNhan WHERE maBenhNhan = ?")) {
            preparedStatement.setString(1, maBenhNhan);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String soCCCD = resultSet.getString("soCCCD");
                String hoVaTen = resultSet.getString("hoVaTen");
                int tuoi = resultSet.getInt("tuoi");
                String gioiTinh = resultSet.getString("gioiTinh");
                String soDienThoai = resultSet.getString("soDienThoai");
                String soDienThoaiKhanCap = resultSet.getString("soDienThoaiKhanCap");
                String soBHYT = resultSet.getString("soBHYT");
                String trangThaiDieuTri = resultSet.getString("trangThaiDieuTri");

                // Hiển thị giao diện sửa
                suaBenhNhanView suaBenhNhanView = new suaBenhNhanView(maBenhNhan, soCCCD, hoVaTen, tuoi, gioiTinh, soDienThoai, soDienThoaiKhanCap, soBHYT, trangThaiDieuTri);
                suaBenhNhanView.saveButton.addActionListener(ev -> {
                    // Cập nhật bệnh nhân vào cơ sở dữ liệu
                    updatePatientInDatabase(suaBenhNhanView);
                });

                suaBenhNhanView.cancelButton.addActionListener(ev -> {
                    suaBenhNhanView.dispose();
                });
            } else {
                JOptionPane.showMessageDialog(view, "Không tìm thấy bệnh nhân.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm bệnh nhân.");
            ex.printStackTrace();
        }
    }

    private void updatePatientInDatabase(suaBenhNhanView suaBenhNhanView) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE BenhNhan SET soCCCD = ?, hoVaTen = ?, tuoi = ?, gioiTinh = ?, soDienThoai = ?, soDienThoaiKhanCap = ?, soBHYT = ?, trangThaiDieuTri = ? WHERE maBenhNhan = ?")) {

            preparedStatement.setString(1, suaBenhNhanView.soCCCDField.getText());
            preparedStatement.setString(2, suaBenhNhanView.hoVaTenField.getText());
            preparedStatement.setInt(3, Integer.parseInt(suaBenhNhanView.tuoiField.getText()));
            preparedStatement.setString(4, suaBenhNhanView.gioiTinhField.getText());
            preparedStatement.setString(5, suaBenhNhanView.soDienThoaiField.getText());
            preparedStatement.setString(6, suaBenhNhanView.soDienThoaiKhanCapField.getText());
            preparedStatement.setString(7, suaBenhNhanView.soBHYTField.getText());
            preparedStatement.setString(8, suaBenhNhanView.trangThaiDieuTriField.getText());
            preparedStatement.setString(9, suaBenhNhanView.maBenhNhanField.getText());

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            suaBenhNhanView.dispose(); // Đóng cửa sổ sửa
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật bệnh nhân.");
        }
    }

    // hàm xóa bệnh nhân
    private void deletePatient() {
        String selectedID = (String) view.maBenhNhanBox.getSelectedItem();
        if (selectedID != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM BenhNhan WHERE maBenhNhan = ?")) {
                preparedStatement.setString(1, selectedID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(view, "Xóa bệnh nhân thành công.");
                    // Cập nhật lại danh sách bệnh nhân sau khi xóa
                    view.maBenhNhanBox.removeItem(selectedID);
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy bệnh nhân để xóa.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xóa bệnh nhân.");
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn bệnh nhân để xóa.");
        }
    }

    public static void main(String[] args) {
        try {
            benhNhanView view = new benhNhanView();
            new benhNhanController(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
