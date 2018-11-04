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

package com.github.alexandrepiveteau.algebraicLMDGraphs

// Labelled Multidigraph with distinct labels.
sealed class LMDGraph<V, L> {
    class Empty<V, L> : LMDGraph<V, L>()
    class Vertex<V, L>(val vertex: V) : LMDGraph<V, L>()
    class Union<V, L>(val a: LMDGraph<V, L>, val b: LMDGraph<V, L>) : LMDGraph<V, L>()
    class Product<V, L>(val label: L, val a: LMDGraph<V, L>, val b: LMDGraph<V, L>) : LMDGraph<V, L>()

    fun vertices(): Set<V> = when (this) {
        is Empty -> emptySet()
        is Vertex -> setOf(vertex)
        is Union -> a.vertices() union b.vertices()
        is Product -> a.vertices() union b.vertices()
    }

    fun edges(): Set<Triple<L, V, V>> = when (this) {
        is Empty -> emptySet()
        is Vertex -> emptySet()
        is Union -> a.edges() union b.edges()
        is Product -> a.edges() union b.edges() union (a.vertices().flatMap { va ->
            b.vertices().map { vb -> Triple(label, va, vb) }
        })
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LMDGraph<*, *>) return false

        return (vertices() == other.vertices()) && (edges() == other.edges())
    }

    override fun hashCode(): Int {
        return 31 * vertices().hashCode() + edges().hashCode()
    }

    override fun toString(): String = when (this) {
        is Empty -> "()"
        is Vertex -> "($vertex)"
        is Union -> "$a+$b"
        is Product -> "$a*[$label]*$b"
    }

    companion object Factory {

        fun <V, L> empty(): LMDGraph<V, L> =
            Empty()

        fun <V, L> union(a: LMDGraph<V, L>, b: LMDGraph<V, L>): LMDGraph<V, L> =
            Union(a, b)

        fun <V, L> vertex(vertex: V): LMDGraph<V, L> =
            Vertex(vertex)

        fun <V, L> product(label: L, a: LMDGraph<V, L>, b: LMDGraph<V, L>): LMDGraph<V, L> =
            Product(label, a, b)

        fun <V, L> edge(from: V, to: V, label: L): LMDGraph<V, L> =
            product(label, vertex(from), vertex(to))

        fun <V, L> clique(label: L, vararg elements: V): LMDGraph<V, L> =
            clique(label, elements.asIterable())

        fun <V, L> clique(label: L, elements: Iterable<V>): LMDGraph<V, L> =
            elements.fold(empty()) { g, a -> product(label, vertex(a), g) + product(label, g, vertex(a)) }

        fun <V, L> vertices(vararg elements: V): LMDGraph<V, L> =
            vertices(elements.asIterable())

        fun <V, L> vertices(elements: Iterable<V>): LMDGraph<V, L> =
            elements.fold(empty()) { g, a -> union(vertex(a), g) }

        fun <V, L> star(label: L, center: V, vararg elements: V): LMDGraph<V, L> =
            star(label, center, elements.asIterable())

        fun <V, L> star(label: L, center: V, elements: Iterable<V>): LMDGraph<V, L> =
            product(label, vertex(center), vertices(elements))
    }
}