package myBank.myBank.controller;
import myBank.myBank.model.User;
import myBank.myBank.model.UserInfoResponse;
import myBank.myBank.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
public class InsideController {

    @Autowired
    private UserService userService;

    @GetMapping("/client")
    public String helloClient(Model model, @SessionAttribute("user") User user) {
        UserInfoResponse response = userService.showUserInfo(user);
        if (response.getUser().isPresent()) {
            User actualUser = response.getUser().get();
            model.addAttribute("user", actualUser);
        }
            model.addAttribute("response", response);
            return "client";
        }

    @GetMapping("/admin")
    public String helloAdmin(Model model, @SessionAttribute("user") User user) {
        UserInfoResponse response = userService.showUserInfo(user);
        model.addAttribute("response", response);
        return "admin";
    }


    @GetMapping("/owner")
    public String helloOwner(Model model, @SessionAttribute("user") User user) {
        UserInfoResponse response = userService.showUserInfo(user);
        model.addAttribute("response", response);
        return "owner";
    }

    @GetMapping("/transfer")
    public String transferMoney(Model model, @SessionAttribute("user") User user, @RequestParam("amount") int amount) {
        UserInfoResponse response = userService.showUserInfo(user);
        if (response.getUser().isPresent()) {
            User actualUser = response.getUser().get();
            if (actualUser.getCreditLimit() >= amount) {
                userService.getMoney(actualUser, amount);
                model.addAttribute("message", "Transfer successful! Amount transferred: " + amount);
            } else {
                model.addAttribute("message", "Insufficient funds for transfer.");
            }
            response = userService.showUserInfo(actualUser);
            actualUser = response.getUser().get();
            model.addAttribute("user", actualUser);
        }
        model.addAttribute("response", response);
        return "client";
    }

     @GetMapping("/change_phone")
    public String changePhone(Model model, @SessionAttribute("user") User user, @RequestParam("phone") String phone) {
        UserInfoResponse response = userService.showUserInfo(user);
        User actualUser = response.getUser().get();
        if (response.getUser().isPresent()) {
            actualUser = response.getUser().get();
            userService.changePhoneNumber(actualUser,phone);
                model.addAttribute("message", "Change successful! Phone changed: " + phone);
            }

            response = userService.showUserInfo(actualUser);
            actualUser = response.getUser().get();
            model.addAttribute("user", actualUser);

        model.addAttribute("response", response);
        return "client";
    }

    @GetMapping("/change_email")
    public String changeEmail(Model model, @SessionAttribute("user") User user, @RequestParam("email") String email) {
        UserInfoResponse response = userService.showUserInfo(user);
        User actualUser = response.getUser().get();
        if (response.getUser().isPresent()) {
            actualUser = response.getUser().get();
            userService.changeEmail(actualUser,email);
            model.addAttribute("message", "Change successful! Email changed: " + email);
        }

        response = userService.showUserInfo(actualUser);
        actualUser = response.getUser().get();
        model.addAttribute("user", actualUser);

        model.addAttribute("response", response);
        return "client";
    }

    @GetMapping("/change_password")
    public String changePassword(Model model, @SessionAttribute("user") User user, @RequestParam("password") String password) {
        UserInfoResponse response = userService.showUserInfo(user);
        User actualUser = response.getUser().get();
        if (response.getUser().isPresent()) {
            actualUser = response.getUser().get();
            userService.changePassword(actualUser,password);
            model.addAttribute("message", "Change successful! Password changed: " + password);
        }

        response = userService.showUserInfo(actualUser);
        actualUser = response.getUser().get();
        model.addAttribute("user", actualUser);

        model.addAttribute("response", response);
        return "client";
    }
}
