package com.bogdan.cryptodashboard.model

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.cryptodashboard.R
import com.squareup.picasso.Picasso

class CardInfoViewAdapter(
    context: Context,
    private val cardInfo: List<CardInfo>
) :
    RecyclerView.Adapter<CardInfoViewAdapter.Companion.ViewHolder>() {

    private var inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = inflater.inflate(R.layout.card_info_data, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCardInfo = cardInfo[position]
        holder.currencyName.text = currentCardInfo.name
        holder.currencyAmount.text = currentCardInfo.crypto_amount
        holder.currencyFiatAmount.text = currentCardInfo.fiat_amount
        updateCurrencyLogo(holder.currencyLogo, currentCardInfo)
    }

    private fun updateCurrencyLogo(
        currencyLogo: AppCompatImageView,
        currentCardInfo: CardInfo?
    ) {
        Picasso.get()
            .load(Uri.parse(currentCardInfo?.logoUrl))
            .fit()
            .into(currencyLogo)
    }

    override fun getItemCount(): Int {
        return cardInfo.size
    }

    companion object {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var currencyLogo: AppCompatImageView =
                itemView.findViewById(R.id.card_info_currency_logo)
            var currencyName: TextView = itemView.findViewById(R.id.card_info_currency_name)
            var currencyAmount: TextView = itemView.findViewById(R.id.card_info_currency_amount)
            var currencyFiatAmount: TextView =
                itemView.findViewById(R.id.card_info_currency_fiat_amount)
        }
    }
}
