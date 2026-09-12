package util;

/**
 * Reintento acotado para altas que calculan su ID como MAX(id)+1.
 *
 * Dos sesiones concurrentes pueden calcular el mismo ID: la primera inserta y
 * la segunda recibe Duplicate entry (el DAO devuelve false, no excepcion). Este
 * helper re-ejecuta el bloque completo (que debe recalcular los IDs dentro de
 * cada intento) hasta N veces. Si todos los intentos fallan, devuelve false
 * para que el llamador lo informe al usuario en vez de seguir como si nada.
 *
 * Reglas para el bloque: sin efectos colaterales fuera de la BD (mails, PDFs y
 * syncs van DESPUES del reintento) y con compensacion propia ante insert
 * parcial (borrar lo que ese mismo intento haya insertado).
 */
public final class ReintentoAlta {

    public interface Intento {
        boolean ejecutar();
    }

    private ReintentoAlta() {
    }

    public static boolean reintentar(int maxIntentos, Intento intento) {
        int n = Math.max(1, maxIntentos);
        for (int i = 0; i < n; i++) {
            boolean ok;
            try {
                ok = intento.ejecutar();
            } catch (RuntimeException e) {
                if (i == n - 1) {
                    throw e;
                }
                continue;
            }
            if (ok) {
                return true;
            }
        }
        return false;
    }
}
