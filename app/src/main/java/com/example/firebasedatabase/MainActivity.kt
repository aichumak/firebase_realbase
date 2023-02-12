package com.example.firebasedatabase

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasedatabase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val userGroup = "user"
    private val dataBase: DatabaseReference = FirebaseDatabase.getInstance().getReference(userGroup)
    private lateinit var arrayAdapter: ArrayAdapter<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val arrayList = mutableListOf<User>()
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        binding.listView.adapter = arrayAdapter
        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayList.clear()
                dataSnapshot.ref
                dataSnapshot.children.forEach { child ->
                    val user = child.getValue<User>()
                    user?.let {
                        arrayList.add(it)
                    }
                }
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        dataBase.addValueEventListener(postListener)
        setContentView(binding.root)
        binding.apply {
            saveButton.setOnClickListener {
                val id = dataBase.key
                val firstName = editTextFirstName.text.toString()
                val secondName = editTextSecondName.text.toString()
                val lastName = editTextLastName.text.toString()
                dataBase.push().setValue(User(id, firstName, secondName, lastName))
            }
            readButton.setOnClickListener {
                dataBase.parent?.setValue(null)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}