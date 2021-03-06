package com.procurement.dossier.infrastructure.handler.query

import com.fasterxml.jackson.databind.JsonNode
import com.procurement.dossier.application.model.data.submission.organization.GetOrganizationsResult
import com.procurement.dossier.application.service.Logger
import com.procurement.dossier.application.service.SubmissionService
import com.procurement.dossier.domain.fail.Fail
import com.procurement.dossier.domain.util.Result
import com.procurement.dossier.infrastructure.converter.submission.convert
import com.procurement.dossier.infrastructure.handler.AbstractQueryHandler
import com.procurement.dossier.infrastructure.model.dto.bpe.Command2Type
import com.procurement.dossier.infrastructure.model.dto.bpe.tryGetParams
import com.procurement.dossier.infrastructure.model.dto.request.submission.organization.GetOrganizationsRequest
import org.springframework.stereotype.Component

@Component
class GetOrganizationsHandler(logger: Logger, private val submissionService: SubmissionService) :
    AbstractQueryHandler<Command2Type, List<GetOrganizationsResult>>(logger) {

    override val action: Command2Type = Command2Type.GET_ORGANIZATIONS

    override fun execute(node: JsonNode): Result<List<GetOrganizationsResult>, Fail> {
        val params = node.tryGetParams(GetOrganizationsRequest::class.java)
            .orForwardFail { fail -> return fail }
            .convert()
            .orForwardFail { fail -> return fail }

        return submissionService.getOrganizations(params)
    }
}