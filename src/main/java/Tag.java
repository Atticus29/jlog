// import org.sql2o.*;
// import java.util.Arrays;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.Timer;
// import java.util.TimerTask;
// import java.sql.Timestamp;
//
// public class Tag {
//   private int id;
//   private String tagName;
//   private Timestamp created;
//   private int timesUsed;
//
//   public Tag(String name) {
//     this.tagName = name;
//   }
//
//   public String getTagName() {
//     return this.name;
//   }
//
//   public int getTagId() {
//     return this.id;
//   }
//
//   public int getTimesUsed() {
//     return this.timesUsed;
//   }
//
//   public static List<Tag> all() {
//     String sql = "SELECT * FROM posts;";
//     try(Connection con = DB.sql2o.open()) {
//       return con.createQuery(sql)
//       .throwOnMappingFailure(false)
//       .executeAndFetch(Tag.class);
//     }
//   }
//
//   public static Tag findByTitle(String title) {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "SELECT * FROM posts where title=:title";
//       Tag post = con.createQuery(sql)
//       .addParameter("title", title)
//       .throwOnMappingFailure(false)
//       .executeAndFetchFirst(Tag.class);
//       return post;
//     }
//   }
//
//   public static Tag findById(int id) {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "SELECT * FROM posts where id=:id";
//       Tag post = con.createQuery(sql)
//       .addParameter("id", id)
//       .throwOnMappingFailure(false)
//       .executeAndFetchFirst(Tag.class);
//       return post;
//     }
//   }
//
//   public void save() {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "INSERT INTO posts (title, body, posted, total_length) VALUES (:title, :body, now(), :total_length)";
//       this.id = (int) con.createQuery(sql, true)
//       .addParameter("title", this.title)
//       .addParameter("body", this.body)
//       .addParameter("total_length", this.body.length())
//       .executeUpdate()
//       .getKey();
//     }
//   }
//
//   public void delete() {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "DELETE FROM posts WHERE id = :id;";
//       con.createQuery(sql)
//       .addParameter("id", this.id)
//       // .executeUpdate();
//       // String joinDeleteQuery = "DELETE FROM tags_posts WHERE post_id = :postId";
//       // con.createQuery(joinDeleteQuery)
//       // .addParameter("postId", this.getTagId())
//       .executeUpdate();
//     }
//   }
//
//   public void update(String newTitle) {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "UPDATE posts SET title = :newTitle WHERE id = :id;";
//       con.createQuery(sql)
//       .addParameter("newTitle", newTitle)
//       // .addParameter("newBody", newBody)
//       .addParameter("id", this.id)
//       .executeUpdate();
//       System.out.println("I RAN");
//       System.out.println(newTitle);
//     }
//   }
//
//   @Override
//   public boolean equals(Object otherTag){
//     if (!(otherTag instanceof Tag)) {
//       return false;
//     } else {
//       Tag newTag = (Tag) otherTag;
//       return this.getTagTitle().equals(newTag.getTagTitle()) &&
//       this.getTagBody().equals(newTag.getTagBody()) &&
//       this.getTagId() == newTag.getTagId();
//     }
//   }
//
// }
