package com.achaka.fragmentshomework

import android.os.Bundle
import android.text.InputType
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.achaka.fragmentshomework.databinding.FragmentContactDetailsBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContactDetailsFragment : Fragment() {
    private var name: String? = null
    private var phone: String? = null
    private lateinit var binding: FragmentContactDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            name = it.getString(CONTACT_NAME_KEY)
            phone = it.getString(CONTACT_PHONE_NUMBER_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contactName = binding.contactName
        val contactPhone = binding.contactPhone

        contactName.setText(name, TextView.BufferType.EDITABLE)
        contactName.setBackgroundResource(android.R.color.transparent)
        contactName.inputType = InputType.TYPE_NULL

        contactPhone.setText(phone, TextView.BufferType.EDITABLE)
        contactPhone.setBackgroundResource(android.R.color.transparent)
        contactPhone.inputType = InputType.TYPE_NULL
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_contact_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_contact -> {
                binding.contactName.inputType = InputType.TYPE_CLASS_TEXT
                binding.contactPhone.inputType = InputType.TYPE_CLASS_PHONE
            }
            R.id.save_contact -> {

            }
        }
        return true
    }
}