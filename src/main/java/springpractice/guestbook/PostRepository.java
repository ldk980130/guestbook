package springpractice.guestbook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public Post findById(Long id) {
        Post post = em.find(Post.class, id);
        return post;
    }

    public List<Post> findByName(String name) {
        return em.createQuery("select p from Post p where p.name =:name", Post.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Post> findAll() {
        return  em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }
}
