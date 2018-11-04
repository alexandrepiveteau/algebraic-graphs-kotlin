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

package com.github.alexandrepiveteau.algebraicGraphs

fun <V1, V2> Graph<V1>.flatMap(f: (V1) -> Graph<V2>): Graph<V2> =
    map { f(it) }.flatten()

fun <V1> Graph<Graph<V1>>.flatten(): Graph<V1> = when (this) {
    is Graph.Empty -> Graph.empty()
    is Graph.Vertex -> vertex
    is Graph.Union -> Graph.union(a.flatten(), b.flatten())
    is Graph.Product -> Graph.product(a.flatten(), b.flatten())
}

fun <V1, V2> Graph<V1>.map(f: (V1) -> V2): Graph<V2> = when (this) {
    is Graph.Empty -> Graph.empty()
    is Graph.Vertex -> Graph.vertex(f(vertex))
    is Graph.Union -> Graph.union(a.map(f), b.map(f))
    is Graph.Product -> Graph.product(a.map(f), b.map(f))
}

fun <V1> Graph<V1>.filter(f: (V1) -> Boolean): Graph<V1> =
    map { v1 -> if (f((v1))) Graph.vertex(v1) else Graph.empty() }.flatten()

fun <V1> Graph<V1>.filterEdge(from: V1, to: V1): Graph<V1> = when (this) {
    is Graph.Empty -> Graph.empty()
    is Graph.Vertex -> Graph.vertex(vertex)
    is Graph.Union -> Graph.union(a, b)
    is Graph.Product -> {
        val au = a - from
        val buv = b.filterEdge(from, to)
        val auv = a.filterEdge(from, to)
        val bv = b - to
        (au * buv) + (auv * bv)
    }
}

fun <V1, V2> Graph<V1>.zip2(a: Graph<V2>): Graph<Pair<V1, V2>> =
    flatMap { t1 -> a.map { t2 -> t1 to t2 } }

fun <V1, V2, V3> Graph<V1>.zip3(a: Graph<V2>, b: Graph<V3>): Graph<Triple<V1, V2, V3>> =
    flatMap { t1 -> a.flatMap { t2 -> b.map { t3 -> Triple(t1, t2, t3) } } }