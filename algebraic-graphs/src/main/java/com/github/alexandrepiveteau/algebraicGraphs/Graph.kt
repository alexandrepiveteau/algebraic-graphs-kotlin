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

sealed class Graph<V> {
    class Empty<V> : Graph<V>()
    class Vertex<V>(val vertex: V) : Graph<V>()
    class Union<V>(val a: Graph<V>, val b: Graph<V>) : Graph<V>()
    class Product<V>(val a: Graph<V>, val b: Graph<V>) : Graph<V>()

    fun vertices(): Set<V> = when (this) {
        is Empty -> emptySet()
        is Vertex -> setOf(vertex)
        is Union -> a.vertices() union b.vertices()
        is Product -> a.vertices() union b.vertices()
    }

    fun edges(): Set<Pair<V, V>> = when (this) {
        is Empty -> emptySet()
        is Vertex -> emptySet()
        is Union -> a.edges() union b.edges()
        is Product -> a.edges() union b.edges() union (a.vertices().flatMap { va ->
            b.vertices().map { vb -> va to vb }
        })
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Graph<*>) return false

        return (vertices() == other.vertices()) && (edges() == other.edges())
    }

    override fun hashCode(): Int {
        return 31 * vertices().hashCode() + edges().hashCode()
    }

    override fun toString(): String = when (this) {
        is Empty -> "()"
        is Vertex -> "($vertex)"
        is Union -> "$a+$b"
        is Product -> "$a*$b"
    }

    companion object Factory {

        fun <V> empty(): Graph<V> =
            Empty()

        fun <V> union(a: Graph<V>, b: Graph<V>): Graph<V> =
            Union(a, b)

        fun <V> vertex(vertex: V): Graph<V> =
            Vertex(vertex)

        fun <V> product(a: Graph<V>, b: Graph<V>): Graph<V> =
            Product(a, b)

        fun <V> edge(from: V, to: V): Graph<V> =
            vertex(from) * vertex(to)

        fun <V> clique(vararg elements: V): Graph<V> =
            clique(elements.asIterable())

        fun <V> clique(elements: Iterable<V>): Graph<V> =
            elements.fold(empty()) { g, e -> vertex(e) * g + g * vertex(e) }

        fun <V> vertices(vararg elements: V): Graph<V> =
            vertices(elements.asIterable())

        fun <V> vertices(elements: Iterable<V>): Graph<V> =
            elements.fold(empty()) { g, e -> vertex(e) + g }

        fun <V> star(center: V, vararg elements: V): Graph<V> =
            star(center, elements.asIterable())

        fun <V> star(center: V, elements: Iterable<V>): Graph<V> =
            vertex(center) * vertices(elements.asIterable())
    }
}