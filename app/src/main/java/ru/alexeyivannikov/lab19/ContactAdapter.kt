package ru.alexeyivannikov.lab19

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.alexeyivannikov.lab19.databinding.ContactItemBinding

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var contacts: List<String> = listOf()

    fun submitList(contacts: List<String>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    class ContactViewHolder(val binding: ContactItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contactName = contacts[position]
        holder.binding.tvContactName.text = contactName
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}