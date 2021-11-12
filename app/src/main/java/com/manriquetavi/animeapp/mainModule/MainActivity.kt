package com.manriquetavi.animeapp.mainModule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.manriquetavi.animeapp.R
import com.manriquetavi.animeapp.bioModule.BioFragment
import com.manriquetavi.animeapp.bioModule.viewModel.BioFragmentViewModel
import com.manriquetavi.animeapp.common.entities.AnimeEntity
import com.manriquetavi.animeapp.mainModule.adapter.AnimeAdapter
import com.manriquetavi.animeapp.databinding.ActivityMainBinding
import com.manriquetavi.animeapp.mainModule.adapter.OnClickListener
import com.manriquetavi.animeapp.mainModule.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mAdapter: AnimeAdapter
    private lateinit var mGridlayout: GridLayoutManager

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mBioFragmentViewModel: BioFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mAdapter = AnimeAdapter(mutableListOf(), this)
        mGridlayout = GridLayoutManager(this, resources.getInteger(R.integer.main_columns_rv))

        mBinding.rvAnimeMain.apply {
            setHasFixedSize(true)
            layoutManager = mGridlayout
            adapter = mAdapter
        }
    }

    override fun onClick(animeEntity: AnimeEntity) {
        launchBioFragment(animeEntity)
    }

    private fun launchBioFragment(animeEntity: AnimeEntity = AnimeEntity()) {
        val fragment = BioFragment()
        val fragmentManager =  supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val bundle = Bundle()
        bundle.putString(getString(R.string.title_text_bundle), animeEntity.title)
        bundle.putString(getString(R.string.image_text_bundle), animeEntity.coverImage)
        bundle.putString(getString(R.string.date_text_bundle), animeEntity.creationAt)
        bundle.putString(getString(R.string.description_text_bundle), animeEntity.description)
        bundle.putSerializable("key",animeEntity)

        fragment.arguments = bundle
        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun setupViewModel() {
        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mMainViewModel.getAnimes().observe(this, { animes ->
            mAdapter.setAnimes(animes)
        })
       // mBioFragmentViewModel = ViewModelProvider(this).get(BioFragmentViewModel::class.java)
        
    }
}