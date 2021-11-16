package com.achaka.fragmentshomework

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.achaka.fragmentshomework.databinding.FragmentContactsListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val CONTACT_NAME_KEY = "com.achaka.fragmentshomework.contactName"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsListFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename and change types of parameters
    val contacts =  ArrayList<Contact>()

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contacts.add(Contact("Anme", "", "+7981"))
        LoaderManager.getInstance(this).initLoader(0, null, this)
        arguments?.let {
//            param1 = it.getString(CONTACT_NAME_KEY)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentContactsListBinding.inflate(inflater, container, false)
        addNewContact(binding, contacts[0])
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contentResolver = requireActivity().contentResolver
        val cursor: Cursor?

        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)
        cursor?.close()
    }

    private fun addNewContact(binding: FragmentContactsListBinding, contact: Contact) {
        val linearLayout = binding.linearLayoutContactsList
        val scale = resources.displayMetrics.density
        val paddingDp = (resources.getDimension(R.dimen.padding)/scale).toInt()
//        val textSizeSp = (resources.getDimension(R.dimen.padding)-0.5f)/scale
        val textView = TextView(requireContext())
        textView.text = contact.name
        textView.textSize = 18f
        textView.setPadding(0,paddingDp,0, 0)
        textView.setOnClickListener {
            val args = Bundle()
            args.putString(CONTACT_NAME_KEY,contact.name)
            parentFragmentManager.beginTransaction()
                .replace(R.id.contacts_fragment_container_view, ContactDetailsFragment::class.java, args)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }
        linearLayout.addView(textView)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactsListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsListFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}