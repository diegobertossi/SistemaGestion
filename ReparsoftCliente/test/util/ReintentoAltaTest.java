package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class ReintentoAltaTest {

    @Test
    public void exitoAlPrimerIntento_ejecutaUnaVez() {
        AtomicInteger llamadas = new AtomicInteger();
        boolean ok = ReintentoAlta.reintentar(3, () -> {
            llamadas.incrementAndGet();
            return true;
        });
        assertTrue(ok);
        assertEquals(1, llamadas.get());
    }

    @Test
    public void fallaUnaVezYDespuesOk_reintentaConIdsFrescos() {
        AtomicInteger llamadas = new AtomicInteger();
        boolean ok = ReintentoAlta.reintentar(3, () -> llamadas.incrementAndGet() >= 2);
        assertTrue(ok);
        assertEquals(2, llamadas.get());
    }

    @Test
    public void fallaSiempre_devuelveFalseTrasMaxIntentos() {
        AtomicInteger llamadas = new AtomicInteger();
        boolean ok = ReintentoAlta.reintentar(3, () -> {
            llamadas.incrementAndGet();
            return false;
        });
        assertFalse(ok);
        assertEquals(3, llamadas.get());
    }

    @Test
    public void excepcionDeRuntime_seReintentaYAlFinalPropaga() {
        AtomicInteger llamadas = new AtomicInteger();
        try {
            ReintentoAlta.reintentar(2, () -> {
                llamadas.incrementAndGet();
                throw new IllegalStateException("falla");
            });
            fail("debio propagar la excepcion");
        } catch (IllegalStateException e) {
            assertEquals(2, llamadas.get());
        }
    }
}
