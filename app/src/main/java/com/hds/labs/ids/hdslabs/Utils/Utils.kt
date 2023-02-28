package com.hds.labs.ids.hdslabs.Utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.telephony.SignalThresholdInfo
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.hds.labs.ids.hdslabs.Interface.DateCallback
import com.hds.labs.ids.hdslabs.R
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class Utils {

    companion object{
        private lateinit var mProgressDialog: Dialog
        var DirectoryNameForInsta = "/DrGit/LogBook/"
        var DirectoryInstaShow = File(
            Environment.getExternalStorageDirectory().toString() + "/Download/DrGit/LogBook"
        )


        fun showProgressDialog(mContext: Context){
            try {
                mProgressDialog = Dialog(mContext)
                mProgressDialog.setContentView(R.layout.dialog_progress)
                mProgressDialog.setCancelable(true)
                mProgressDialog.setCanceledOnTouchOutside(false)
                mProgressDialog.show()
            } catch (e: Exception) {
            }
        }

        fun hideProgressDialog(mContext: Context){
            try {
                mProgressDialog.dismiss()
            } catch (e: Exception) {
            }
        }

        fun createFileFolder() {
            if (!DirectoryInstaShow.exists()) {
                DirectoryInstaShow.mkdirs()
            }
        }



        fun selectDateFromDatePicker(context: Context , call: DateCallback.DateCallback) {
            val mCalendar = Calendar.getInstance()
            val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
            val mMonth = mCalendar.get(Calendar.MONTH)
            val mYear = mCalendar.get(Calendar.YEAR)
            var mDate = ""
            val datePickerDialog = DatePickerDialog(
                context, { view, year, month, dayOfMonth ->
                    //binding.etDate.text = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                    mDate = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
                    call.onclickDate(mDate)},
                mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        fun getCurrentDateTime(mContext: Context, dateTimeType:Int):String {
            val calendar = Calendar.getInstance()
            val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm:a")
            val dateTime = simpleDateFormat.format(calendar.time).toString()

            val delimiter = " "
            val parts = dateTime.split(delimiter)
            val currentTime = parts[1]
            val currentDate = parts[0]
            var data = ""
            when (dateTimeType) {
                1 ->
                    data= "${currentTime} $currentDate"
                2 ->
                    data = currentDate
                3 ->
                    data = currentTime
                5 ->
                    data = "logBook${currentDate}and$currentTime"
            }
            return data
        }



        fun setToast(_mContext: Context?, str: String?) {
            val toast = Toast.makeText(_mContext, str, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }

    }
}