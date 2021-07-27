package springpractice.guestbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String createPostForm(Model model) {
        List<Post> posts = postService.searchAll();
        Collections.reverse(posts);

        model.addAttribute("posts", posts);
        model.addAttribute("form", new PostForm());
        return "home";
    }

    @PostMapping("/")
    public String posting(@Valid PostForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "errorpage";
        }

        Post post = Post.createPost(form.getName(), form.getContent());
        postService.post(post);
        return "redirect:/";
    }

}
