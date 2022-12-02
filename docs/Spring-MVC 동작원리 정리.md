# Spring-MVC 동작원리 정리

![img.png](img.png)

그림과 함께 `org.springframework.web.servlet.DispatcherServlet` 의 `doDispatch()` 코드를 살펴보면서 동작원리를 정리해보자.
전반적인 내용은 김영한 선생님의 Spring MVC 1편을 참고하였습니다.

#### 1. 핸들러 조회
````java
HandlerExecutionChain mappedHandler = null;
        
try {

    // Determine handler for the current request.
    mappedHandler = getHandler(processedRequest);
    if (mappedHandler == null) {
        noHandlerFound(processedRequest, response);
        return;
    }
````

#### 2. 핸들러를 처리할 수 있는 핸들러 어댑터 조회
````java
// Determine handler adapter for the current request.
HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
````

#### 3. 어댑터에게 핸들러 위임

#### 4. 핸들러 호출
````java
// Actually invoke the handler.
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
````

#### 4-1. `ArgumentResolver` 호출
애노테이션 기반의 컨트롤러는 매우 다양한 파라미터를 사용할 수 있다.
`HttpServletRequest`, `Model`은 물론이고,
`@RequestParam` `@ModelAttribute` 같은 애노테이션 그리고
`@RequestBody`, `HttpEntity` 같은 HTTP 메시지를 처리하는 부분까지
매우 큰 유연함을 보여주었다.

애노테이션 기반 컨트롤러를 처리하는 `RequestMappingHandlerAdapter`는
`ArgumentResolver`를 호출해서 컨트롤러(핸들러)가 필요로 하는 다양한 파라미터의 값(객체)을 생성한다.
이 때, 메시지 바디를 값으로 변환할 때 HTTP 메시지 컨버터가 사용된다.
파라미터의 값이 모두 준비되면 컨트롤러를 호출하면서 값을 넘겨준다.


예를 들어보자.
```
content-type: application/json
@RequestMapping
void hello(@RequestBody String data) {}
```
content-type : Json & 클래스 타입 : String
RequestBody 처리 아규먼트 리졸버 : 요청 바디는 json인데, String 으로 받길 원하네.
스트링 메시지 컨버터로 String 으로 바꿔 파라미터로 넘겨주자.

```
content-type: application/json
@RequestMapping
void hello(@RequestBody HelloData data) {}
```
content-type : Json & 클래스 타입 : 객체
아규먼트 리졸버 : 요청 바디는 json인데, 객체로 받길 원하네. 잭슨 메시지 컨버터를 사용해
요청 바디를 객체로 변환해 파라미터로 넘겨주자.

#### 4-2. 컨트롤러 실행
비즈니스 로직을 수행하고 응답값을 반환

#### 4-3. `ReturnValueHandler` 호출
`HandlerMethodReturnValueHandler`를 줄여서 `ReturnValueHandler`라 부른다.
`ArgumentResolver`와 비슷한데, 이것은 응답 값을 변환하고 처리한다.
컨트롤러에서 `String`으로 뷰 이름을 반환해도, 동작하는 이유가 바로 `ReturnValueHandler` 덕분이다.
스프링은 10여개가 넘는 `ReturnValueHandler`를 지원한다.
예) `ModelAndView`, `ResponseBody`, `HttpEntity`, `String`

예를 들어보자.

```
accept : application / json
@ResponseBody
HelloData hello() {}
```
`ReturnValueHandler` : 클라이언트가 json 으로 받길 원하네, 반환형이 객체구나. 잭슨 메시지 컨버터를 써서
객체를 json 으로 바꿔서 응답 body에 담도록하자.

#### 5. ModelAndView 반환
#### 6. ViewResolver 호출
#### 7. View 반환
#### 8. render(model) 호출
#### HTML 응답이 전송되며 종료

#### 참고링크
- [메시지 컨버터란](https://velog.io/@kiwonkim/MVC1-HTTP-메시지-컨버터)
- [@ResponseBody 실행 흐름](https://www.inflearn.com/questions/422763/responsebody인-경우의-실행흐름이-궁금합니다)
