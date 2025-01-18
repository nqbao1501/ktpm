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
        view.btnXoaDichVu.addActionListener(e-> xoaDichVu()); 
        view.btnLuuHoaDon.addActionListener(e-> luuHoaDon());
        view.btnTimKiemHoaDon.addActionListener(e->timHoaDon());
        view.btnXoaHoaDon.addActionListener(e->xoaHoaDon());
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
            String queryCheckDichVu = "SELECT giaTien, heSoBaoHiem , tenDichVu FROM dichVu WHERE maDichVu = ?";
            try (PreparedStatement psCheckDichVu = connection.prepareStatement(queryCheckDichVu)) {
                psCheckDichVu.setString(1, maDichVu);
                ResultSet rs = psCheckDichVu.executeQuery();

                if (rs.next()) {
                    double giaTien = rs.getDouble("giaTien");
                    double heSoBaoHiem = rs.getDouble("heSoBaoHiem");
                    String tenDichVu = rs.getString("tenDichVu");

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
                                psInsertChiTiet.setString(4, maDichVu);
                                psInsertChiTiet.setString(5, maHoaDon);
  
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

        
                    // Update JTable (thay thế thay vì cộng thêm)
                    DefaultTableModel model = (DefaultTableModel) view.table.getModel();
                    boolean found = false;

                    for (int i = 0; i < model.getRowCount(); i++) {
                        String existingMaDichVu = (String) model.getValueAt(i, 1);
                        if (existingMaDichVu.equals(maDichVu)) {
                            // Nếu tìm thấy, thay thế (update) các giá trị
                        	model.setValueAt(tenDichVu, i, 2);
                            model.setValueAt(soLuong, i, 3);  // Thay thế số lượng
                            model.setValueAt(thanhTien, i, 4);  // Thay thế thành tiền
                            model.setValueAt(thanhTienBHYT, i, 5);  // Thay thế thành tiền BHYT
                            found = true;
                            break;
                        }
                    }

                    // Nếu không tìm thấy, thêm một dòng mới vào JTable
                    if (!found) {
                        model.addRow(new Object[]{
                                model.getRowCount() + 1,  // STT (Tự động tăng)
                                maDichVu,                 // Mã dịch vụ
                                tenDichVu,                       // Tên dịch vụ
                                soLuong,                  // Số lượng
                                thanhTien,                // Thành tiền
                                thanhTienBHYT             // Thành tiền BHYT
                        });
                    }
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

    private void xoaDichVu() {
        // Lấy dòng được chọn trong JTable
        int selectedRow = view.table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dịch vụ cần xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác nhận trước khi xóa
        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Bạn có chắc chắn muốn xóa dịch vụ này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Lấy mã hóa đơn và mã dịch vụ từ JTable
        String maHoaDon = view.MaHoaDonField.getText().trim();
        String maDichVu = (String) view.table.getValueAt(selectedRow, 1); // Cột mã dịch vụ

        try {
            // Bắt đầu giao dịch
            connection.setAutoCommit(false);

            // Xóa dịch vụ trong cơ sở dữ liệu
            String queryDeleteChiTiet = "DELETE FROM chiTietHoaDon WHERE maHoaDon = ? AND maDichVu = ?";
            try (PreparedStatement psDeleteChiTiet = connection.prepareStatement(queryDeleteChiTiet)) {
                psDeleteChiTiet.setString(1, maHoaDon);
                psDeleteChiTiet.setString(2, maDichVu);
                psDeleteChiTiet.executeUpdate();
            }

            // Xóa dòng khỏi JTable
            DefaultTableModel model = (DefaultTableModel) view.table.getModel();
            model.removeRow(selectedRow);

            // Cập nhật lại số thứ tự (STT) trong JTable
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(i + 1, i, 0); // Cột STT là cột đầu tiên
            }

            // Commit giao dịch
            connection.commit();
            JOptionPane.showMessageDialog(view, "Dịch vụ đã được xóa thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi xóa dịch vụ. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void timHoaDon() {
        String maHoaDon = view.MaHoaDonField.getText().trim();
        if (maHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã hoá đơn.");
            return;
        }

        try {
            connection.setAutoCommit(false); // Tắt auto-commit

            // Kiểm tra mã hóa đơn tồn tại
            String queryCheckHoaDon = "SELECT COUNT(*) FROM hoaDon WHERE maHoaDon = ?";
            try (PreparedStatement psCheckHoaDon = connection.prepareStatement(queryCheckHoaDon)) {
                psCheckHoaDon.setString(1, maHoaDon);
                ResultSet rsCheck = psCheckHoaDon.executeQuery();
                rsCheck.next();

                if (rsCheck.getInt(1) > 0) {
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
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy hóa đơn với mã: " + maHoaDon, "Thông báo", JOptionPane.WARNING_MESSAGE);
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

    private void luuHoaDon() {
    	//Xem tất cả mọi text field đã được điền chưa?
        String maHoaDon = view.MaHoaDonField.getText().trim();
        String maBenhNhan = view.MaBenhNhanField.getText().trim();
        String ngayTaoHoaDon = view.NgayTaoHoaDonField.getText().trim();
        String hinhThucThanhToan = view.HinhThucThanhToanField.getText().trim();
        
        if (maHoaDon.isEmpty() || maBenhNhan.isEmpty() || ngayTaoHoaDon.isEmpty() || hinhThucThanhToan.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin hóa đơn");
            return;
        }
    	String queryTimChiTietHoaDon = "SELECT chiTietHoaDon.thanhTien, chiTietHoaDon.thanhTienBHYT FROM chiTietHoaDon where chiTietHoaDon.maHoaDon = ?" ;
    	try (PreparedStatement psTimChiTietHoaDon = connection.prepareStatement(queryTimChiTietHoaDon)) {
    		psTimChiTietHoaDon.setString(1, maHoaDon);
            ResultSet resultSet = psTimChiTietHoaDon.executeQuery(); 
            
            double tongTienBHYT = 0.0;
            double tongTienDichVu = 0.0;
            while (resultSet.next()) {
            double thanhTien = resultSet.getDouble("thanhTien");
            double thanhTienBHYT = resultSet.getDouble("thanhTienBHYT");

            // Cộng dồn tổng tiền và tổng tiền BHYT
            tongTienDichVu += thanhTien;
            tongTienBHYT += thanhTienBHYT;
            }

            // Cập nhật tổng tiền và tổng tiền BHYT vào bảng hóa đơn
            double tongTienThanhToan = tongTienDichVu - tongTienBHYT;
            String queryUpdateHoaDon = "UPDATE hoaDon " +
                                       "SET  tongTienBHYT = ?, tongTienDichVu = ?, maBenhNhan = ?, ngayLapHoaDon = ?, hinhThucThanhToan = ?, tongTienThanhToan =? " +
                                       "WHERE maHoaDon = ?";

            try (PreparedStatement psUpdateHoaDon = connection.prepareStatement(queryUpdateHoaDon)) {
                psUpdateHoaDon.setDouble(1, tongTienBHYT);
                psUpdateHoaDon.setDouble(2, tongTienDichVu);
                psUpdateHoaDon.setString(3, maBenhNhan);
                psUpdateHoaDon.setString(4, ngayTaoHoaDon);
                psUpdateHoaDon.setString(5, hinhThucThanhToan);
                psUpdateHoaDon.setDouble(6, tongTienThanhToan);
                psUpdateHoaDon.setString(7, maHoaDon);

                psUpdateHoaDon.executeUpdate();
            }
            view.TongtienDichVuField.setText(String.valueOf(tongTienDichVu));
            view.TongTienBHYTField.setText(String.valueOf(tongTienBHYT));
            view.TongTienThanhToanField.setText(String.valueOf(tongTienThanhToan));
  
            
            // Thông báo thành công
            JOptionPane.showMessageDialog(view, "Hóa đơn đã được lưu thành công");
        } catch (SQLException e) {
            // Xử lý lỗi kết nối hoặc câu lệnh SQL
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi lưu hóa đơn: " + e.getMessage());
        }
    }

    private void xoaHoaDon() {
        String maHoaDon = view.MaHoaDonField.getText().trim();
        
        if (maHoaDon.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập mã hóa đơn để xóa.");
            return;
        }
        
        // Câu lệnh SQL để xóa các chi tiết hóa đơn liên quan đến mã hóa đơn
        String queryXoaChiTietHoaDon = "DELETE FROM chiTietHoaDon WHERE maHoaDon = ?";
        // Câu lệnh SQL để xóa hóa đơn
        String queryXoaHoaDon = "DELETE FROM hoaDon WHERE maHoaDon = ?";
        
        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);  // Tắt Auto Commit để làm việc với giao dịch
            
            // Xóa các chi tiết hóa đơn trước
            try (PreparedStatement psXoaChiTiet = connection.prepareStatement(queryXoaChiTietHoaDon)) {
                psXoaChiTiet.setString(1, maHoaDon);
                psXoaChiTiet.executeUpdate();
            }

            // Sau khi xóa chi tiết hóa đơn, xóa hóa đơn
            try (PreparedStatement psXoaHoaDon = connection.prepareStatement(queryXoaHoaDon)) {
                psXoaHoaDon.setString(1, maHoaDon);
                psXoaHoaDon.executeUpdate();
            }

            // Commit giao dịch
            connection.commit();
            
            // Thông báo xóa thành công
            JOptionPane.showMessageDialog(view, "Hóa đơn và chi tiết hóa đơn đã được xóa thành công.");
            
            // Xóa tất cả các trường text
            view.MaHoaDonField.setText("");
            view.MaBenhNhanField.setText("");
            view.NgayTaoHoaDonField.setText("");
            view.HinhThucThanhToanField.setText("");

            // Clear bảng JTable
            DefaultTableModel model = (DefaultTableModel) view.table.getModel();
            model.setRowCount(0); // Xóa tất cả các dòng trong bảng
        } catch (SQLException e) {
            // Nếu có lỗi, rollback tất cả các thay đổi
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            // Xử lý lỗi nếu có
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi xóa hóa đơn: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);  // Bật lại AutoCommit sau khi kết thúc
            } catch (SQLException e) {
                e.printStackTrace();
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
