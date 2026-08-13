package smoke;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class SmokeTest {

    @Test
    public void junitFunciona() {
        assertEquals(2 + 2, 4);
    }

    @Test
    public void mockitoFunciona() {
        java.util.List<String> lista = mock(java.util.List.class);
        when(lista.size()).thenReturn(42);
        assertEquals(42, lista.size());
    }
}
