package com.procurement.dossier.domain.fail.error

import com.procurement.dossier.application.service.Logger
import com.procurement.dossier.domain.fail.Fail

sealed class BadRequestErrors(
    numberError: String,
    override val description: String
) : Fail.Error("RQ-") {

    override val code: String = prefix + numberError

    class Parsing(message: String, val request: String, val exception: Exception? = null) : BadRequestErrors(
        numberError = "01",
        description = "$message."
    ) {
        override fun logging(logger: Logger) {
            logger.error(message = "$message INVALID BODY: '$request'.", exception = exception)
        }
    }

    override fun logging(logger: Logger) {
        logger.error(message = message)
    }
}
