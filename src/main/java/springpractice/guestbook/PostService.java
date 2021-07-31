package springpractice.guestbook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void post(Post post) {

        Optional<Post> findPost = latestPostBySameName(post.getName());

        if (!post.canPost(findPost)) {
            throw new IllegalStateException("같은 회원이 1분 내에 2번 이상 글을 쓸 수 없습니다");
        }

        postRepository.save(post);
    }

    public Optional<Post> latestPostBySameName(String name) {
        List<Post> posts = postRepository.findByName(name);

        if (posts.isEmpty()) return Optional.empty();

        return Optional.ofNullable(posts.get(posts.size() - 1));
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }

    public Post searchById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> searchAll() {
        return postRepository.findAll();
    }

    @Transactional
    public Post pushLike(Long postId) {
        Post post = postRepository.findById(postId);
        post.addLike();
        return post;
    }

    @Transactional
    public Post pushHate(Long postId) {
        Post post = postRepository.findById(postId);
        post.addHate();
        return post;
    }

}
