package com.prinzlycoder.apprate

import org.koin.dsl.module

val appModule = module {

    factory {
       val dialogPresenter: DialogContract.Presenter = RateDialogPresenter()
        dialogPresenter
    }
}