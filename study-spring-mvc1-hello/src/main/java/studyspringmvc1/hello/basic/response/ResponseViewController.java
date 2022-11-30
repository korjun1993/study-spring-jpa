package studyspringmvc1.hello.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello";
    }

    @RequestMapping("/response/hello") // 권장하지 않는 방식
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
        // 리턴값이 없으면, 컨트롤러의 URL와 논리적이름이 같은 뷰가 있는지 찾는다.
        // 있다면, 해당 뷰가 랜더링된다.
    }
}
