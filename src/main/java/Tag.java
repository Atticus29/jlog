import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Tag {
  private int id;
  private String tagName;
  private Timestamp created;
  private int timesUsed;

  public Tag(String name) {
    this.tagName = name;
  }

  public String getTagName() {
    return this.tagName;
  }

  public int getTagId() {
    return this.id;
  }

  public int getTimesUsed() {
    // return this.timesUsed;
    String sqlQuery = "SELECT * FROM tags_posts WHERE tag_id=:tagid;";
    try(Connection con = DB.sql2o.open()){
      List<Tag> results = con.createQuery(sqlQuery)
        .addParameter("tagid", this.id)
        .addColumnMapping("name", "tagName")
        .throwOnMappingFailure(false)
        .executeAndFetch(Tag.class);
      return results.size();
    }
  }

  public static List<Tag> all() {
    String sql = "SELECT * FROM tags;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .throwOnMappingFailure(false)
      .addColumnMapping("name", "tagName")
      .executeAndFetch(Tag.class);
    }
  }

  public static Tag findByTag(String tag) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags where name=:tag";
      Tag post = con.createQuery(sql)
      .addParameter("tag", tag)
      .addColumnMapping("name", "tagName")
      .throwOnMappingFailure(false)
      .executeAndFetchFirst(Tag.class);
      return post;
    }
  }

  public static Tag findById(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags where id=:id";
      Tag post = con.createQuery(sql)
      .addParameter("id", id)
      .addColumnMapping("name", "tagName")
      .throwOnMappingFailure(false)
      .executeAndFetchFirst(Tag.class);
      return post;
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (name, created, num_times_used) VALUES (:name, now(), :num_times_used)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.tagName) //maybe ATTN here
      .addParameter("num_times_used", this.getTimesUsed())
      .addColumnMapping("name", "tagName")
      .executeUpdate()
      .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tags WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .addColumnMapping("name", "tagName")
      .executeUpdate();
    }
  }

  public void update(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tags SET name = :name WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherTag){
    if (!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getTagName().equals(newTag.getTagName()) &&
      // this.getTimesUsed().equals(newTag.getTimesUsed()) &&
      this.getTagId() == newTag.getTagId();
    }
  }

}
