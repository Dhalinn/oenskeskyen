package Wishlist.M.controller;

import Wishlist.M.model.Wishlist;
import Wishlist.M.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlist/create")
    public String showCreateWishlistForm() {
        return "create-wishlist";
    }

    @PostMapping("/wishlist/create")
    public String createWishlist(@RequestParam String name, HttpSession session) {
        // hent brugers id
        Long userId = (Long) session.getAttribute("userId");

        // check bruger er logged in
        if (userId == null) {
            return "redirect:/login"; // Redirect til login side, hvis ikke konto
        }

        // Opret ny wishlist
        wishlistService.createWishlist(name, userId);



        // Redirect til dashboard
        return "redirect:/dashboard";
    }


    @GetMapping("/wishlist/view/{id}")
    public String viewWishlist(@PathVariable Long id, Model model) {
        Wishlist wishlist = wishlistService.getWishlistById(id);
        model.addAttribute("wishlist", wishlist);
        return "view-wishlist";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Sletter session
        return "redirect:/login"; // Redirect to login/ home page
    }

}





