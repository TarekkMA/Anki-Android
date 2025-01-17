/*
 *  Copyright (c) 2022 David Allison <davidallisongithub@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 3 of the License, or (at your option) any later
 *  version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 *  PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with
 *  this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ichi2.anki.compat

import com.ichi2.compat.Compat
import com.ichi2.compat.CompatV21
import com.ichi2.compat.CompatV26
import com.ichi2.testutils.createTransientDirectory
import com.ichi2.testutils.withTempFile
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.io.File

/** Tests for [Compat.hasFiles] */
@RunWith(Parameterized::class)
class CompatHasFilesTest(
    val compat: Compat,
    /** Used in the "Test Results" Window */
    @Suppress("unused") private val unitTestDescription: String
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{1}")
        fun data(): Iterable<Array<Any>> = sequence {
            yield(arrayOf(CompatV21(), "CompatV21"))
            yield(arrayOf(CompatV26(), "CompatV26"))
        }.asIterable()
    }

    @Test
    fun has_files_with_file() {
        val dir = createTransientDirectory().withTempFile("aa.txt")
        assertThat("empty directory has no files", hasFiles(dir), equalTo(true))
    }

    @Test
    fun has_files_exists() {
        val dir = createTransientDirectory()
        assertThat("empty directory has no files", hasFiles(dir), equalTo(false))
    }

    @Test
    fun has_files_not_exists() {
        val dir = createTransientDirectory()
        dir.delete()
        assertThat("deleted directory has no files", hasFiles(dir), equalTo(false))
    }

    private fun hasFiles(dir: File) = compat.hasFiles(dir)
}
