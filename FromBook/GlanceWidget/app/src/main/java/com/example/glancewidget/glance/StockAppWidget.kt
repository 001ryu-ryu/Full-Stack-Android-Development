package com.example.glancewidget.glance

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Interval
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration

class StockAppWidget : GlanceAppWidget() {
    private var job: Job? = null

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        if (job == null) {
            job = starUpdateJob(
                Duration.ofSeconds(20).toMillis(),
                context
            )
        }
        provideContent {
            GlanceTheme {
                GlanceContent()
            }
        }
    }

    private fun starUpdateJob(timeInterval: Long, context: Context): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                PriceDataRepo.update()
                updateAll(context) // ← this will now call the companion function
                delay(timeInterval)
            }
        }
    }

    @Composable
    fun GlanceContent() {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(GlanceTheme.colors.background)
                .padding(8.dp)
        ) {
            Text("Demo")
        }
    }

    companion object {
        suspend fun updateAll(context: Context) {
            GlanceAppWidgetManager(context).getGlanceIds(StockAppWidget::class.java).forEach { glanceId ->
                StockAppWidget().update(context, glanceId)
            }
        }
    }
}

































