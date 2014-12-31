
package at.plechinger.demo.scribesec.facebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author lukas
 */
@Controller
public class TestController {

    @RequestMapping("/login-success")
    @ResponseBody
    String home() {
        return "Hello (protected) World!";
    }
    
    @RequestMapping("/login-error")
    @ResponseBody
    String error() {
        return "Error while Authentication!";
    }
}
