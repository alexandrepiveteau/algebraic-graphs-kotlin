/*
 * MIT License
 *
 * Copyright (c) 2018 Alexandre Piveteau
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import com.github.alexandrepiveteau.algebraicLMDGraphs.LMDGraph
import com.github.alexandrepiveteau.algebraicLMDGraphs.plus
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LMDGraphSample {

    @Test
    fun sample() {

        val e0 = LMDGraph.vertices<Int, String>(0..3000)
        val e1 = LMDGraph.edge(1, 2, "a")
        val e2 = LMDGraph.edge(1, 2, "a")
        val g = e0 + e1 + e2

        println("e1 : $e1")
        println("e2 : $e2")
        println("g : $g")
        println("g.vertices : ${g.verticesToString()}")
        println("g.edges : ${g.edgesToString()}")

        println("Hello world.")
    }

    fun <T> Iterable<T>.joinToStringFormatted(): String = joinToString(prefix = "[", postfix = "]", separator = ", ", limit = 10)
    fun <V, L> LMDGraph<V, L>.edgesToString(): String = edges().joinToStringFormatted()
    fun <V, L> LMDGraph<V, L>.verticesToString(): String = vertices().joinToStringFormatted()
}