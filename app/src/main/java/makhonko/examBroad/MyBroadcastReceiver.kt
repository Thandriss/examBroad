package makhonko.examBroad

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BATTERY_CHANGED
import android.os.BatteryManager
import android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION
import android.provider.Telephony.Sms.Intents.getMessagesFromIntent
import android.telephony.SmsMessage
import java.util.logging.Logger

class MyBroadcastReceiver: BroadcastReceiver() {
    private val logger = Logger.getLogger(this::javaClass.name)

    override fun onReceive(p0: Context?, p1: Intent?) {
        when (p1?.action) {
            ACTION_BATTERY_CHANGED -> {
                val extras = p1.extras!!
                val level = extras.getInt(BatteryManager.EXTRA_LEVEL, -1)
                val scale = extras.getInt(BatteryManager.EXTRA_SCALE, -1)
                logger.info("Battery level: ${(level * 100)/scale.toFloat()}%")
            }
            SMS_RECEIVED_ACTION -> {
               val smsMessages: Array<SmsMessage> = getMessagesFromIntent(p1)
                smsMessages.forEach {
                    logger.info(it.displayMessageBody)
                }
            }
        }
    }
}