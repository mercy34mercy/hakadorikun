package com.example.myapplication

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


class LocalNotificationWorker(context: Context, workerParams: WorkerParameters) :
        Worker(context, workerParams) {
        override fun doWork(): Result {
            val pendingIntent = PendingIntent.getActivity(applicationContext,
                4,
                Intent(applicationContext, home3::class.java), PendingIntent.FLAG_ONE_SHOT)
            val GROUP_KEY_WORK_EMAIL = inputData.getString("title")

            val notification = NotificationCompat.Builder(applicationContext, "default")
                .setContentTitle(inputData.getString("title")) // enqueue元から渡されたタイトルテキストを通知にセット
                .setContentText(inputData.getString("text"))
                .setSmallIcon(R.drawable.iconhakadorikun)
                .setContentIntent(pendingIntent)
                .setVisibility(VISIBILITY_PUBLIC)
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .build()


            val newMessageNotification1 = NotificationCompat.Builder(applicationContext, "default")
                .setSmallIcon(R.drawable.iconhakadorikun)
                .setContentTitle("minititle3")
                .setContentText("You will not believe...")
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .build()

            val newMessageNotification2 = NotificationCompat.Builder(applicationContext, "default")
                .setSmallIcon(R.drawable.iconhakadorikun)
                .setContentTitle("minititle2")
                .setContentText("Please join us to celebrate the...")
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .build()

            val summaryNotification = NotificationCompat.Builder(applicationContext, "default")
                .setContentTitle("title")
                //set content text to support devices running API level < 24
                .setContentText("Two new messages")
                .setSmallIcon(R.drawable.iconhakadorikun)
                //build summary info into InboxStyle template
                .setStyle(NotificationCompat.InboxStyle()
                    .addLine("Alex Faarborg Check this out")
                    .addLine("Jeff Chang Launch Party")
                    .setBigContentTitle("2 new messages")
                    .setSummaryText("課題"))
                //specify which group this notification belongs to
                .setGroup(GROUP_KEY_WORK_EMAIL)
                //set this notification as the summary for the group
                .setGroupSummary(true)
                .build()

            NotificationManagerCompat.from(applicationContext).apply {
                notify(inputData.getLong("id",0).toInt(), notification)
            }


            //NotificationManagerCompat.from(applicationContext).notify(1, notification)

            return Result.success()
        }
    }