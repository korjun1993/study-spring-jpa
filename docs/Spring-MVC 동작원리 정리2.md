# Spring-MVC 동작원리 정리

- [메시지 국제화](#메시지-국제화)
- [검증](#검증)
- [쿠키와 세션](#쿠키와-세션)
- [필터](#필터)
- [인터셉터](#인터셉터)
- [예외 처리](#예외-처리)
- [타입 턴버터](#타입-컨버터)
- [파일 업로드](#파일-업로드)

## 메시지 국제화
메시지 국제화 내용
메시지 국제화 내용
---


##검증

###BindingResult
스프링이 제공하는 **검증 오류를 보관**하는 객체이다.
`BindingResult` 객체는 `Model`에 담겨 서버 코드나 [Thymeleaf 와 같은 템플릿엔진에서 활용]()된다.
검증 오류를 보관하는 방법은 2가지가 있다.

case1. 스프링의 자동화된 검증 로직에 의해 (뒤에서 다룰 예정)

case2. 개발자에 의해
```java
public class MyController {
  public String addItem(@ModelAttribute Item item, BindingResult bindingResult) {
      if(검증오류상황) {
          bindingResult.addError(검증오류); //FieldError 또는 ObjectError
      }
    }
}
```
- 컨트롤러의 파라미터 순서에 주의해야 한다. 검증 대상(=`Item`) 다음에 `BindingResult` 위치
- `검증오류` 객체는 크게 2가지 존재. `FieldError`, `ObjectError`


###FieldError
```java
public class FieldError {
  /**
   * 
   * @param objectName 오류가 발생한 객체 이름
   * @param field 오류 필드
   * @param rejectedValue 사용자가 입력한 값(거절된 값)
   * @param bindingFailure 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
   * @param codes 메시지 코드
   * @param arguments 메시지에서 사용하는 인자
   * @param defaultMessage 기본 오류 메시지
   */
    public FieldError(String objectName, String field, @Nullable Object rejectedValue,
                      boolean bindingFailure, @Nullable String[] codes,
                      @Nullable Object[] arguments, @Nullable String defaultMessage);
}
```
- 특정 필드에 오류가 있을 때, 해당 객체를 생성하여 `BindingResult`에 넣는다.
- `bindingResult.rejectValue()`에는 해당 객체를 만들고 `bindingResult`에 넣는 작업까지 모두 작성되어있다.
- 예시) `요구사항: 가격 >= 1,000, 가격=900`


###ObjectError
```java
public class ObjectError {
  /**
   * 
   * @param objectName 오류가 발생한 객체 이름
   * @param codes 메시지 코드
   * @param arguments 메시지에서 사용하는 인자
   * @param defaultMessage 기본 오류 메시지
   */
    public ObjectError(String objectName, @Nullable String[] codes, @Nullable Object[] arguments, 
                       @Nullable String defaultMessage);
}
```
- 복합적인 오류가 있을 때, 해당 객체를 생성하여 `BindingResult`에 넣는다.
- `bindingResult.reject()`에는 해당 객체를 만들고 `bindingResult`에 넣는 작업까지 모두 작성되어있다.
- 예시) `요구사항: 가격x수량 >= 10,000, 가격=900, 수량=10`


###MessageCodesResolver
`FieldError`, `ObjectError` 의 생성자에는 `codes(메시지코드)`가 있다.
이 값은 오류 메시지를 출력해야하는 경우에 `메시지소스`의 키 값으로 사용된다.
`rejectValue()`, `reject()`는 내부에서 `MessageCodesResolver`를 사용한다.
`MessageCodesResolver`는 `메시지코드`, `필드명`, `객체명`, `타입`을 조합해 새로운 메시지 코드를 생성한다.
정해진 우선 순위에 따라 이 값들을 키로 지정해 오류 메시지를 찾는다. 그리고 없으면 디폴트 메시지를 출력한다.


###Validator
스프링은 검증을 체계적으로 제공하기 위해 `Validator` 인터페이스를 제공한다.
```java
public interface Validator {
    // 이 검증기를 지원하는지 여부 확인
    boolean supports(Class<?> clazz);
    
    // 검증 로직
    // target: 
    // errors: 검증 오류 정보를 담을 객체
    void validate(Object target, Errors errors);
}
```

검증 로직을 담당하는 클래스이다. `Validator` 인터페이스를 구현했다.
```java
@Component
public class MyValidator implements Validator { 
    @Override
    public boolean supports(Class<?> clazz) {
        return 검증대상.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(필드검증오류) {
            errors.rejectValue("필드명", "오류코드", "메시지 매개변수", "기본 메시지");
        }
        
        if (복합검증오류) {
          errors.reject("오류코드", "메시지 매개변수", "기본 메시지");
        }
    }
}
```

검증 로직이 필요한 컨트롤러에서 검증기를 `WebDataBinder`에 등록하면, 자동으로 검증기를 적용할 수 있다.
```java
@Controller
@RequiredArgsConstructor
public class MyController {
    
    //생성자주입
    private final MyValidator myValidator;
  
    //검증기 등록
    @InitBinder 
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(myValidator);
    }
  
    @PostMapping("/add") 
    public String addItem(@Validated @ModelAttribute Target target, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return "addForm";
    }
    
    //성공 로직
    return "items/{itemId}";
  }
}
```
- `WebDataBinder`: 바인딩, 검증을 수행하는 객체
- `@InitBinder`: `WebDataBinder` 초기화 메서드 (해당 컨트롤러에만 영향)
- `@Validated`: 검증기를 실행하라는 애노테이션이다. 이 애노테이션이 붙으면 앞서 `WebDataBinder` 에 등록한 검증기를 찾아서 실행한다. 그런데 여러 검증기를 등록한다면 그 중에 어떤 검증기가 실행되어야 할지 구분이 필요하다. 이때 `supports()` 가 사용된다. 결과가 `true` 이면 `validate()` 가 호출된다.
---


##쿠키와 세션
##필터
##인터셉터
##예외 처리
##파일 업로드
