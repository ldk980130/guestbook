package springpractice.guestbook.controller;

import lombok.RequiredArgsConstructor;
import org.dom4j.rule.Mode;
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
    public String createPostForm(Model model) {
        List<Post> posts = postService.searchAll();
        Collections.reverse(posts);

        model.addAttribute("posts", posts);
        model.addAttribute("form", new PostForm());
        return "home";
    }

    @PostMapping("/")
    public String posting(@Valid PostForm form, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage",
                    "이름과 내용 모두 입력해야 합니다.");
            return "errorpage";
        }

        Post post = Post.createPost(form.getName(), form.getContent());
        try{
            postService.post(post);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage",
                    "같은 이용자가1분 내에 두 번 게시글을 올릴 수 없습니다.");
            return "errorpage";
        }

        return "redirect:/";
    }

    @GetMapping("like/{postId}")
    public String pushLike(@PathVariable Long postId) {
        postService.pushLike(postId);
        return "redirect:/";
    }

    @GetMapping("hate/{postId}")
    public String pushHate(@PathVariable Long postId) {
        Post post = postService.pushHate(postId);

        if (post.getHates() >= 10) {
            postService.delete(post);
        }

        return "redirect:/";
    }

}
