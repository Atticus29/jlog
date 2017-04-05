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

public class TagTest {
  private Tag testTag;

  @Before
  public void setUp(){
    testTag = new Tag("troubleshooting");
    testTag.save();
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void getters_work_true(){
    assertEquals("troubleshooting", testTag.getTagName());
    assertTrue(testTag.getTagId()>0);
    // assertTrue(testTag.getTimesUsed()>0);
  }

  @Test
  public void tag_instantiatesCorrectly_true() {
    assertTrue(testTag instanceof Tag);
  }

  @Test
  public void equals_testsWhetherAttributesOfTagMatch_true(){
    assertTrue(testTag.getTagId()>0);
    assertEquals("troubleshooting", testTag.getTagName());
  }

  @Test
  public void save_storesTagAndIdInDatabase(){
    Tag restoredTag = Tag.findByTag("troubleshooting");
    assertEquals(testTag, restoredTag);
  }

  @Test
  public void findByTag_returnsSecondTag_true(){
    Tag testTag2 = new Tag ("vodoo");
    testTag2.save();
    Tag retrievedTag = Tag.findByTag(testTag2.getTagName());
    assertEquals(testTag2, retrievedTag);
  }

  @Test
  public void all_returnsListOfTags_true(){
    Tag testTag2 = new Tag ("vodoo");
    testTag2.save();
    List<Tag> allTags = Tag.all();
    Tag[] tagArray = {testTag, testTag2};
    assertTrue(allTags.size()>1);

    assertTrue(allTags.containsAll(Arrays.asList(tagArray)));
  }

  @Test
public void update_updatesTagDescription_true() {
  Tag testTag2 = new Tag ("vodoo");
  testTag2.save();
  testTag2.update("sorcery");
  // Tag whateverTag = Tag.findByTag(testTag2.getTagName());
  // whateverTag.save();
  //  assertEquals(whateverTag.getTagName(), Tag.findByTag(testTag2.getTagName()).getTagName());
  assertEquals("sorcery", Tag.findByTag("sorcery").getTagName());
}

@Test
public void delete_deletesTag_true() {
  testTag.delete();
  assertEquals(0, Tag.all().size());
}


}
