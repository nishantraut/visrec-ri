package visrec.ri.util;

import org.junit.jupiter.api.Test;

import javax.visrec.ml.ClassifierCreationException;
import javax.visrec.util.Builder;
import javax.visrec.util.InvalidBuilderConfigurationException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Kevin Berendsen
 */
public class BuilderConfigurationTest {

    /**
     * Successfully build the output of the builder.
     */
    @Test
    public void testReflectionInvocationBuild() throws ClassifierCreationException {
        Map<String, String> trainingSet = new HashMap<>();
        trainingSet.put("hello", "world");
        trainingSet.put("lorem", "ipsum");
        Map<String, Object> configuration = new HashMap<>();
        configuration.put("trainingSet", trainingSet);

        BuilderImpl builder = new BuilderImpl();
        String output = builder.build(configuration);
        assertEquals("{lorem=ipsum, hello=world}", output);
    }

    /**
     * The trainingSet method is invoked without the valid parameter and should
     * throw an exception.
     */
    @Test
    public void testInvalidParameterForMethod() {
        String trainingSet = "invalid";
        Map<String, Object> configuration = new HashMap<>();
        configuration.put("trainingSet", trainingSet);

        BuilderImpl builder = new BuilderImpl();
        try {
            builder.build(configuration);
            fail("The configuration is invalid and should throw the InvalidBuilderConfigurationException");
        } catch (InvalidBuilderConfigurationException | ClassifierCreationException e) {
            /* Expected */
        }
    }



    public static class BuilderImpl implements Builder<String> {

        private Map<String, String> trainingSet;

        public void trainingSet(Map<String, String> trainingSet) {
            this.trainingSet = trainingSet;
        }

        @Override
        public String build() {
            return trainingSet.toString();
        }
    }

}
