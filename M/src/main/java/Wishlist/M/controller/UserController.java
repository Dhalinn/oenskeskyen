package Wishlist.M.controller;

import Wishlist.M.model.User;
import Wishlist.M.service.UserService;
import Wishlist.M.service.WishlistService;
import Wishlist.M.model.Wishlist;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, WishlistService wishlistService) {
        this.userService = userService;
        this.wishlistService = wishlistService;
    }


    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String firstName, @RequestParam String lastName,
                               @RequestParam String email, @RequestParam String password,
                               Model model, RedirectAttributes redirectAttributes) {

        // Check if email is already registered
        if (userService.isEmailRegistered(email)) {
            model.addAttribute("", true);
            model.addAttribute("error", "Denne mail er allerade registret.");
            return "register";
        }

        // Valider password
        if (!userService.isValidPassword(password)) {
            model.addAttribute("", true);
            model.addAttribute("error", "Adgangskoden skal være på mindst 8 tegn og indeholde både bogstaver og tal.");
            return "register";
        }

        // Regrister bruger
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        boolean registered = userService.registerUser(user);
        if (!registered) {
            model.addAttribute("error", "Registreringen mislykkedes. E-mailen er allerede registreret eller adgangskoden er ugyldig.");
            return "register";
        }


        redirectAttributes.addFlashAttribute("success", "Registreringen gik igennem Du kan nu logge ind.");

        return "redirect:/dashboard"; // Redirect til login
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.authenticateUser(email, password);
        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getFirstName());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "ugyldig e-mail eller password.");
            return "login";
        }
    }


    private final WishlistService wishlistService; //

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/login"; // Redirect til login
        }

        // Hent brugers wishlist
        List<Wishlist> wishlists = wishlistService.getWishlistsByUserId(userId);
        String username = (String) session.getAttribute("username");

        model.addAttribute("username", username);
        model.addAttribute("wishlists", wishlists);


        return "dashboard";
    }


}
