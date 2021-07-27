package springpractice.guestbook.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class PostForm {

    @NotEmpty(message = "이름은 필수 입니다.")
    private String name;

    @NotEmpty(message = "내용은 필수 입니다.")
    private String content;
}
