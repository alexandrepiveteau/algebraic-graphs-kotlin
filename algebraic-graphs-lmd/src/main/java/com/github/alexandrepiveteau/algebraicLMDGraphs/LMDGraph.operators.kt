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

operator fun <V, L> LMDGraph<V, L>.plus(other: LMDGraph<V, L>): LMDGraph<V, L> =
    LMDGraph.union(this, other)

operator fun <V, L> LMDGraph<V, L>.minus(vertex: V): LMDGraph<V, L> =
    filter { v1 -> v1 != vertex }

operator fun <V, L> LMDGraph<V, L>.contains(other: LMDGraph<V, L>): Boolean =
    this + other == this

operator fun <V, L> LMDGraph<V, L>.compareTo(other: LMDGraph<V, L>): Int =
    when {
        this in other && other in this -> 0
        other in this -> 1
        this in other -> -1
        else -> 0
    }