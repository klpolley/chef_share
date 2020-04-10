import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class tagLibTest {


    @Test
    void validTagTest(){
        assertTrue(TagLib.validTag("Noodle"));
        assertTrue(TagLib.validTag("woop"));

        assertFalse(TagLib.validTag("tag*asdf"));
        assertFalse(TagLib.validTag("tagasdf&"));
        assertFalse(TagLib.validTag(":tagasdf"));
        assertFalse(TagLib.validTag(":::"));

        assertFalse(TagLib.validTag("hello space"));
        assertFalse(TagLib.validTag("hello "));
        assertFalse(TagLib.validTag(" hellospace"));
        assertFalse(TagLib.validTag("he llo sp ace"));

        assertFalse(TagLib.validTag(""));
        assertFalse(TagLib.validTag("   "));
        assertFalse(TagLib.validTag("                      "));
    }
    @Test
    void testAddTag(){
        TagLib t = new TagLib();
        assertEquals(0, t.numTags());
        t.addTag("noodle");
        assertEquals(1, t.numTags());
        t.addTag("starch");
        assertEquals(2, t.numTags());

        assertThrows(IllegalArgumentException.class, ()-> t.addTag("noodle"));
        assertThrows(IllegalArgumentException.class, ()-> t.addTag(""));
    }

    @Test
    void isTagTest(){
        TagLib t = new TagLib();
        t.addTag("noodle");
        t.addTag("starch");
        assertTrue(t.isTag("starch"));
        assertTrue(t.isTag("noodle"));
        assertFalse(t.isTag("potato"));
        t.addTag("potato");
        assertTrue(t.isTag("potato"));

        assertThrows(IllegalArgumentException.class, ()-> t.isTag(""));
    }

    @Test
    void printTagTest(){
        TagLib t = new TagLib();
        t.addTag("noodle");
        assertEquals("Tags:\nnoodle\n", t.printTags());
        t.addTag("starch");
        assertEquals("Tags:\nnoodle\nstarch\n", t.printTags());

        TagLib t2 = new TagLib();
        t2.addTag("starch");
        assertEquals("Tags:\nstarch\n", t2.printTags());
        t2.addTag("noodle");
        assertEquals("Tags:\nnoodle\nstarch\n", t2.printTags());
    }

    @Test
    void searchTest(){
        String[] tags =  {"starch","stroodle", "straping", "big", "supper" };
        TagLib t = new TagLib();
        for(int x = 0; x < 5; x ++){
            t.addTag(tags[x]);
        }
        String[] returnTags = t.search("s");
        String[] expected = {"starch", "straping", "stroodle", "supper"};
        for(int x = 0; x < returnTags.length; x ++){
            assertEquals(expected[x], returnTags[x]);
        }
        returnTags = t.search("st");
        expected = new String[] {"starch", "straping", "stroodle"};
        for(int x = 0; x < returnTags.length; x ++){
            assertEquals(expected[x], returnTags[x]);
        }
        returnTags = t.search("str");
        expected = new String[] { "straping", "stroodle"};
        for(int x = 0; x < returnTags.length; x ++){
            assertEquals(expected[x], returnTags[x]);
        }
        returnTags = t.search("b");
        expected = new String[] {"big"};
        for(int x = 0; x < returnTags.length; x ++){
            assertEquals(expected[x], returnTags[x]);
        }
    }

    @Test
    void doubleLengthTest(){
        TagLib t = new TagLib();
        for(int x = 0; x < 26; x ++){
            t.addTag("" + (char)(x + 65));
            assertEquals(x+1, t.numTags());
        }
    }
}
