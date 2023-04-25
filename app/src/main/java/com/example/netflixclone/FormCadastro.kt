package com.example.netflixclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.netflixclone.databinding.ActivityFormCadastroBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        Toolbar()

        val bt_cadastrar = binding.btCadastro

        bt_cadastrar.setOnClickListener {

            val edit_email_cadastro = binding.editEmailCadastro.text.toString()
            val edit_senha_cadastro = binding.editSenhaCadastro.text.toString()
            val mensagem_erro = binding.mensagemErroCadastrar

            if(edit_email_cadastro.isEmpty() && edit_senha_cadastro.isEmpty()) {
                mensagem_erro.setText("Preencha os campos de Email e Senha!")

            }else if(edit_email_cadastro.isEmpty()) {
                mensagem_erro.setText("Preencha o campo de Email!")

            }else if(edit_senha_cadastro.isEmpty()) {
                mensagem_erro.setText("Preencha o campo de Senha!")

            }else {
                CadastrarUsuario()
            }
        }

    }

    private fun Toolbar() {

        val toolbarCadastro = binding.toolbarCadastro
        toolbarCadastro.navigationIcon = getDrawable(R.drawable.ic_netflix_official_logo)

    }

    private fun CadastrarUsuario() {

        val email = binding.editEmailCadastro.text.toString()
        val senha = binding.editSenhaCadastro.text.toString()
        val mensagem_erro = binding.mensagemErroCadastrar

        //Usar o serviço de autenticação do Firebse
        //Usar o servidor
        //Criar um usuário com email e senha
        //it: Task<AuthResult!> é responsável por cadastrar um usuário no Firebase ou recuperar um usuário que já foi cadastrado.

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener {
            
            if(it.isSuccessful) {
                Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }

        }.addOnFailureListener {

            //Com esse it é possível capturar as excessões do Firebase.

            var erro = it

            when {
                erro is FirebaseAuthWeakPasswordException -> mensagem_erro.setText("Digite uma senha com no mínimo 6 caracteres.")
                erro is FirebaseAuthUserCollisionException -> mensagem_erro.setText("Esta conta já existe.")
                erro is FirebaseNetworkException -> mensagem_erro.setText("Sem conexão com a internet.")
                else -> mensagem_erro.setText("Erro ao cadastrar usuário.")
            }
        }
    }
}