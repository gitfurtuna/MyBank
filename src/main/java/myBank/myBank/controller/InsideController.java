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
            System.out.println(actualUser.getAccountAmount());
            if (user.getCreditLimit() >= amount) {
                userService.getMoney(actualUser, amount);
                model.addAttribute("message", "Transfer successful! Amount transferred: " + amount);
            } else {
                model.addAttribute("message", "Insufficient funds for transfer.");
            }
            response = userService.showUserInfo(actualUser);
            actualUser = response.getUser().get();
            System.out.println(actualUser.getAccountAmount());
            model.addAttribute("user", actualUser);
        }
        model.addAttribute("response", response);
        return "client";
    }
}
