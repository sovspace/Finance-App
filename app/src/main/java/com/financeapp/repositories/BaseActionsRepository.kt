package com.financeapp.repositories

import com.financeapp.webservice.ActionsService
import com.financeapp.webservice.ServiceGenerator

abstract class BaseActionsRepository(token: String) {
    protected val authenticateService: ActionsService = ServiceGenerator.createService(ActionsService::class.java, token)
}