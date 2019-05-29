import com.ericlindau.psx.config.Configure;
import com.ericlindau.psx.core.polling.AnalogComponent;
import com.ericlindau.psx.core.processing.AnalogValue;
import com.ericlindau.psx.core.processing.Variable;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestToml {

  @Test
  public void testParse() throws Exception {
    Configure c = new Configure();
    List<Variable> variables = c.variables();

    // First level
    Variable flt = variables.get(0);

    Assert.assertEquals("Qs120", flt.getPsx());
    Assert.assertEquals("Flight controls", flt.getName());
    Assert.assertEquals(";", flt.getDelimiter());

    // Second level
    AnalogValue aileron = (AnalogValue) flt.getValues().get(0);

    // Implicit - from properties table
    Assert.assertEquals(-999, aileron.getMin());
    Assert.assertEquals(999, aileron.getMax());

    // Third level
    AnalogComponent capt = (AnalogComponent) aileron.components.get(0);

    Assert.assertEquals("Aileron - Captain", capt.getName());
    Assert.assertFalse(capt.isInverted());

    AnalogComponent fo = (AnalogComponent) aileron.components.get(1);

    // Explicit
    Assert.assertEquals("Aileron - F/O", fo.getName());
    Assert.assertFalse(capt.isInverted());
  }
}
