import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @RequestMapping(value = "/test")
    public void test(@RequestBody List<User> list){
        for (User u:list
             ) {
            System.out.println(u);
        }
    }
    @RequestMapping(value = "tt")
    public void tt(){
        System.out.println("00000000000000000");
    }
}
