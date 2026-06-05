package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoriesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    private val categories = listOf(
        CategoryItem("推荐", R.color.primary_color),
        CategoryItem("科技", R.color.secondary_color),
        CategoryItem("生活", R.color.tertiary_color),
        CategoryItem("体育", R.color.quaternary_color),
        CategoryItem("娱乐", R.color.primary_color),
        CategoryItem("财经", R.color.secondary_color)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.categoriesRecyclerView)
        categoryAdapter = CategoryAdapter(categories)
        
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = categoryAdapter
        }
    }

    inner class CategoryAdapter(private val categories: List<CategoryItem>) :
        RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val categoryName: TextView = itemView.findViewById(R.id.categoryName)
            val categoryIcon: View = itemView.findViewById(R.id.categoryIcon)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_grid, parent, false)
            return CategoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val category = categories[position]
            holder.categoryName.text = category.name
            holder.categoryIcon.setBackgroundColor(resources.getColor(category.colorRes))
        }

        override fun getItemCount(): Int = categories.size
    }

    data class CategoryItem(val name: String, val colorRes: Int)
}
