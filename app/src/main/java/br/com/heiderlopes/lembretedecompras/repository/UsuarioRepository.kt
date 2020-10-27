package br.com.heiderlopes.lembretedecompras.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.heiderlopes.lembretedecompras.models.RequestState
import br.com.heiderlopes.lembretedecompras.models.Usuario
import java.lang.Exception

class UsuarioRepository {

    fun logar(usuario: Usuario) : LiveData<RequestState<String>> {

        val response = MutableLiveData<RequestState<String>>()
        response.value  = RequestState.Loading

        if(usuario.email == "user@fiap.com.br" && usuario.senha == "123456") {
            response.value = RequestState.Success("")
        } else {
            response.value = RequestState.Error(Exception("Usuario ou senha invalido"))
        }

        return response
    }
}