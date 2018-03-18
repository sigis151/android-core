package com.telesoftas.core.common.intent.email

/**
 * Used in conjuction with [EmailChooserIntentFactory]. Some examples of raw emails:
 * ```
 *      test@example.com
 *      mail:test@example.com
 *      <email>test@example.com</email>
 * ```
 *
 * @param email the email in it's raw form
 * @param chooserTitle the title of the chooser intent dialog
 * @param emailPrefix the email prefix, used for subtraction
 * @param emailSuffix the email suffix, used for subtraction
 *
 * @see EmailChooserIntentFactory
 */
data class EmailChooserData(
        val email: String,
        val chooserTitle: String,
        val emailPrefix: String = "",
        val emailSuffix: String = ""
)