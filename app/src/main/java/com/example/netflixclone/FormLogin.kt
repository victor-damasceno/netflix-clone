package com.example.netflixclone

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.netflixclone.databinding.ActivityFormLoginBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        VerificarUsuarioAutenticado()

        val mensagemAjuda = binding.txtAjuda
        val criarConta = binding.txtCriarConta
        val mensagem_erro = binding.mensagemErro
        val bt_entrar = binding.btEntrar

        mensagemAjuda.setOnClickListener {

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://help.netflix.com/pt"))
            startActivity(browserIntent)
        }

        criarConta.setOnClickListener {

            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
            mensagem_erro.setText("")
        }

        bt_entrar.setOnClickListener {
            AutenticarUsuario()
        }

    }

    private fun AutenticarUsuario() {

        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()
        val mensagem_erro = binding.mensagemErro

        if(email.isEmpty() && senha.isEmpty()) {
            mensagem_erro.setText("Preencha os campos de Email e Senha!")

        }else if (email.isEmpty()) {
            mensagem_erro.setText("Preencha o campo de Email!")

        }else if (senha.isEmpty()) {
            mensagem_erro.setText("Preencha o campo de Senha!")

        }else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
                    AbrirTelaDaListaDeFilmes()

                }
            }.addOnFailureListener {

                var erro = it

                when{

                    erro is FirebaseAuthInvalidCredentialsException -> mensagem_erro.setText("Email ou senha estão incorretos.")
                    erro is FirebaseNetworkException -> mensagem_erro.setText("Sem conexão com a internet!")
                    else -> mensagem_erro.setText("Erro ao logar usuário.")
                }
            }

        }

    }

    private fun VerificarUsuarioAutenticado() {

        val autenticado = FirebaseAuth.getInstance().currentUser

        if(autenticado != null) {
            AbrirTelaDaListaDeFilmes()
        }
    }

    private fun AbrirTelaDaListaDeFilmes() {

        val intent = Intent(this, ListaFilmes::class.java)
        startActivity(intent)
        finish()

    }
}