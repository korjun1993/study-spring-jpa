package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationControllerTest {

    static String thymeLeafReturnFieldValue(BindingResult bindingResult, String fieldName, Item model) {
        // 오류가 있는지 확인한다.
        if (bindingResult.hasErrors()) {
            return String.valueOf(bindingResult.getFieldError(fieldName).getRejectedValue());
        }

        // 오류가 없다면 정상 값을 출력한다.
        return String.valueOf(model.getPrice());
    }

    @Test
    void bindingResultTestError() {
        // 오류 케이스
        Item model = new Item();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "item");
        bindingResult.addError(new FieldError("item", "price", "qq", false, null, null, "상품 이름은 필수입니다."));
        Assertions.assertThat(thymeLeafReturnFieldValue(bindingResult, "price", model)).isEqualTo("qq");
    }

    @Test
    void bindingResultTest() {
        Item model = new Item("itemA", 1000, 10);
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "item");
        Assertions.assertThat(thymeLeafReturnFieldValue(bindingResult, "price", model)).isEqualTo("1000");
    }
}
