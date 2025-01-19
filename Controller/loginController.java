package MVC.Controller;

import javax.swing.*;
import java.sql.*;
import MVC.View.*;
import MVC.Controller.*;


public class loginController {
	private Connection connection;
	private loginView view;
	
    public loginController(loginView view) throws SQLException, ClassNotFoundException {
        this.view = view;

        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=quanLyVienPhi;encrypt=false";
        String username = "sa";
        String password = "1234567890";
        connection = DriverManager.getConnection(url, username, password);
        
        view.btnLogin.addActionListener(e-> dangNhap());
        
    }
    private void dangNhap() {
        String tenNguoiDung = view.usernameField.getText().trim();
        String matKhau = view.passwordField.getText().trim();

        if (tenNguoiDung.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        String queryDangNhap = "SELECT maPhanQuyen FROM nguoiDung WHERE tenNguoiDung = ? AND matKhau = ?";
        try (PreparedStatement psDangNhap = connection.prepareStatement(queryDangNhap)) {
            psDangNhap.setString(1, tenNguoiDung);
            psDangNhap.setString(2, matKhau);

            try (ResultSet rsDangNhap = psDangNhap.executeQuery()) {
                if (rsDangNhap.next()) {
                    // Lấy giá trị maPhanQuyen
                    String maPhanQuyen = rsDangNhap.getString("maPhanQuyen");

                    // Kiểm tra quyền và mở menu tương ứng
                    switch (maPhanQuyen) {
                        case "BENHNHAN":
                            openBenhNhanMenu();
                            break;
                        case "NHANVIEN":
                            openNhanVienMenu();
                            break;
                        case "BACSI":
                            openBacSiMenu();
                            break;
                        default:
                            JOptionPane.showMessageDialog(view, "Quyền không xác định!");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Sai tên người dùng hoặc mật khẩu!");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Các phương thức mở menu tương ứng
    private void openBacSiMenu() {
        // Gọi code để mở giao diện chính của bác sĩ
        try {
        	bacSiMenuView bacSinMenuView = new bacSiMenuView();
        	bacSiMenuController bacSiMenuController = new bacSiMenuController(bacSinMenuView);
            
    
        	bacSinMenuView.setVisible(true);
            view.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openNhanVienMenu() {
        try {
        	nhanVienMenuView nhanVienMenuView = new nhanVienMenuView();
        	nhanVienMenuController nhanVienMenuController = new nhanVienMenuController(nhanVienMenuView);
            
      
            nhanVienMenuView.setVisible(true);
            view.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void openBenhNhanMenu() {
        // Gọi code để mở giao diện chính của bác sĩ
        try {
        	benhNhanMenuView benhNhanMenuView = new benhNhanMenuView();
        	benhNhanMenuController benhNhanMenuController = new benhNhanMenuController(benhNhanMenuView);
            
      
        	benhNhanMenuView.setVisible(true);
            view.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
        	loginView view = new loginView();
            new loginController(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
