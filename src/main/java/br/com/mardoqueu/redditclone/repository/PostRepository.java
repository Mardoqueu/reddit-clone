package br.com.mardoqueu.redditclone.repository;

import br.com.mardoqueu.redditclone.model.Post;
import br.com.mardoqueu.redditclone.model.Subreddit;
import br.com.mardoqueu.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}