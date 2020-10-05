package com.financeapp.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.financeapp.utils.Resource
import com.financeapp.viewmodels.factories.TokenViewModelFactory
import com.financeapp.utils.FilePath
import com.financeapp.utils.SharedPreferencesInfo
import com.financeapp.viewmodels.SettingsViewModel
import com.financeapp.views.databinding.SettingsFragmentBinding
import com.squareup.picasso.Picasso
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import es.dmoral.toasty.Toasty


class SettingsFragment : Fragment() {

    lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val binding: SettingsFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.settings_fragment, container, false
        )
        val preferences = requireActivity().getSharedPreferences(
            SharedPreferencesInfo.preferencesName,
            Context.MODE_PRIVATE
        )
        val token = preferences.getString(SharedPreferencesInfo.tokenName, "")

        settingsViewModel =
            ViewModelProvider(requireActivity(),
                TokenViewModelFactory(token as String)
            ).get(
                SettingsViewModel::class.java
            )


        binding.viewmodel = settingsViewModel

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: ImageButton = view.findViewById(R.id.changeAvatar)
        val progressBar: ContentLoadingProgressBar = view.findViewById(R.id.progressBarSettings)


        button.setOnClickListener {
            PickImageDialog.build(PickSetup())
                .setOnPickResult {
                    val avatarUri = FilePath.getPath(requireActivity(), it.uri)
                    settingsViewModel.avatar = avatarUri!!

                    Picasso.with(context)
                        .load(it.uri)
                        .centerCrop()
                        .fit()
                        .into(button)

                }.show(requireActivity())
        }


        settingsViewModel.getUser().observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.OK -> {
                    progressBar.hide()

                    Picasso.with(context)
                        .load(it.getData()?.avatarUrl)
                        .centerCrop()
                        .fit()
                        .into(view.findViewById<ImageView>(R.id.avatar))

                    val usernameTextView: TextView = view.findViewById(R.id.usernameText)

                    usernameTextView.text = it.getData()?.userInfo?.username ?: "No username"

                    val firstNameTextView: TextView = view.findViewById(R.id.firstNameText)
                    firstNameTextView.text = it.getData()?.userInfo?.firstName ?: "No first name"


                    val lastNameTextView: TextView = view.findViewById(R.id.lastNameText)
                    lastNameTextView.text = it.getData()?.userInfo?.lastName ?: "No last name"

                    val emailTextView: TextView = view.findViewById(R.id.emailText)
                    emailTextView.text = it.getData()?.userInfo?.email ?: "No email"

                    val birthDateTextView: TextView = view.findViewById(R.id.birthDateText)

//                    if (it.getData()?.userInfo?.birthDate != null) {
//                        val formatter = SimpleDateFormat("yyyy-MM-dd", getDefault())
//                        birthDateTextView.text = formatter.format(it.getData()?.userInfo?.birthDate as Date)
//                    } else {
//                        birthDateTextView.text = "No birth date"
//                    }

                }
                Resource.Status.ERROR -> {
                    progressBar.hide()
                    Toasty.error(requireContext(), it.getMessage() as String)
                }
                Resource.Status.LOADING -> progressBar.show()
            }
        }

        settingsViewModel.requestUser()
    }

}