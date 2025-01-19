package MVC.Controller;

import MVC.View.*;

import java.sql.SQLException;

public class nhanVienMenuController {
    private nhanVienMenuView view;

    public nhanVienMenuController(nhanVienMenuView view) throws SQLException, ClassNotFoundException {
        this.view = view;
        initController();
    }

    private void initController() {
        // Gắn sự kiện cho các nút
        view.btnQuanLyHoaDon.addActionListener(e -> moQuanLyHoaDon());
        view.btnQuanLyDichVu.addActionListener(e -> moQuanLyDichVu());
    }

    private void moQuanLyHoaDon() {
        try {
            // Khởi tạo hoaDonView và hoaDonController
            hoaDonView hoaDonView = new hoaDonView();
            hoaDonController hoaDonController = new hoaDonController(hoaDonView);
            
            // Hiển thị giao diện hóa đơn
            hoaDonView.setVisible(true);
            
         
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void moQuanLyDichVu() {
        try {
            // Khởi tạo dichVuView và dichVuController
            dichVuView dichVuView = new dichVuView();
            dichVuController dichVuController = new dichVuController(dichVuView);
            
            // Hiển thị giao diện dịch vụ
            dichVuView.setVisible(true);
            
           
          
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Khởi tạo giao diện và controller
            nhanVienMenuView menuView = new nhanVienMenuView();
            new nhanVienMenuController(menuView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
