package com.example.SmartPhoneHup.DuAn.controller;

import com.example.SmartPhoneHup.DuAn.model.Cart;
import com.example.SmartPhoneHup.DuAn.model.CartItem;
import com.example.SmartPhoneHup.DuAn.model.SanPham;
import com.example.SmartPhoneHup.DuAn.model.SanPhamChiTiet;
import com.example.SmartPhoneHup.DuAn.repository.SanPhamChiTietRepo;
import com.example.SmartPhoneHup.DuAn.repository.SanPhamRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {

    @Autowired
    private SanPhamChiTietRepo spChiTietRepo;

    @Autowired
    private SanPhamRepo sanPhamRepo;

    @GetMapping("/add")
    public String themVaoGio(@RequestParam("id") Integer idSpChiTiet,
                             @RequestParam(defaultValue = "1") int soLuong,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        // Lấy chi tiết sản phẩm từ DB
        Optional<SanPhamChiTiet> optional = spChiTietRepo.findById(idSpChiTiet);
        if (optional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại.");
            return "redirect:/san-pham2/hien-thi";
        }

        SanPhamChiTiet spChiTiet = optional.get();
        SanPham sp = spChiTiet.getSanPham();

        // Lấy giỏ hàng từ session, nếu chưa có thì tạo mới
        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null) {
            cart = new Cart();
        }

        // Tạo item và thêm vào giỏ
        CartItem item = new CartItem(
                spChiTiet.getId(),
                sp.getTen(),
                spChiTiet.getMauSac().getTen(),
                spChiTiet.getKichThuoc().getTen(),
                soLuong,
                spChiTiet.getDonGia(),
                sp.getAnh()  // Thêm ảnh vào CartItem
        );
        cart.addItem(item);

        // Lưu lại vào session
        session.setAttribute("gioHang", cart);
        redirectAttributes.addFlashAttribute("success", "Đã thêm vào giỏ hàng!");

        return "redirect:/gio-hang/hien-thi"; // hoặc redirect về trang chi tiết sản phẩm
    }

    @GetMapping("/hien-thi")
    public String xemGio(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null) cart = new Cart();

        model.addAttribute("cart", cart);
        return "gio_hang/view"; // tạo view này để hiển thị giỏ
    }



    //update
    @GetMapping("/update/{id}")
    public String updateItemQuantity(@PathVariable("id") Integer idSpChiTiet,
                                     @RequestParam("quantity") int quantity,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {

        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn hiện không có sản phẩm nào.");
            return "redirect:/gio-hang/hien-thi";
        }

        // Lấy sản phẩm trong giỏ hàng theo ID
        CartItem item = cart.getItemById(idSpChiTiet);
        if (item == null) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không có trong giỏ hàng.");
            return "redirect:/gio-hang/hien-thi";
        }

        // Kiểm tra số lượng mới và cập nhật
        if (quantity <= 0) {
            redirectAttributes.addFlashAttribute("error", "Số lượng phải lớn hơn 0.");
            return "redirect:/gio-hang/hien-thi";
        }

        // Cập nhật số lượng cho sản phẩm
        item.setSoLuong(quantity);
        cart.updateItem(item);

        // Lưu giỏ hàng vào session
        session.setAttribute("gioHang", cart);
        redirectAttributes.addFlashAttribute("success", "Cập nhật số lượng thành công!");

        return "redirect:/gio-hang/hien-thi";
    }


    //xóa

    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable("id") Integer idSpChiTiet,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn hiện không có sản phẩm nào.");
            return "redirect:/gio-hang/hien-thi";
        }

        // Kiểm tra xem sản phẩm có trong giỏ hàng hay không
        CartItem item = cart.getItemById(idSpChiTiet);
        if (item == null) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không có trong giỏ hàng.");
            return "redirect:/gio-hang/hien-thi";
        }

        // Xóa sản phẩm khỏi giỏ hàng
        cart.removeItem(idSpChiTiet);

        // Lưu giỏ hàng vào session
        session.setAttribute("gioHang", cart);
        redirectAttributes.addFlashAttribute("success", "Sản phẩm đã được xóa khỏi giỏ hàng!");

        return "redirect:/gio-hang/hien-thi";
    }

}

