package br.com.heiderlopes.lembretedecompras.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.heiderlopes.lembretedecompras.models.RequestState
import br.com.heiderlopes.lembretedecompras.models.Usuario
import br.com.heiderlopes.lembretedecompras.repository.UsuarioRepository

class LoginViewModel : ViewModel() {

    private val usuarioRepository = UsuarioRepository()

    val loginState = MutableLiveData<RequestState<String>>()

    fun logar(usuario: Usuario) {
        loginState.value = usuarioRepository.logar(usuario).value
    }

}