package com.cjwilliams24680.seatgeeksearch.utils

import org.junit.Assert
import org.junit.Test

/**
 * Created by chris on 4/19/18.
 */
class TextUtilsTest {

    @Test
    fun nonNullify_test() {
        Assert.assertEquals("", TextUtils.nonNullify(null))
        Assert.assertEquals("hello", TextUtils.nonNullify("hello"))
        Assert.assertEquals("  ", TextUtils.nonNullify("  "))
    }

    @Test
    fun isBlack_test() {
        Assert.assertEquals(true, TextUtils.isBlank(null))
        Assert.assertEquals(true, TextUtils.isBlank(""))
        Assert.assertEquals(true, TextUtils.isBlank("   "))
        Assert.assertEquals(false, TextUtils.isBlank("  hello"))
        Assert.assertEquals(false, TextUtils.isBlank("."))
    }

}