import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class PostTest {
  private Post testPost;
  @Before
  public void newPost() {
    testPost = new Post("Beach", "I went to the beach today.");
    testPost.save();
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void post_instantiatesCorrectly_true() {
    assertTrue(testPost instanceof Post);
    // assertEquals("Beach", testPost.getPostTitle());
    // assertEquals("I went to the beach today.", testPost.getPostBody());
  }

  @Test
  public void equals_testsWhetherAttributesOfPostMatch_true(){
    assertTrue(testPost.getPostId()>0);
    assertEquals("Beach", testPost.getPostTitle());
    assertEquals("I went to the beach today.", testPost.getPostBody());
  }

  @Test
  public void save_storesPostAndIdInDatabase(){
    Post restoredPost = Post.findByTitle("Beach");
    assertEquals(testPost, restoredPost);
  }

  @Test
  public void findByTitle_returnsSecondPost_true(){
    Post testPost2 = new Post ("Space", "I'm stranded in space. This is my last post.");
    testPost2.save();
    Post retrievedPost = Post.findByTitle("Space");
    assertEquals(testPost2, retrievedPost);
  }

  @Test
  public void all_returnsListOfPosts_true(){
    Post testPost2 = new Post ("Space", "I'm stranded in space. This is my last post.");
    testPost2.save();
    List<Post> allPosts = Post.all();
    Post[] postArray = {testPost, testPost2};
    assertTrue(allPosts.size()>1);
    assertTrue(allPosts.containsAll(Arrays.asList(postArray)));
  }

  @Test
public void update_updatesPostDescription_true() {
  Post testPost2 = new Post("Asdf", "Fdsad!");
  System.out.println("Pre-update title is: " + testPost2.getPostTitle());
  testPost2.update("Take a nap");
  System.out.println("Post-update title is: " + testPost2.getPostTitle());
  // , "Slept all day.");
  assertEquals("Take a nap", testPost2.getPostTitle());
  // assertEquals("Slept all day.", testPost.getPostBody());
}

@Test
public void delete_deletesPost_true() {
  testPost.delete();
  assertEquals(0, Post.all().size());
}

}
