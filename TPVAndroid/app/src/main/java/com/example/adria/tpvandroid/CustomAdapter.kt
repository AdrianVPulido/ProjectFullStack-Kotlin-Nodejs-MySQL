package com.example.adria.tpvandroid

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class CustomAdapter(var context: Context, var productosV: ArrayList<ProductosV>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView
        var ivImage: ImageView

        init{
            this.txtName = row?.findViewById(R.id.txtname) as TextView
            this.ivImage = row?.findViewById(R.id.imagep) as ImageView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder
        if (convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.productos_item_list, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var productosV: ProductosV = getItem(position) as ProductosV
        viewHolder.txtName.text = productosV.name
        viewHolder.ivImage.setImageResource(productosV.image)

        return view as View
    }

    override fun getItem(position: Int): Any {
        return productosV.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return productosV.count()
    }
}