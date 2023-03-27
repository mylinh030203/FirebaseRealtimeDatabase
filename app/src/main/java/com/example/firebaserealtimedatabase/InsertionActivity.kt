package com.example.firebaserealtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_insertion.*

class InsertionActivity : AppCompatActivity() {
    private lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        btnsave.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {
        var empName = edtname.text.toString()
        var empAge = edtage.text.toString()
        var empSalary = edtsalary.text.toString()

        //đẩy dữ liệu
        var empID = dbRef.push().key!!
        var employee = EmployeeModel(empID, empName, empAge, empSalary)
        //kiem tra dk
        if(empName.isEmpty()){
            edtname.error = "please enter name"
            return
        }
        if(empAge.isEmpty()){
            edtage.error = "please enter age"
            return
        }
        if(empSalary.isEmpty()){
            edtsalary.error = "please enter salary"
            return
        }
        //chen data
        dbRef.child(empID).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, " data insert success", Toast.LENGTH_SHORT).show()
                edtname.setText("")
                edtage.setText("")
                edtsalary.setText("")
            }
            .addOnFailureListener { err->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}