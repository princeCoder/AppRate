package com.prinzlycoder.apprate

class RateDialogPresenter : DialogContract.Presenter {

    private var view: DialogContract.View? = null
    private var listener: RateDialog.Callback? = null

    override fun attachView(view: DialogContract.View) {
        this.view = view
    }

    override fun addListener(callback: RateDialog.Callback) {
        listener = callback
    }

    override fun promptToRate() {
        view?.let {
            it.promptToRate()
            it.dismiss()
        }
        listener?.onYesClicked()
    }

    override fun handleMaybeButtonClicked() {
        view?.dismiss()
        listener?.onMaybeClicked()
    }
}