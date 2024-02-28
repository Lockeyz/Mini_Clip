package com.lucky.miniclip

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lucky.miniclip.databinding.ActivitySignupBinding
import com.lucky.miniclip.model.UserModel
import com.lucky.miniclip.util.UiUtil

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            signUp()
        }

        binding.goToLoginBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    fun setInProgress(inProgress : Boolean) {
        if (inProgress) {
            binding.submitBtn.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.submitBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun signUp() {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val confirmPassword = binding.confirmPasswordInput.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.setError("Email not valid")
            return
        }

        if (password.length < 6) {
            binding.passwordInput.setError("Minimum 6 characters")
            return
        }

        if (password != confirmPassword) {
            binding.confirmPasswordInput.setError("Password not matched")
            return
        }

        signUpWithFirebase(email, password)
    }

    private fun signUpWithFirebase(email : String, password : String) {
        setInProgress(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let { user ->
                    val userModel = UserModel(user.uid, email, email.substringBefore("@"))
                    Firebase.firestore.collection("users")
                        .document(user.uid)
                        .set(userModel).addOnSuccessListener {
                            UiUtil.showToast(applicationContext, "Account created successfully")
                            setInProgress(false)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                }
            }.addOnFailureListener {
                UiUtil.showToast(applicationContext, it.localizedMessage ?: "Something went wrong")
                setInProgress(false)
            }
    }
}