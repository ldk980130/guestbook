package springpractice.guestbook;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Entity
@Getter
public class Post {

    protected Post() {
    }

    public static Post createPost(String name, String content) {
        Post post = new Post();

        post.name = name;
        post.content = content;

        post.likes = 0;
        post.hates = 0;

        post.createTime = System.currentTimeMillis();
        post.timeFormat = new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초")
                .format(post.createTime);

        return post;
    }

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String name;
    private String content;

    private int likes;
    private int hates;

    private Long createTime;
    private String timeFormat;

    //매서드

    public void addLike() { this.likes++; }
    public void addHate() { this.hates++; }

    public boolean canPost(Optional<Post> prev) {

        if(prev.isEmpty()) return true;

        return this.createTime - prev.get().createTime > 60000;
    }


}
