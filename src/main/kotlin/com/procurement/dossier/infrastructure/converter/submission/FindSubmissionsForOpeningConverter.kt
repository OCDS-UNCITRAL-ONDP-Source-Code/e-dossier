package com.procurement.dossier.infrastructure.converter.submission

import com.procurement.dossier.application.model.data.submission.find.FindSubmissionsForOpeningParams
import com.procurement.dossier.infrastructure.model.dto.request.submission.FindSubmissionsForOpeningRequest

fun FindSubmissionsForOpeningRequest.convert() = FindSubmissionsForOpeningParams.tryCreate(
    cpid = cpid,
    ocid = ocid,
    country = country,
    pmd = pmd
)