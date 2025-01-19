package MVC.Controller;

import MVC.View.*;

import java.sql.SQLException;

public class benhNhanMenuController {
    private benhNhanMenuView view;

    public benhNhanMenuController(benhNhanMenuView view) throws SQLException, ClassNotFoundException {
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
            hoaDonViewBenhNhan hoaDonViewBenhNhan = new hoaDonViewBenhNhan();
            hoaDonControllerBenhNhan hoaDonControllerBenhNhan = new hoaDonControllerBenhNhan(hoaDonViewBenhNhan);
            
            // Hiển thị giao diện hóa đơn
            hoaDonViewBenhNhan.setVisible(true);
            
         
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void moQuanLyDichVu() {
        try {
            // Khởi tạo dichVuView và dichVuController
            dichVuViewBenhNhan dichVuViewBenhNhan = new dichVuViewBenhNhan();
            dichVuControllerBenhNhan dichVuControllerBenhNhan = new dichVuControllerBenhNhan(dichVuViewBenhNhan);
            
            // Hiển thị giao diện dịch vụ
            dichVuViewBenhNhan.setVisible(true);
            
           
          
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // Khởi tạo giao diện và controller
        	benhNhanMenuView menuView = new benhNhanMenuView();
            new benhNhanMenuController(menuView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
