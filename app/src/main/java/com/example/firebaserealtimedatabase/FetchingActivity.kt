package com.example.firebaserealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Contacts.Data
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaserealtimedatabase.adapter.empAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_employee_detail.*
import kotlinx.android.synthetic.main.activity_fetching.*

class FetchingActivity : AppCompatActivity() {
    private lateinit var ds:ArrayList<EmployeeModel>
    private lateinit var dbRef :DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)
        emp.layoutManager = LinearLayoutManager(this)
        emp.setHasFixedSize(true)
        ds = arrayListOf()
        GetInforEmp()
    }

    private fun GetInforEmp() {
        emp.visibility = View.GONE
        txtLoadingData.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if(snapshot.exists()){
                    for(i in snapshot.children){
                         val empData = i.getValue(EmployeeModel::class.java)
                        ds.add(empData!!)
                    }
                    val EmpAdapter = empAdapter(ds)
                    emp.adapter = EmpAdapter
                    //
                   EmpAdapter.setOnItemclickListener(object : empAdapter.OnItemClickListener{
                       override fun onItemClick(position: Int) {
                           var intent = Intent(this@FetchingActivity, EmployeeDetailActivity::class.java)
                           intent.putExtra("empID", ds[position].emoId)
                           intent.putExtra("empName", ds[position].empName)
                           intent.putExtra("empAge", ds[position].empAge)
                           intent.putExtra("empSalary", ds[position].empSalary)
                           startActivity(intent)
                       }
                   })
                    emp.visibility = View.VISIBLE
                    txtLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}