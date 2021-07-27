package springpractice.guestbook;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostRepositoryTest {

    @Autowired PostRepository postRepository;

    @Test
    public void 게시글저장() throws Exception {
        //given
        Post post = Post.createPost("user", "hello");

        //when
        postRepository.save(post);

        //then
        assertThat(postRepository.findById(post.getId())).isEqualTo(post);
    }

    @Test
    public void 전체검색() throws Exception {
        //given
        Post post1 = Post.createPost("user1", "hello");
        Post post2 = Post.createPost("user2", "hello");
        Post post3 = Post.createPost("user1", "hello");

        //when
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        List<Post> all = postRepository.findAll();

        //then
        assertThat(all.size()).isEqualTo(3);
    }

    @Test
    public void 이름으로검색() throws Exception {
        //given
        Post post1 = Post.createPost("user", "hello");
        Post post2 = Post.createPost("user", "hello");
        Post post3 = Post.createPost("user1", "hello");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        //when
        List<Post> posts = postRepository.findByName("user");

        //then
        assertThat(posts.size()).isEqualTo(2);
    }

    @Test
    public void 게시글삭제() throws Exception {
        //given
        Post post1 = Post.createPost("user", "hello");
        Post post2 = Post.createPost("user", "hello");
        Post post3 = Post.createPost("user1", "hello");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        //when
        postRepository.delete(post1);

        //then
        assertThat(postRepository.findAll().size()).isEqualTo(2);
    }

}