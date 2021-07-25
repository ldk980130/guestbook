package springpractice.guestbook;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired PostService postService;

    @Test
    public void 게시글저장_다른이름() throws Exception {
        //given
        Post post1 = Post.createPost("user1", "hello");
        Post post2 = Post.createPost("user2", "hello");
        Post post3 = Post.createPost("user3", "hello");
        Post post4 = Post.createPost("user4", "hello");

        //when
        postService.post(post1);
        postService.post(post2);
        postService.post(post3);
        postService.post(post4);

        //then
        assertThat(postService.searchAll().size()).isEqualTo(4);
        assertThat(postService.searchById(post1.getId()).get()).isEqualTo(post1);
    }

    @Test
    public void 이름으로검색() throws Exception {
        //given
        Post post1 = Post.createPost("user", "hello");
        Post post2 = Post.createPost("user1", "hello");

        //when
        postService.post(post1);
        postService.post(post2);

        //then
        Optional<Post> user = postService.latestPostBySameName("user");
        assertThat(user.get()).isEqualTo(post1);
    }

    @Test(expected = IllegalStateException.class)
    public void 도배확인() throws Exception {
        //given
        Post post1 = Post.createPost("user", "hello");
        Thread.sleep(5000);
        Post post2 = Post.createPost("user", "hello");

        //when
        postService.post(post1);
        postService.post(post2);

        //then
    }

    @Test
    public void 같은이름_최근게시물_검색() throws Exception {
        //given
        Post post1 = Post.createPost("user", "hello");
        Thread.sleep(60001);
        Post post2 = Post.createPost("user", "hello");
        Thread.sleep(60001);
        Post post3 = Post.createPost("user", "hello");
        Thread.sleep(60001);

        //when
        postService.post(post1);
        postService.post(post2);
        postService.post(post3);

        //then
        assertThat(postService.latestPostBySameName("user").get()).isEqualTo(post3);
    }

    @Test
    public void 게시글_삭제() throws Exception {
        //given
        Post post1 = Post.createPost("user1", "hello");
        Post post2 = Post.createPost("user2", "hello");
        Post post3 = Post.createPost("user3", "hello");
        postService.post(post1);
        postService.post(post2);
        postService.post(post3);

        //when
        postService.delete(post1);

        //then
        assertThat(postService.searchAll().size()).isEqualTo(2);
    }
}