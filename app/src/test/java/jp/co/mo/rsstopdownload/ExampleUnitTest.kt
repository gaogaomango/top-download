package jp.co.mo.rsstopdownload

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        val str: String = "a"

        if("a".equals(str)) {
            println("等しい")
        } else {
            println("等しくない")
        }

        if("a" == str) {
            println("等しい")
        } else {
            println("等しくない")
        }

    }
}
