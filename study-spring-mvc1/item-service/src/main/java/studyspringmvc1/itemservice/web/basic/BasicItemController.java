package studyspringmvc1.itemservice.web.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import studyspringmvc1.itemservice.domain.item.Item;
import studyspringmvc1.itemservice.domain.item.ItemRepository;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
//    public String addItem(Item item) {
//        itemRepository.save(item);
////        key값: 클래스이름에서 첫글자를 소문자로 변경 (Item -> item)
////        value값: 객체
////        model.addAttribute("item", item); // 이 코드가 자동으로 추가된다.
//        return "basic/item";
//    }

    // PRG 패턴, POST요청 중복 방지
    @PostMapping("/add")
    public String addItemV2(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }
}
