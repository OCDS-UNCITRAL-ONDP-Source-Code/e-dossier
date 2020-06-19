package com.procurement.dossier.domain.fail.error

import com.procurement.dossier.domain.fail.Fail
import com.procurement.dossier.domain.model.Cpid
import com.procurement.dossier.domain.model.document.DocumentId
import com.procurement.dossier.domain.model.requirement.RequirementId
import com.procurement.dossier.domain.model.submission.SubmissionId
import com.procurement.dossier.domain.util.extension.format
import com.procurement.dossier.infrastructure.model.dto.ocds.CriteriaSource
import com.procurement.dossier.infrastructure.model.dto.ocds.RequirementDataType
import java.time.LocalDateTime

sealed class ValidationErrors(
    numberError: String,
    override val description: String,
    val entityId: String? = null
) : Fail.Error(prefix = "VR-") {

    override val code: String = prefix + numberError

    class RequirementNotFoundOnValidateRequirementResponse(requirementId: RequirementId) : ValidationErrors(
        numberError = "10.5.1.1",
        description = "Requirement with id '$requirementId' not found.",
        entityId = requirementId
    )

    class RequirementsNotFoundOnValidateRequirementResponse(cpid: Cpid) : ValidationErrors(
        numberError = "10.5.1.3",
        description = "Requirements not found by cpid '$cpid'."
    )

    class RequirementDataTypeCompareError(actualDataType: RequirementDataType, expectedDataType: RequirementDataType) :
        ValidationErrors(
            numberError = "10.5.1.2",
            description = "Data type mismatch. Expected data type: '$expectedDataType', actual data type: '$actualDataType'."
        )

    class UnexpectedCriteriaSource(actual: CriteriaSource, expected: CriteriaSource) : ValidationErrors(
        numberError = "10.5.1.4",
        description = "Unexpected criteria.source value. Expected: '${expected}', actual: '${actual}'."
    )

    class InvalidPeriodDateComparedWithStartDate(requestDate: LocalDateTime, startDate: LocalDateTime) :
        ValidationErrors(
            numberError = "5.6.3",
            description = "Period date '${requestDate.format()}' must be after stored period start date '${startDate.format()}'."
        )

    class InvalidPeriodDateComparedWithEndDate(requestDate: LocalDateTime, endDate: LocalDateTime) : ValidationErrors(
        numberError = "5.6.4",
        description = "Period date '${requestDate.format()}' must precede stored period end date '${endDate.format()}'."
    )

    sealed class SubmissionNotFoundFor(id: SubmissionId, numberError: String) : ValidationErrors(
        numberError = numberError,
        description = "Submission id(s) '$id' not found."
    ) {
        class GetSubmissionStateByIds(id: SubmissionId) : SubmissionNotFoundFor(id = id, numberError = "5.10.1")
        class SetStateForSubmission(id: SubmissionId) : SubmissionNotFoundFor(id = id, numberError = "5.11.1")
        class CheckAccessToSubmission(id: SubmissionId) : SubmissionNotFoundFor(id = id, numberError = "5.9.3")
    }

    class InvalidToken() : ValidationErrors(
        numberError = "5.9.1",
        description = "Received token does not match submission token."
    )

    class InvalidOwner() : ValidationErrors(
        numberError = "5.9.2",
        description = "Received owner does not match submission owner."
    )

    sealed class Duplicate(value: String, entityName: String, numberError: String) : ValidationErrors(
        numberError = numberError,
        description = "Value '$value' is not unique in '$entityName'."
    ) {
        class Candidate(id: String) : Duplicate(
            value = id,
            entityName = "candidates.id",
            numberError = "5.7.1"
        )

        class OrganizationDocument(id: DocumentId) : Duplicate(
            value = id,
            entityName = "documents.id",
            numberError = "5.7.2"
        )

        class PersonBusinessFunction(id: String) : Duplicate(
            value = id,
            entityName = "candidates.persons.businessFunctions.id",
            numberError = "5.7.3"
        )

        class PersonDocument(id: DocumentId) : Duplicate(
            value = id,
            entityName = "candidates.persons.businessFunctions.documents.id",
            numberError = "5.7.4"
        )
    }
}
