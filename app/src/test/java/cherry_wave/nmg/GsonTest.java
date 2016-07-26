package cherry_wave.nmg;

import com.google.gson.Gson;

import org.junit.Test;

public class GsonTest {

    @Test
    public void testToGson() throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(new InitialImport());
        System.out.println(json);
    }

    class InitialImport {
        private String[] patternsCharacters = {"{X1-9}", "{V1} {C1}'{c1-4}"};
        private String[] syllablesCharacters = {"dro", "mund"};
    }
}