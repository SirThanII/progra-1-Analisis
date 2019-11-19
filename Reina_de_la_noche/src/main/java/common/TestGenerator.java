package common;
import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;


public class TestGenerator implements ITestConstants {
    private ArrayList<TestTree>[] tests = new ArrayList[AMOUNT_OF_TESTS]; 

    public TestGenerator() {
        for(int testCount=0; testCount<AMOUNT_OF_TESTS; testCount++) {
            tests[testCount] = new ArrayList<TestTree>();
        }
        generateTests();
    }

    private void generateTests() {
        for(int testCount=0; testCount<AMOUNT_OF_TESTS; testCount++) {
            for(int ruleIndex=0; ruleIndex<TEST_RULES[testCount].length; ruleIndex++) {
                for(int treeCount=0; treeCount<TEST_RULES[testCount][ruleIndex][TestRanges.QUANTITY.getIndex()]; treeCount++) {
                    int posX = TEST_RULES[testCount][ruleIndex][TestRanges.MIN_DISTANCE.getIndex()]+
                                    (int)(Math.random()*(TEST_RULES[testCount][ruleIndex][TestRanges.MAX_DISTANCE.getIndex()]-TEST_RULES[testCount][ruleIndex][TestRanges.MIN_DISTANCE.getIndex()]));

                    int length = TEST_RULES[testCount][ruleIndex][TestRanges.MIN_LENGTH.getIndex()]+
                                    (int)(Math.random()*(TEST_RULES[testCount][ruleIndex][TestRanges.MAX_LENGTH.getIndex()]-TEST_RULES[testCount][ruleIndex][TestRanges.MIN_LENGTH.getIndex()]));

                    int levels = TEST_RULES[testCount][ruleIndex][TestRanges.MIN_LEVELS.getIndex()]+
                                    (int)(Math.random()*(TEST_RULES[testCount][ruleIndex][TestRanges.MAX_LEVELS.getIndex()]-TEST_RULES[testCount][ruleIndex][TestRanges.MIN_LEVELS.getIndex()]));

                    TestTree test = new TestTree(posX, length, levels);
                    tests[testCount].add(test);

                }
            }
        }		
    }

    public ArrayList<TestTree>[] getTests() {
        return tests;
    }


    /*
    public static void main(String args[]) {
        TestGenerator generator = new TestGenerator();

        try {
                FileWriter file = new FileWriter("./testtrees.json");

                file.write("{ \"test\" : "+(new Gson()).toJson(generator.getTests())+"}");
                System.out.println("Posicion del hormiguero: "+ TEST_POSICION_HORMIGUERO);
                System.out.println("Porcentaje de crecimiento del �rbol: "+ GROW_PERCENTAGE);
                System.out.println("Los test quedaron en el archivo testtrees.json...");
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }
*/
}
