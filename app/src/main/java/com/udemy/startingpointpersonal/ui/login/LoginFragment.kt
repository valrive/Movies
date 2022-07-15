package com.udemy.startingpointpersonal.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.udemy.startingpointpersonal.R
import com.udemy.startingpointpersonal.core.ApiResult
import com.udemy.startingpointpersonal.databinding.FragmentLoginBinding
import com.udemy.startingpointpersonal.pojos.User
import com.udemy.startingpointpersonal.presentation.LoginViewModel
import com.udemy.startingpointpersonal.ui.BaseFragment
import com.udemy.startingpointpersonal.ui.MainActivity
import com.udemy.startingpointpersonal.utils.RequiredFieldException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginViewModel by viewModels<LoginViewModel>()

    /**
     * Relation between EditTexts and user properties
     */
    private val loginFormMap by lazy {
        hashMapOf(
            User::username.name to binding.tilUsername,
            User::password.name to binding.tilPassword
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //_binding = FragmentLoginBinding.bind(view)

        with(binding){
            viewModel = loginViewModel
            lifecycleOwner = viewLifecycleOwner
            //o el de abajo
            //lifecycleOwner = this@LoginFragment
        }

        loginViewModel.userLogged.observe(viewLifecycleOwner, Observer(::holi))
    }

    private fun holi(it: ApiResult<Unit>){
        when (it) {

            is ApiResult.Success -> {
                navigateToHome()
            }

            is ApiResult.Failure -> {
                if(it.exception is RequiredFieldException){
                    showRequiredErrors(it.exception.fieldsRequired)
                    binding.root.showSnack("Holi")
                    return
                }
                showError(it.exception.message!!)
            }
        }
    }


    /**
     * Show required field errors
     * @param fieldsRequired List of fields name
     */
    private fun showRequiredErrors(fieldsRequired: List<String>) {
        // clean previous errors
        loginFormMap.forEach { it.value.error = null }

        //show error message in every field and focus it
        fieldsRequired.forEach {
            loginFormMap[it]?.apply {
                this.error = getString(R.string.error_required_field)
            }
        }

        // focus first field
        loginFormMap[fieldsRequired.first()]?.requestFocus()
    }

    /**
     * Show a SnackBar with the error message
     * @param message
     */
    private fun showError(message: String) = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()


    /**
     * Navigates to home activity and finish current activity
     */
    private fun navigateToHome() {
        startActivity(Intent(context, MainActivity::class.java))
        activity?.finish()
    }

    private fun View.showSnack(message: String){
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }
}