package com.example.firebaserealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_employee_detail.*
import kotlinx.android.synthetic.main.update_dialog.*
import java.util.zip.Inflater

class EmployeeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)
        setValueToView()
        btndelete.setOnClickListener {
            deleteRecord(intent.getStringExtra("empID").toString())
        }
        btnupdate.setOnClickListener {
            openUpdateDialog(intent.getStringExtra("empID").toString(),
                intent.getStringExtra("empName").toString())
        }
    }

    private fun openUpdateDialog(empID: String, empName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)
        mDialog.setView(mDialogView)
        //update infor in Dialog
        val edtempName = mDialogView.findViewById<EditText>(R.id.edtnameEmp)
        val edtempAge = mDialogView.findViewById<EditText>(R.id.edtageEmp)
        val edtempSalary = mDialogView.findViewById<EditText>(R.id.edtsalaryEmp)
        val btnUpdatedata = mDialogView.findViewById<Button>(R.id.btnUpdatedata)

        edtempName.setText(intent.getStringExtra("empName").toString())
        edtempAge.setText(intent.getStringExtra("empAge").toString())
        edtempSalary.setText(intent.getStringExtra("empSalary").toString())

        mDialog.setTitle("Updating ${empName} record")
        val alertDialog = mDialog.create()
        alertDialog.show()

        //click btnupdatedata
        btnUpdatedata.setOnClickListener {
            updateEmployeeData(
                empID,
                edtempName.text.toString(),
                edtempAge.text.toString(),
                edtempSalary.text.toString()
            )
            Toast.makeText(applicationContext,"Employee data update", Toast.LENGTH_SHORT).show()
            //update data in dialog
            txtname.setText(edtempName.text.toString())
            txtage.setText(edtempAge.text.toString())
            txtsalary.setText(edtempSalary.text.toString())

            alertDialog.dismiss()
        }
    }

    private fun updateEmployeeData(empID: String, edtName: String,
                                   edtAge: String, edtSalary: String)
    {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(empID)
        val empInfor = EmployeeModel(empID,edtName,edtAge,edtSalary)
        dbRef.setValue(empInfor)
    }


    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empTask = dbRef.removeValue()
        empTask.addOnSuccessListener {
            Toast.makeText(this,"Delete success", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener { err->
            Toast.makeText(this,"Delete error ${err.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setValueToView() {
        txtid.text = intent.getStringExtra("empID")
        txtname.text = intent.getStringExtra("empName")
        txtage.text = intent.getStringExtra("empAge")
        txtsalary.text = intent.getStringExtra("empSalary")
    }
}