package cherry_wave.nmg;

import com.google.gson.Gson;

import org.junit.Test;

public class GsonTest {

    @Test
    public void testToGson() throws Exception {
//        Gson gson = new Gson();
//        String json = gson.toJson(new InitialImport());
//        System.out.println(json);

        String string = "jdK10ds3TEST"; // jdK10TESTds3TEST / jdK10TESTds3
        if(string.toUpperCase().endsWith("TEST")) {
            string = string.substring(0, string.length() -4);
        }
        System.out.println(string);
    }

    class InitialImport {
        private String[] patternsCharacters = {"{X1-9}", "{V1} {C1}'{c1-4}"};
        private String[] syllablesCharacters = {"dro", "mund"};
    }
}