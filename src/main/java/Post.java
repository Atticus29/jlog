import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Post {
  private int id;
  private String title;
  private String body;


  public Post(String title, String body) {
    this.title = title;
    this.body = body;
  }

  public String getPostTitle() {
    return this.title;
  }

  public String getPostBody() {
    return this.body;
  }

  public int getPostId() {
    return this.id;
  }

  public static List<Post> all() {
    String sql = "SELECT * FROM posts;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .throwOnMappingFailure(false)
      .executeAndFetch(Post.class);
    }
  }

  public static Post findByTitle(String title) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts where title=:title";
      Post post = con.createQuery(sql)
      .addParameter("title", title)
      .throwOnMappingFailure(false)
      .executeAndFetchFirst(Post.class);
      System.out.println(post.getPostTitle());
      return post;
    }
  }

  public static Post findById(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts where id=:id";
      Post post = con.createQuery(sql)
      .addParameter("id", id)
      .throwOnMappingFailure(false)
      .executeAndFetchFirst(Post.class);
      return post;
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO posts (title, body, posted, total_length) VALUES (:title, :body, now(), :total_length)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("title", this.title)
      .addParameter("body", this.body)
      .addParameter("total_length", this.body.length())
      .executeUpdate()
      .getKey();
    }
  }

  public void delete() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM posts WHERE id = :id;";
    con.createQuery(sql)
    .addParameter("id", this.id)
    // .executeUpdate();
    // String joinDeleteQuery = "DELETE FROM tags_posts WHERE post_id = :postId";
    // con.createQuery(joinDeleteQuery)
    // .addParameter("postId", this.getPostId())
    .executeUpdate();
  }
}

public void update(String newTitle) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE posts SET title = :newTitle WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("newTitle", newTitle)
      // .addParameter("newBody", newBody)
      .addParameter("id", this.id)
      .executeUpdate();
  }
}

  @Override
  public boolean equals(Object otherPost){
    if (!(otherPost instanceof Post)) {
      return false;
    } else {
      Post newPost = (Post) otherPost;
      return this.getPostTitle().equals(newPost.getPostTitle()) &&
      this.getPostBody().equals(newPost.getPostBody()) &&
      this.getPostId() == newPost.getPostId();
    }
  }

}
