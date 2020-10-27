package br.com.heiderlopes.lembretedecompras.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.heiderlopes.lembretedecompras.models.RequestState
import br.com.heiderlopes.lembretedecompras.models.Usuario
import java.lang.Exception

class UsuarioRepository(val context: Context) {

    fun logar(usuario: Usuario) : LiveData<RequestState<String>> {

        val response = MutableLiveData<RequestState<String>>()
        response.value  = RequestState.Loading

        if(usuario.email == "user@fiap.com.br" && usuario.senha == "123456") {
            val pref = context.getSharedPreferences("lembretedecompras", 0)
            val editor = pref.edit()
            editor.putString("email", usuario.email)
            editor.apply()

            response.value = RequestState.Success("")
        } else {
            response.value = RequestState.Error(Exception("Usuario ou senha invalido"))
        }

        return response
    }

    fun getUsuarioLogado(): LiveData<RequestState<String>> {
        val response = MutableLiveData<RequestState<String>>()

        val pref = context.getSharedPreferences("lembretedecompras", 0)
        val email = pref.getString("email", "") ?: ""
        response.value = RequestState.Success(email)

        return response
    }

}