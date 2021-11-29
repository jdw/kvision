/*
 * Copyright (c) 2017-present Robert Jaros
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
package test.io.kvision.chart

import io.kvision.chart.ChartCanvas
import io.kvision.chart.ChartType
import io.kvision.chart.Configuration
import io.kvision.chart.DataSets
import io.kvision.chart.ChartOptions
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlinx.browser.document
import kotlin.test.Test

class ChartCanvasSpec : DomSpec {

    @Test
    fun renderResponsive() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val chart = ChartCanvas(
                configuration = Configuration(
                    ChartType.SCATTER,
                    listOf(DataSets(label = "Chart", data = listOf(0, 1)))
                )
            )
            root.add(chart)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<canvas height=\"0\" style=\"display: block; box-sizing: border-box; height: 0px; width: 0px;\" width=\"0\"></canvas>",
                element?.innerHTML,
                "Should render correct responsive chart canvas"
            )
        }
    }

    @Test
    fun renderNotResponsive() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val chart = ChartCanvas(
                300, 600,
                configuration = Configuration(
                    ChartType.SCATTER,
                    listOf(DataSets(label = "Chart", data = listOf(0, 1))),
                    options = ChartOptions(responsive = false)
                )
            )
            root.add(chart)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<canvas width=\"300\" height=\"600\" style=\"display: block; box-sizing: border-box; height: 600px; width: 300px;\"></canvas>",
                element?.innerHTML,
                "Should render correct not responsive chart canvas"
            )
        }
    }
}