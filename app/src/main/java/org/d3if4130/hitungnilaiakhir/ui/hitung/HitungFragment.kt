package org.d3if4130.hitungnilaiakhir.ui.hitung

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if4130.hitungnilaiakhir.R
import org.d3if4130.hitungnilaiakhir.databinding.FragmentHitungBinding
import org.d3if4130.hitungnilaiakhir.db.Nilai
import org.d3if4130.hitungnilaiakhir.model.HasilNilai
import org.d3if4130.hitungnilaiakhir.model.KategoriNilai

class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding

    private val viewModel : HitungViewModel by lazy {
        val db = Nilai.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory) [HitungViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener{ hitungNilai() }
        binding.resetButton.setOnClickListener{ reset() }
        viewModel.getHasilNilai().observe(requireActivity(), { showResult(it) })
        setupObserveres()
        binding.saran.setOnClickListener {
            viewModel.mulaiNavigasi() }
        binding.bagikan.setOnClickListener {
            shareData()
        }

    }

    private fun setupObserveres() {
        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if(it == null) return@observe
            findNavController().navigate(HitungFragmentDirections.actionHitungFragmentToSaranFragment(it))
            viewModel.selesaiNavigasi()
        })
        viewModel.getHasilNilai().observe(requireActivity(), { showResult(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
          R.id.menu_histori -> {
               findNavController().navigate(R.id.action_hitungFragment_to_historiFragment)
                return true
           }
            R.id.menu_about -> {
                findNavController().navigate(
                    R.id.action_hitungFragment_to_aboutFragment
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("StringFormatMatches")
    private fun showResult(result: HasilNilai?){
        if (result == null) return
        binding.namaTextView2.text = getString(R.string.nama_siswa, result.nama)
        binding.akhirTextView.text = getString(R.string.akhir_n, result.hasil)
        binding.hurufTextView.text = getString(R.string.huruf_n, getHurufLabel(result.kategoriNilai))
        binding.saran.visibility = View.VISIBLE
        binding.bagikan.visibility = View.VISIBLE
    }

    private fun shareData(){
        val massage = getString(R.string.bagikan_template,
            binding.namaTextView2.text,
            binding.akhirTextView.text,
            binding.hurufTextView.text)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, massage)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null){
            startActivity(shareIntent)
        }
    }

    private fun reset(){
        binding.namaEditText.text?.clear()
        binding.hadirEditText.text?.clear()
        binding.tugasEditText.text?.clear()
        binding.utsEditText.text?.clear()
        binding.uasEditText.text?.clear()
        binding.namaTextView2.text = ""
        binding.akhirTextView.text = ""
        binding.hurufTextView.text = ""
        binding.saran.visibility = View.INVISIBLE
        binding.bagikan.visibility = View.INVISIBLE
    }

    private fun hitungNilai(){
        val nama = binding.namaEditText.text.toString()
        if(TextUtils.isEmpty(nama)){
            Toast.makeText(context, R.string.nama_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val kehadiran = binding.hadirEditText.text.toString()
        if(TextUtils.isEmpty(kehadiran)){
            Toast.makeText(context, R.string.hadir_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val tugas = binding.tugasEditText.text.toString()
        if(TextUtils.isEmpty(tugas)){
            Toast.makeText(context, R.string.tugas_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val uts = binding.utsEditText.text.toString()
        if(TextUtils.isEmpty(uts)){
            Toast.makeText(context, R.string.uts_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val uas = binding.uasEditText.text.toString()
        if(TextUtils.isEmpty(uas)){
            Toast.makeText(context, R.string.uas_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungNilai(nama, kehadiran.toFloat(), tugas.toFloat(), uts.toFloat(), uas.toFloat())

    }

    private fun getHurufLabel(kategori: KategoriNilai): String {
        val stringRes = when (kategori){
            KategoriNilai.A -> R.string.nilai_a
            KategoriNilai.B -> R.string.nilai_b
            KategoriNilai.C -> R.string.nilai_c
        }
        return getString(stringRes)
    }
}