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

fun <V1, V2, L> LMDGraph<V1, L>.flatMap(f: (V1) -> LMDGraph<V2, L>): LMDGraph<V2, L> =
    map { f(it) }.flatten()

fun <V1, L> LMDGraph<LMDGraph<V1, L>, L>.flatten(): LMDGraph<V1, L> = when (this) {
    is LMDGraph.Empty -> LMDGraph.empty()
    is LMDGraph.Vertex -> vertex
    is LMDGraph.Union -> LMDGraph.union(a.flatten(), b.flatten())
    is LMDGraph.Product -> LMDGraph.product(label, a.flatten(), b.flatten())
}

fun <V1, V2, L> LMDGraph<V1, L>.map(f: (V1) -> V2): LMDGraph<V2, L> = when (this) {
    is LMDGraph.Empty -> LMDGraph.empty()
    is LMDGraph.Vertex -> LMDGraph.vertex(f(vertex))
    is LMDGraph.Union -> LMDGraph.union(a.map(f), b.map(f))
    is LMDGraph.Product -> LMDGraph.product(label, a.map(f), b.map(f))
}

fun <V1, L> LMDGraph<V1, L>.filter(f: (V1) -> Boolean): LMDGraph<V1, L> =
    map { v1 -> if (f((v1))) LMDGraph.vertex<V1, L>(v1) else LMDGraph.empty() }.flatten()

fun <V1, V2, L> LMDGraph<V1, L>.zip2(a: LMDGraph<V2, L>): LMDGraph<Pair<V1, V2>, L> =
    flatMap { t1 -> a.map { t2 -> t1 to t2 } }

fun <V1, V2, V3, L> LMDGraph<V1, L>.zip3(a: LMDGraph<V2, L>, b: LMDGraph<V3, L>): LMDGraph<Triple<V1, V2, V3>, L> =
    flatMap { t1 -> a.flatMap { t2 -> b.map { t3 -> Triple(t1, t2, t3) } } }