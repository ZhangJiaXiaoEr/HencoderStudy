package cn.zectec.kotlinstudy

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("cn.zectec.kotlinstudy.test", appContext.packageName)
    }

    @Test
    fun function1() = runBlocking {
        val job = launch {
            println("launch......ThreadName: ${Thread.currentThread().name}" )
        }

        val job1 = async {
            delay(500L)
            println("async......ThreadName: ${Thread.currentThread().name}" )
            return@async "hello"
        }
        println("async的输出: ${job1.await()}" )
        delay(1300L)
    }
}