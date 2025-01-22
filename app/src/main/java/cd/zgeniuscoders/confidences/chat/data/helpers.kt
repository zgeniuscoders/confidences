package cd.zgeniuscoders.confidences.chat.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getTimeAgo(timestamp: Long): String {

    val currentTimeMills = System.currentTimeMillis()
    val elapsedMillis = (currentTimeMills - timestamp)

    val totalHours = (elapsedMillis / (1000 * 60 * 60))

    val date = Date(timestamp)

    val result = when {

        totalHours <= 24 -> {
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            format.format(date)
        }


        else -> {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            format.format(date)
        }
    }


    return result

}