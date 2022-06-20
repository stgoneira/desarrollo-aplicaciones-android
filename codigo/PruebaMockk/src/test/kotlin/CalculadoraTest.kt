import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CalculadoraTest {

    lateinit var calculadora: Calculadora

    @BeforeEach
    fun setup() {
        calculadora = Calculadora()
        println("Instanciando calculadora ...")
    }

    @Test
    fun sumarTest() {
        assertEquals(calculadora.sumar(1,3), 4)
    }

    @Test
    fun restarTest() {
        assertEquals(calculadora.restar(10,1,2), 7)
    }

}