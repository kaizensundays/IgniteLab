package com.kaizensundays.ignite.quorum

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Created: Saturday 4/19/2025, 12:34 PM Eastern Time
 *
 * @author Sergey Chuykov
 */
class DefaultTopologyValidatorTest {

    private val validator = DefaultTopologyValidator(2)

    @Test
    fun validate() {

        assertFalse(validator.validate(mutableListOf(mock())))
        assertTrue(validator.validate(mutableListOf(mock(), mock())))
        assertTrue(validator.validate(mutableListOf(mock(), mock(), mock())))
    }

}