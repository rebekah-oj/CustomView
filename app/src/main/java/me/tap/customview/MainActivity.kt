package me.tap.customview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: MutableList<String> = ArrayList()
        list.add("Kotlin")
        list.add("Java")
        list.add("C++")
        list.add("Html")
        list.add("JavaScript")
        list.add("Php")

        customView.setData(list)
        customView.setTitle("Languages")
        submit.setOnClickListener {
            Toast.makeText(
                this,
                "Selected Datas : " + customView.getSelectedData().toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}