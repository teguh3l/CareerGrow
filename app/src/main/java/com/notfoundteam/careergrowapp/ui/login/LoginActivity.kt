package com.notfoundteam.careergrowapp.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.notfoundteam.careergrowapp.R
import com.notfoundteam.careergrowapp.data.pref.ResultState
import com.notfoundteam.careergrowapp.data.model.UserModel
import com.notfoundteam.careergrowapp.databinding.ActivityLoginBinding
import com.notfoundteam.careergrowapp.ui.ViewModelFactory
import com.notfoundteam.careergrowapp.ui.main.MainActivity
import com.notfoundteam.careergrowapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    //firebase
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        binding.optionRegister.setOnClickListener { optionRegister() }

        // Configure Google Sign In
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Initialize Firebase Auth
        auth = Firebase.auth

        setupGoogleSignInButton()

        binding.signInButton.setOnClickListener {
            showLoading(true)
            signIn()
        }


    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isEmpty()) {
                binding.loginEmail.error = getString(R.string.email_rule)
            } else if (password.isEmpty()) {
                binding.loginPassword.error = getString(R.string.password_rule)
            }

            viewModel.login(email, password).observe(this) { response ->
                when(response) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }
                    is ResultState.Success -> {
                        response.data.message.let {
                            viewModel.saveSession(
                                UserModel(
                                    email, response.data.data.token
                                )
                            )
                            showLoading(false)
                            showLoginSuccessDialog()
                        }
                    }
                    is ResultState.Error -> {
                        showLoading(false)
                        showLoginErrorToast()
                    }
                }
            }
        }
    }

    private fun showLoginSuccessDialog(){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Anda berhasil login. Mari bagikan Ceritamu!")
            setPositiveButton("Lanjut") { _, _ ->
                startMainActivity()
            }
            create()
            show()
        }
    }
    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Log.d("LoginActivity", "Navigation to MainActivity")
        startActivity(intent)
        finish()
    }
    private fun showLoginErrorToast() {
        Toast.makeText(
            this, "Login gagal. Silahkan coba lagi.",
            Toast.LENGTH_SHORT).show()
    }

    private fun optionRegister() {
        val optionIntent = Intent(this, RegisterActivity::class.java)
        startActivity(optionIntent)
        finish()
    }
    private fun setupGoogleSignInButton() {
        val signInButton = findViewById<SignInButton>(R.id.signInButton)
        signInButton.setSize(SignInButton.SIZE_WIDE) // Sesuaikan ukuran sesuai keinginan Anda

        // Ganti teks tombol menjadi sesuatu yang sesuai dengan kebutuhan Anda
        val signInText = signInButton.getChildAt(0) as View
        if (signInText is android.widget.TextView) {
            signInText.text = getString(R.string.sign_in_with_google)
        }
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }
    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }
    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
    companion object {
        private const val TAG = "LoginActivity"
    }

}

