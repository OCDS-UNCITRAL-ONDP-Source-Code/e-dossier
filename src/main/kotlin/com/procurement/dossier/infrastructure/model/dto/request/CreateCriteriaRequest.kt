package com.procurement.dossier.infrastructure.model.dto.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.procurement.dossier.application.model.data.CoefficientRate
import com.procurement.dossier.application.model.data.CoefficientValue
import com.procurement.dossier.application.model.data.Requirement
import com.procurement.dossier.infrastructure.bind.coefficient.CoefficientRateDeserializer
import com.procurement.dossier.infrastructure.bind.coefficient.CoefficientRateSerializer
import com.procurement.dossier.infrastructure.bind.coefficient.value.CoefficientValueDeserializer
import com.procurement.dossier.infrastructure.bind.coefficient.value.CoefficientValueSerializer
import com.procurement.dossier.infrastructure.bind.criteria.RequirementDeserializer
import com.procurement.dossier.infrastructure.bind.criteria.RequirementSerializer
import com.procurement.dossier.infrastructure.model.dto.ocds.AwardCriteria
import com.procurement.dossier.infrastructure.model.dto.ocds.AwardCriteriaDetails
import com.procurement.dossier.infrastructure.model.dto.ocds.ConversionsRelatesTo
import com.procurement.dossier.infrastructure.model.dto.ocds.CriteriaRelatesTo

@JsonIgnoreProperties(ignoreUnknown = true)
data class CreateCriteriaRequest(
    @field:JsonProperty("tender") @param:JsonProperty("tender") val tender: Tender
) {
    data class Tender(
        @field:JsonProperty("awardCriteria") @param:JsonProperty("awardCriteria") val awardCriteria: AwardCriteria,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @field:JsonProperty("awardCriteriaDetails") @param:JsonProperty("awardCriteriaDetails") val awardCriteriaDetails: AwardCriteriaDetails?,

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @field:JsonProperty("criteria") @param:JsonProperty("criteria") val criteria: List<Criteria>?,

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @field:JsonProperty("conversions") @param:JsonProperty("conversions") val conversions: List<Conversion>?
    ) {
        data class Criteria(
            @field:JsonProperty("id") @param:JsonProperty("id") val id: String,
            @field:JsonProperty("title") @param:JsonProperty("title") val title: String,

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @field:JsonProperty("description") @param:JsonProperty("description") val description: String?,

            @field:JsonProperty("requirementGroups") @param:JsonProperty("requirementGroups") val requirementGroups: List<RequirementGroup>,

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @field:JsonProperty("relatesTo") @param:JsonProperty("relatesTo") val relatesTo: CriteriaRelatesTo?,

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @field:JsonProperty("relatedItem") @param:JsonProperty("relatedItem") val relatedItem: String?
        ) {
            data class RequirementGroup(
                @field:JsonProperty("id") @param:JsonProperty("id") val id: String,

                @JsonInclude(JsonInclude.Include.NON_NULL)
                @field:JsonProperty("description") @param:JsonProperty("description") val description: String?,

                @JsonDeserialize(using = RequirementDeserializer::class)
                @JsonSerialize(using = RequirementSerializer::class)
                @field:JsonProperty("requirements") @param:JsonProperty("requirements") val requirements: List<Requirement>
            )
        }

        data class Conversion(
            @field:JsonProperty("id") @param:JsonProperty("id") val id: String,
            @field:JsonProperty("relatesTo") @param:JsonProperty("relatesTo") val relatesTo: ConversionsRelatesTo,
            @field:JsonProperty("relatedItem") @param:JsonProperty("relatedItem") val relatedItem: String,
            @field:JsonProperty("rationale") @param:JsonProperty("rationale") val rationale: String,

            @JsonInclude(JsonInclude.Include.NON_NULL)
            @field:JsonProperty("description") @param:JsonProperty("description") val description: String?,

            @field:JsonProperty("coefficients") @param:JsonProperty("coefficients") val coefficients: List<Coefficient>
        ) {
            data class Coefficient(
                @field:JsonProperty("id") @param:JsonProperty("id") val id: String,

                @JsonDeserialize(using = CoefficientValueDeserializer::class)
                @JsonSerialize(using = CoefficientValueSerializer::class)
                @field:JsonProperty("value") @param:JsonProperty("value") val value: CoefficientValue,

                @JsonDeserialize(using = CoefficientRateDeserializer::class)
                @JsonSerialize(using = CoefficientRateSerializer::class)
                @field:JsonProperty("coefficient") @param:JsonProperty("coefficient") val coefficient: CoefficientRate
            )
        }
    }
}