package com.example.user_sp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user_sp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    @SuppressLint("CommitPrefEdits", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)
        val isFirstTime = preferences.getBoolean(getString(R.string.sp_first_time), true)
        Log.i("SP", "${getString(R.string.sp_first_time)} = $isFirstTime")

        if (isFirstTime) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_register, null)
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_confirm) { _, _ ->
                    val username = dialogView.findViewById<TextInputEditText>(R.id.edtUsername).text.toString()
                    with(preferences.edit()){
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), username).apply()
                    }
                    Toast.makeText(this, "Usuario $username registrado.", Toast.LENGTH_SHORT).show()
                }.show()
        }else{
            val username = preferences.getString(getString(R.string.sp_username), "getString(R.string.hint_username)")
            Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT).show()
        }

        userAdapter = UserAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(this)

        mBinding.rvMain.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }

    private fun getUsers(): MutableList<User>{
        val users = mutableListOf<User>()
        val alain = User(1, "Alain", "Nicolas", "https://pbs.twimg.com/profile_images/743330187610251264/xkqVE_Qa_400x400.jpg")
        val samantha = User(2, "Samantha", "Godoy", "https://www.lecturas.com/medio/2020/09/30/emma-watson_4c5ed31b_800x800.jpg")
        val javier = User(3, "Javier", "Gomez", "https://www.realmadrid.com/img/vertical_380px/javier-navarro_1vc0026_20220902070657.jpg")
        val emma = User(4, "Emma", "Cruz", "https://www.lecturas.com/medio/2020/09/30/emma-watson_4c5ed31b_800x800.jpg")

        repeat(3){
            Log.i("MSG", "${it+1}")
            users.add(alain)
            users.add(samantha)
            users.add(javier)
            users.add(emma)
        }
        return users
    }

    override fun onClick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFullName()}", Toast.LENGTH_SHORT).show()

    }
}