package org.d3if4130.hitungnilaiakhir.ui.saran

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import org.d3if4130.hitungnilaiakhir.LinkGambar
import org.d3if4130.hitungnilaiakhir.R
import org.d3if4130.hitungnilaiakhir.databinding.FragmentSaranBinding
import org.d3if4130.hitungnilaiakhir.model.KategoriNilai
import org.d3if4130.hitungnilaiakhir.network.ApiStatus
import org.d3if4130.hitungnilaiakhir.network.SaranApi


class SaranFragment : Fragment() {
    private lateinit var binding: FragmentSaranBinding
    private val args: SaranFragmentArgs by navArgs()

    private lateinit var viewModel: SaranViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaranBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        const val CHANNEL_ID = "updater"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = SaranApi.service
        val factory = SaranViewModelFactory(api)
        viewModel = ViewModelProvider(this, factory)[SaranViewModel::class.java]
    }


    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val kategori = args.kategori
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        viewModel.getHasilNilai.observe(this@SaranFragment) { data ->
            Log.i("Data: ", data.toString())
            if (data != null) {
                when (kategori) {
                    KategoriNilai.C -> {
                        actionBar?.title = getString(R.string.judul_C)
                        binding.textView.text = data[2]?.saranC
                        Glide.with(requireActivity())
                            .load(Uri.parse(LinkGambar.Gambar_C))
                            .into(binding.gambarSaran)
                    }
                    KategoriNilai.A -> {
                        actionBar?.title = getString(R.string.judul_A)
                        binding.textView.text = data[0]?.saranA
                        Glide.with(requireActivity())
                            .load(Uri.parse(LinkGambar.Gambar_A))
                            .into(binding.gambarSaran)
                    }
                    KategoriNilai.B -> {
                        actionBar?.title = getString(R.string.judul_B)
                        binding.textView.text = data[1]?.saranB
                        Glide.with(requireActivity())
                            .load(Uri.parse(LinkGambar.Gambar_B))
                            .into(binding.gambarSaran)
                    }
                }
            }
        }

        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }

        viewModel.scheduleUpdater(requireActivity().application)
    }



    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}