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
    return title;
  }

  public String getPostBody() {
    return body;
  }

  public int getPostId() {
    return id;
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
