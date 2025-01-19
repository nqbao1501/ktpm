package MVC.Controller;

import MVC.View.*;

import java.sql.SQLException;

public class bacSiMenuController {
    private bacSiMenuView view;

    public bacSiMenuController(bacSiMenuView view) throws SQLException, ClassNotFoundException {
        this.view = view;
        initController();
    }

    private void initController() {
        // Gắn sự kiện cho các nút
        view.btnTimHoaDon.addActionListener(e -> moQuanLyHoaDon());
        view.btnTimDichVu.addActionListener(e -> moQuanLyDichVu());
    }

    private void moQuanLyHoaDon() {
        try {
            // Khởi tạo hoaDonView và hoaDonController
            hoaDonViewBacSi hoaDonViewBacSi = new hoaDonViewBacSi();
            hoaDonControllerBacSi hoaDonControllerBacSi = new hoaDonControllerBacSi(hoaDonViewBacSi);
            
            // Hiển thị giao diện hóa đơn
            hoaDonViewBacSi.setVisible(true);
            
         
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void moQuanLyDichVu() {
        try {
            // Khởi tạo dichVuView và dichVuController
            dichVuViewBacSi dichVuViewBacSi = new dichVuViewBacSi();
            dichVuControllerBacSi dichVuControllerBacSi = new dichVuControllerBacSi(dichVuViewBacSi);
            
            // Hiển thị giao diện dịch vụ
            dichVuViewBacSi.setVisible(true);
            
           
          
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Khởi tạo giao diện và controller
        	bacSiMenuView menuView = new bacSiMenuView();
            new bacSiMenuController(menuView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
