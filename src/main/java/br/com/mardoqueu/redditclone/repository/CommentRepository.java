package br.com.mardoqueu.redditclone.repository;

import br.com.mardoqueu.redditclone.model.Comment;
import br.com.mardoqueu.redditclone.model.Post;
import br.com.mardoqueu.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}