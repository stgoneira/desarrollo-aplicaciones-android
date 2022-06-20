class Calculadora {

    fun sumar(vararg numeros:Int):Int {
        return numeros.sum()
    }

    fun restar(vararg numeros:Int):Int {
        return numeros.reduce{ n1, n2 -> n1-n2}
    }
}