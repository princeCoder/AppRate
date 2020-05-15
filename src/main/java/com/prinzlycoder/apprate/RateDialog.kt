package com.prinzlycoder.apprate

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.rating_dialog.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RateDialog : DialogFragment(), DialogContract.View {

    companion object {
        fun newInstance(): RateDialog = RateDialog()
        const val STORE_URL = "http://play.google.com/store/apps/details?id="
    }

    private val presenter: DialogContract.Presenter by inject()

    fun setListeners(callback: Callback) {
        presenter.addListener(callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rating_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        yes_button?.setOnClickListener {
            presenter.promptToRate()
        }
        cancel_button?.setOnClickListener {
            presenter.handleMaybeButtonClicked()
        }
        presenter.attachView(this)
    }

    override fun promptToRate() {
        try {
            context?.let {
                ContextCompat.startActivity(
                    it,
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + context?.getString(R.string.package_name))
                    ),
                    null
                )
            }
        } catch (e: ActivityNotFoundException) {
            context?.let {
                ContextCompat.startActivity(
                    it,
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(STORE_URL + context?.getString(R.string.package_name))
                    ),
                    null
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppRateDialog)
    }


    interface Callback {
        fun onYesClicked()
        fun onMaybeClicked()
    }
}

object AppRate {
    fun init(context: Context) {
        startKoin {
            androidContext(context)
            modules(
                appModule
            )
        }
    }

    fun get(): RateDialog {
        return RateDialog.newInstance()
    }
}

fun RateDialog.addListener(callback: RateDialog.Callback): RateDialog {
    this.setListeners(callback)
    return this
}

fun RateDialog.showDialog(fragmentManager: FragmentManager) {
    this.show(fragmentManager, RateDialog::class.java.simpleName)
}