package com.example.glancewidget.glance

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StockAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = StockAppWidget()

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        CoroutineScope(Dispatchers.IO).launch {
            PriceDataRepo.update()
        }
    }
}