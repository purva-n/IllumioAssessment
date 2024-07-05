import org.example.WordMatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordMatchTest {
    List<String> inputList;
    List<String> predefinedWords;

    public WordMatchTest() {
        this.inputList = new ArrayList<>() {
            {
                add("Detecting first names is tricky to do even with AI.");
                add("how do you say a street name is not a first name?");
                add("how do you say a firstname is a Firstname?");
            }
        };

        this.predefinedWords = new ArrayList<>() {
            {
                add("AI");
                add("detect");
                add("firstname");
            }
        };
    }

    @Test
    public void WordMatchTestToCheckWordMatchWithoutPunctuation() {
        WordMatch wm = new WordMatch(this.predefinedWords, this.inputList);
        //Assertions.assertEquals(true, wm.matchWords());
        wm.matchWords();
        Map<String, Integer> map = wm.getWordMatchMap();

        Assertions.assertEquals(1, map.get("AI"));
    }

    @Test
    public void WordMatchTestToCheckFullWordMatch() {
        WordMatch wm = new WordMatch(this.predefinedWords, this.inputList);
        //Assertions.assertEquals(true, wm.matchWords());
        wm.matchWords();
        Map<String, Integer> map = wm.getWordMatchMap();

        Assertions.assertEquals(0, map.get("detect"));
    }

    @Test
    public void WordMatchTestToCheckCaseInsensitiveWordMatch() {
        WordMatch wm = new WordMatch(this.predefinedWords, this.inputList);
        //Assertions.assertEquals(true, wm.matchWords());
        wm.matchWords();
        Map<String, Integer> map = wm.getWordMatchMap();

        Assertions.assertEquals(2, map.get("firstname"));
    }
}
