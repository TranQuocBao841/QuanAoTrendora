package com.example.Trendora.DuAn.controller;


import com.example.Trendora.DuAn.model.Cart;
import com.example.Trendora.DuAn.model.CartItem;
import com.example.Trendora.DuAn.model.SanPham;
import com.example.Trendora.DuAn.repository.SanPhamRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/gio-hang")
public class GioHangController {


    @Autowired
    private SanPhamRepo sanPhamRepo;



    @GetMapping("/gio-hang")
    public String show (){
        return "gio_hang/view1";
    }



    @GetMapping("/add")
    public String themVaoGio(@RequestParam("id") Integer idSanPham,
                             @RequestParam(defaultValue = "1") int soLuong,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        Optional<SanPham> optional = sanPhamRepo.findById(idSanPham);
        if (optional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại.");
            return "redirect:/san-pham/hien-thi";
        }

        SanPham sp = optional.get();

        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null) {
            cart = new Cart();
        }

        CartItem item = new CartItem(
                sp.getId(),
                sp.getTenSanPham(),
                null,             // Không có màu sắc
                null,             // Không có kích thước
                soLuong,
                sp.getGia(),   // Giá sản phẩm
                sp.getAnhSanPham()       // Ảnh sản phẩm
        );

        cart.addItem(item);
        session.setAttribute("gioHang", cart);
        redirectAttributes.addFlashAttribute("success", "Đã thêm vào giỏ hàng!");

        return "redirect:/gio-hang/hien-thi";
    }

    @GetMapping("/hien-thi")
    public String xemGio(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("gioHang");

        if (cart == null) {
            cart = new Cart(); // tạo cart mới nếu chưa có
            session.setAttribute("gioHang", cart);
        }

        model.addAttribute("cart", cart);
        return "gio_hang/view1";
    }



//    @GetMapping("/update/{id}")
//    public String updateItemQuantity(@PathVariable("id") Integer idSanPham,
//                                     @RequestParam("quantity") int quantity,
//                                     HttpSession session,
//                                     RedirectAttributes redirectAttributes) {
//
//        Cart cart = (Cart) session.getAttribute("gioHang");
//        if (cart == null) {
//            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn hiện không có sản phẩm nào.");
//            return "redirect:/gio-hang/hien-thi";
//        }
//
//        CartItem item = cart.getItemById(idSanPham);
//        if (item == null) {
//            redirectAttributes.addFlashAttribute("error", "Sản phẩm không có trong giỏ hàng.");
//            return "redirect:/gio-hang/hien-thi";
//        }
//
//        if (quantity <= 0) {
//            redirectAttributes.addFlashAttribute("error", "Số lượng phải lớn hơn 0.");
//            return "redirect:/gio-hang/hien-thi";
//        }
//
//        SanPham sp = sanPhamRepo.findById(idSanPham).orElse(null);
//        if (sp == null) {
//            redirectAttributes.addFlashAttribute("error", "Không tìm thấy sản phẩm.");
//            return "redirect:/gio-hang/hien-thi";
//        }
//
//        if (quantity > sp.getSoLuong()) {
//            redirectAttributes.addFlashAttribute("error", "Số lượng yêu cầu vượt quá tồn kho (" + sp.getSoLuong() + ").");
//            return "redirect:/gio-hang/hien-thi";
//        }
//
//        item.setSoLuong(quantity);
//        cart.updateItem(item);
//        session.setAttribute("gioHang", cart);
//        redirectAttributes.addFlashAttribute("success", "Cập nhật số lượng thành công!");
//
//        return "redirect:/gio-hang/hien-thi";
//    }




    @PostMapping("/update-all")
    public String updateAll(@RequestParam("ids") List<Integer> ids,
                            @RequestParam("quantities") List<Integer> quantities,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Cart cart = (Cart) session.getAttribute("gioHang");
        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder();

        if (cart != null) {
            for (int i = 0; i < ids.size(); i++) {
                Integer id = ids.get(i);
                Integer newQuantity = quantities.get(i);

                CartItem item = cart.getItems().get(id);
                if (item != null) {
                    int soLuongTon = sanPhamRepo.findById(id).get().getSoLuong(); // Lấy tồn kho từ DB

                    if (newQuantity > soLuongTon) {
                        hasError = true;
                        errorMsg.append("Sản phẩm '").append(item.getTenSanPham())
                                .append("' chỉ còn ").append(soLuongTon)
                                .append(" sản phẩm trong kho.");
                    } else {
                        item.setSoLuong(newQuantity);
                    }
                }
            }
            session.setAttribute("gioHang", cart); // Cập nhật lại session
        }

        if (hasError) {
            redirectAttributes.addFlashAttribute("error", errorMsg.toString());
        } else {
            redirectAttributes.addFlashAttribute("success", "Cập nhật giỏ hàng thành công.");
        }

        return "redirect:/gio-hang/hien-thi";
    }


    @GetMapping("/remove/{id}")
    public String removeItem(@PathVariable("id") Integer idSanPham,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        Cart cart = (Cart) session.getAttribute("gioHang");
        if (cart == null) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn hiện không có sản phẩm nào.");
            return "redirect:/gio-hang/hien-thi";
        }

        CartItem item = cart.getItemById(idSanPham);
        if (item == null) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không có trong giỏ hàng.");
            return "redirect:/gio-hang/hien-thi";
        }

        cart.removeItem(idSanPham);
        session.setAttribute("gioHang", cart);
        redirectAttributes.addFlashAttribute("success", "Sản phẩm đã được xóa khỏi giỏ hàng!");

        return "redirect:/gio-hang/hien-thi";
    }

    @PostMapping("/xoa-tat-ca")
    public String xoaTatCa(HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("gioHang");

        if (cart != null) {
            cart.getItems().clear(); // Xóa toàn bộ sản phẩm
            session.setAttribute("gioHang", cart); // Cập nhật lại session
        }

        redirectAttributes.addFlashAttribute("success", "Đã xóa toàn bộ sản phẩm trong giỏ hàng.");
        return "redirect:/gio-hang/hien-thi";
    }

}

