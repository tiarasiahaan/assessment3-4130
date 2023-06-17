package org.d3if4130.hitungnilaiakhir.ui.histori

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if4130.hitungnilaiakhir.R
import org.d3if4130.hitungnilaiakhir.databinding.ItemHistoriBinding
import org.d3if4130.hitungnilaiakhir.db.NilaiEntity
import org.d3if4130.hitungnilaiakhir.model.KategoriNilai
import org.d3if4130.hitungnilaiakhir.model.hitungNilai
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter :
    ListAdapter<NilaiEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<NilaiEntity>() {
                override fun areItemsTheSame(
                    oldData: NilaiEntity, newData: NilaiEntity
                ): Boolean {
                    return oldData.id == newData.id
                }

                override fun areContentsTheSame(
                    oldData: NilaiEntity, newData: NilaiEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root){

        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )

        fun bind(item: NilaiEntity) = with(binding) {
            val hasilNilai = item.hitungNilai()

            kategoriTextView.text = hasilNilai.kategoriNilai.toString().substring(0, 1)
            val colorRes = when (hasilNilai.kategoriNilai) {
                KategoriNilai.B -> R.color.B
                KategoriNilai.A -> R.color.A
                else -> R.color.C
            }
            val circleBg = kategoriTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            namaTextView.text = root.context.getString(
                R.string.hasil_x,
                hasilNilai.nama, hasilNilai.kategoriNilai
            )
            akhirTextView.text = root.context.getString(
                R.string.data_x,
                hasilNilai.hasil)
        }
    }
}