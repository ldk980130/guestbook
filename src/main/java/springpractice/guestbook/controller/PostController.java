package springpractice.guestbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springpractice.guestbook.Post;
import springpractice.guestbook.PostService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/")
    public String listPosts(Model model) {
        List<Post> posts = postService.searchAll();
        Collections.reverse(posts);

        model.addAttribute("posts", posts);
        return "home";
    }

    @GetMapping("post/new")
    public String createPostForm(Model model) {
        model.addAttribute("form", new PostForm());
        return "createPost";
    }

    @PostMapping("post/new")
    public String posting(@Valid PostForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("form", form);
            model.addAttribute("msg", "이름과 내용 모두 입력해야 합니다.");
            return "createPost";
        }

        Post post = Post.createPost(form.getName(), form.getContent());
        try {
            postService.post(post);
        } catch (IllegalStateException e) {
            model.addAttribute("form", form);
            model.addAttribute("msg", "도배 금지");
            return "createPost";
        }

        return "redirect:/";
    }
}
