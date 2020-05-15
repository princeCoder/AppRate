package com.prinzlycoder.apprate

interface DialogContract {
    interface View {
        fun promptToRate()
        fun dismiss()
    }

    interface Presenter {
        fun attachView(view: View)
        fun promptToRate()
        fun handleMaybeButtonClicked()
        fun addListener(callback: RateDialog.Callback)
    }
}