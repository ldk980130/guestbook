package springpractice.guestbook;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;


public class PostTest {

    @Test
    public void 도배확인_1분이상() throws Exception {
        //given
        Post postFirst = Post.createPost("user", "abcd");
        Thread.sleep(65000);
        Post postLast = Post.createPost("user", "abcd");

        //when
        boolean result = postLast.canPost(postFirst);

        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void 도배확인_1분이하() throws Exception {
        //given
        Post postFirst = Post.createPost("user", "abcd");
        Thread.sleep(5000);
        Post postLast = Post.createPost("user", "abcd");

        //when
        boolean result = postLast.canPost(postFirst);

        //then
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void 좋아요() throws Exception {
        //given
        Post post = Post.createPost("user", "abcd");

        //when
        post.addLike();
        post.addHate();

        //then
        assertThat(post.getLikes()).isEqualTo(1);
        assertThat(post.getHates()).isEqualTo(1);
    }
}