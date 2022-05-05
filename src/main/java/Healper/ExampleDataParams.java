package Healper;

import org.testng.annotations.DataProvider;

public class ExampleDataParams {
    @DataProvider
    public static Object[][] paramsExample(){
        return new Object[][]{
                {new ParameterBuilder("src/main/resources/example.properties")}
        };
    }

}
