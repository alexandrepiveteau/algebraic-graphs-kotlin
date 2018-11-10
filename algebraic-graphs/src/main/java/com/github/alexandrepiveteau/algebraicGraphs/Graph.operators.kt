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

operator fun <V> Graph<V>.plus(other: Graph<V>): Graph<V> =
    Graph.overlay(this, other)

operator fun <V> Graph<V>.minus(vertex: V): Graph<V> =
    filter { v1 -> v1 != vertex }

operator fun <V1> Graph<V1>.minus(e: Pair<V1, V1>): Graph<V1> =
    filterEdge(e.first, e.second)

operator fun <V> Graph<V>.times(other: Graph<V>): Graph<V> =
    Graph.connect(this, other)

operator fun <V> Graph<V>.contains(other: Graph<V>): Boolean =
    this + other == this

operator fun <V> Graph<V>.compareTo(other: Graph<V>): Int =
    when {
        this in other && other in this -> 0
        other in this -> 1
        this in other -> -1
        else -> 0
    }