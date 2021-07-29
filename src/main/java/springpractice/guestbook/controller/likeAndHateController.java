package springpractice.guestbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springpractice.guestbook.Post;
import springpractice.guestbook.PostService;

@Controller
@RequiredArgsConstructor
public class likeAndHateController {

    private final PostService postService;

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
