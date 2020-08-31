package me.tap.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class CustomViewActivity(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var autoCompletetextview: AutoCompleteTextView
    private lateinit var itemView: ListView
    private var listview: ListView
    private var titletextview: TextView
    private var add: ImageView

    private var selectedItem: MutableList<String> = ArrayList()
    private var allItems: MutableList<String> = ArrayList()

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_custom_view, this, true)
        autoCompletetextview = view.findViewById(R.id.autoCompletetextview)
        listview = view.findViewById(R.id.listview)
        titletextview = view.findViewById(R.id.titletextview)
        add = view.findViewById(R.id.add)

        autoCompletetextview.threshold = 1

        add.setOnClickListener {
            val selectedString = autoCompletetextview.text.trim()
            when {
                selectedString.isEmpty() -> Toast.makeText(
                    getContext(),
                    "Please enter data",
                    Toast.LENGTH_SHORT
                ).show()
                selectedString.contains(selectedString) -> Toast.makeText(
                    getContext(),
                    "item already added",
                    Toast.LENGTH_SHORT
                ).show()

                else -> {
                    selectedItem.add(0, selectedString as String)
                    refreshData(true)
                }
            }


        }
    }


    fun setData(data: MutableList<String>) {
        allItems = data
        autoCompletetextview.setAdapter(
            ArrayAdapter(
                context,
                android.R.layout.simple_list_item_1,
                allItems
            )
        )
    }

    fun setTitle(str: String) {
        titletextview.text = str
    }

    fun getSelectedData(): MutableList<String> {
        return selectedItem
    }

    fun refreshData(clearData: Boolean) {
        listview.adapter = CustomViewAdapter(context, R.layout.activity_custom_view, selectedItem)
        setListViewHeightBasedOnChildren(listview)
        if (clearData)
            autoCompletetextview.setText("")
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        var totalHeight = listView.paddingTop + listView.paddingBottom
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            (listItem as? ViewGroup)?.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }

    inner class CustomViewAdapter(
        context: Context?,
        private var resource: Int,
        private var objects: MutableList<String>?
    ) : ArrayAdapter<String>(
        context!!, resource, objects as MutableList<String>
    ) {
        private val inflater: LayoutInflater = LayoutInflater.from(context)

        override fun getCount(): Int {
            return objects!!.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = inflater.inflate(resource, parent, false)
            val name = view.findViewById<TextView>(R.id.name)
            val delete = view.findViewById<ImageView>(R.id.delete)
            name.text = objects?.get(position)
            delete.setOnClickListener {
                selectedItem.removeAt(position)
                refreshData(false)
            }
            return view
        }


    }

}