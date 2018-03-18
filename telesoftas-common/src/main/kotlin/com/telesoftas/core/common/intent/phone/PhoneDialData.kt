package com.telesoftas.core.common.intent.phone

/**
 * Used in conjuction with [PhoneDialIntentFactory]. Some examples of raw phone numbers:
 * ```
 *      +00 00000000
 *      00000000
 *      tel:00000000
 *      <tel>00000000</tel>
 * ```
 *
 * @param phone the phone number in it's raw form
 * @param phonePrefix the phone number's prefix, used for subtraction
 * @param phoneSuffix the phone number's suffix, used for subtraction
 *
 * @see PhoneDialIntentFactory
 */
data class PhoneDialData(
        val phone: String,
        val phonePrefix: String = "",
        val phoneSuffix: String = ""
)