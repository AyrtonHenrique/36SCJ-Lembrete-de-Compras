package br.com.heiderlopes.lembretedecompras.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.heiderlopes.lembretedecompras.R
import br.com.heiderlopes.lembretedecompras.models.RequestState
import br.com.heiderlopes.lembretedecompras.models.Usuario
import br.com.heiderlopes.lembretedecompras.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private lateinit var animacaoDoMascote: Animation
    private lateinit var animacaoDoFormulario: Animation

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        iniciarAnimacao()
        esconderTeclado()


        iniciarViewModel()
        iniciarListener()
        iniciarObserver()
        buscaUltimoUsuarioLogado()

    }

    private fun buscaUltimoUsuarioLogado() {
        loginViewModel.getUsuarioLogado()
    }

    private fun iniciarObserver() {
        loginViewModel.loginState.observe(this, Observer {
            when(it) {
                is RequestState.Success -> {
                    Log.i("LEMBRETEDECOMPRAS", "LOGIN COM SUCESSO")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                is RequestState.Error -> {
                    Log.i("LEMBRETEDECOMPRAS", "LOGIN INVALIDO")
                    val animShake = AnimationUtils.loadAnimation(this, R.anim.shake)
                    containerLogin.startAnimation(animShake)
                    tvPasswordFeedback.text = it.throwable.message
                }

                is RequestState.Loading -> {
                    // Implementacao do Loading
                    Log.i("LEMBRETEDECOMPRAS", "ESTOU CARREGANDO")
                }
            }
        })

        loginViewModel.usuarioLogadoState.observe(this, Observer {
            when (it) {
                is RequestState.Success -> {
                    etEmail.setText(it.data)
                }
                is RequestState.Error -> {
                }
                is RequestState.Loading -> {
                }
            }
        })
    }

    private fun iniciarViewModel() {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun iniciarListener() {
        btLogin.setOnClickListener {
            loginViewModel.logar(Usuario(
                etEmail.text.toString(),
                etPassword.text.toString()
            ))
        }
    }

    private fun esconderTeclado() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    private fun iniciarAnimacao() {
        animacaoDoMascote = AnimationUtils.loadAnimation(this, R.anim.frombottom2)
        animacaoDoFormulario = AnimationUtils.loadAnimation(this, R.anim.frombottom)

        ivLogin.clearAnimation()
        containerLogin.clearAnimation()

        containerLogin.startAnimation(animacaoDoFormulario)
        ivLogin.startAnimation(animacaoDoMascote)

    }



}