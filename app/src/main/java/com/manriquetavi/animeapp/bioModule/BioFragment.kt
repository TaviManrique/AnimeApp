package com.manriquetavi.animeapp.bioModule

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.manriquetavi.animeapp.R
import com.manriquetavi.animeapp.bioModule.viewModel.BioFragmentViewModel
import com.manriquetavi.animeapp.common.entities.AnimeEntity
import com.manriquetavi.animeapp.databinding.FragmentBioBinding
import com.manriquetavi.animeapp.mainModule.MainActivity

class BioFragment : Fragment() {

    private lateinit var mBinding: FragmentBioBinding
    private lateinit var mBioFragmentViewModel: BioFragmentViewModel
    private var mActivity: MainActivity? = null
    private lateinit var mAnimeEntity: AnimeEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBioFragmentViewModel = ViewModelProvider(requireActivity()).get(BioFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentBioBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        mBioFragmentViewModel.getAnimeSelected().observe(viewLifecycleOwner, {
            mAnimeEntity = it
            setUiAnime(it)
            setupActionBar()
        })

        mBioFragmentViewModel.getResult().observe(viewLifecycleOwner, { result ->
            when(result){
                is Int -> {
                    mAnimeEntity.id = result
                    mBioFragmentViewModel.setAnimeSelected(mAnimeEntity)
                    mActivity?.onBackPressed()
                }
            }
        })
    }

    private fun setUiAnime(animeEntity: AnimeEntity) {
        with(mBinding) {
            tvTitle.text = animeEntity.title
            tvDate.text = animeEntity.creationAt
            tvDescription.text = animeEntity.description
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        hideKeyboard()
        super.onDestroyView()
    }

    private fun hideKeyboard() {
        val imm = mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(view != null) {
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }
    }



    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mBioFragmentViewModel.setResult(Any())

        setHasOptionsMenu(false)
        super.onDestroy()
    }

}