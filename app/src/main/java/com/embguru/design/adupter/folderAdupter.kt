package com.embguru.design.adupter

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.embguru.design.BuildConfig
import com.embguru.design.R
import com.embguru.design.model.folderViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class folderAdupter(private val context: Context, private val mList: List<folderViewModel>) :
    RecyclerView.Adapter<folderAdupter.ViewHolder>() {
    private var Extention = ".zip"

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.folder_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        val path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + ItemsViewModel.categoryName+"_"+ItemsViewModel.folderName + Extention
        val file = File(path)
        if (file.isFile) {
            Log.e("FilePath", file.path)
            holder.progressField.visibility = View.GONE;
            holder.view_btn.visibility = View.VISIBLE;
            holder.downloadBtn.visibility = View.GONE;
        } else {
            holder.progressField.visibility = View.GONE;
            holder.view_btn.visibility = View.GONE;
            holder.downloadBtn.visibility = View.VISIBLE;

        }


        holder.pBar.progress = 0
        holder.Progress.text = "0 %"



        holder.folderName.text = ItemsViewModel.folderName
        holder.categoryName.text = ItemsViewModel.categoryName

        holder.favorite.setOnClickListener {
            if (holder.favoriteFlag) {
                holder.favoriteFlag = false
                holder.favorite.setImageResource(R.drawable.inactive_fav_icon)
            } else {
                holder.favoriteFlag = true
                holder.favorite.setImageResource(R.drawable.active_favorite_icon)
            }

        }
        holder.downloadBtn.setOnClickListener {
            try {

                holder.progressField.visibility = View.VISIBLE;
                holder.downloadBtn.visibility = View.GONE;
                val storage = Firebase.storage(ItemsViewModel.firebaseString)
                val httpsReference = storage.getReferenceFromUrl(ItemsViewModel.folderUrl)

                httpsReference.downloadUrl.addOnSuccessListener { uri ->

                    val url: String = uri.toString()
                    Log.e("Url", uri.toString())

                    val downloadmanager =
                        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    val uri = Uri.parse(url)
                    val request = DownloadManager.Request(uri)
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    request.setDestinationInExternalPublicDir(
                        DIRECTORY_DOWNLOADS,
                        ItemsViewModel.categoryName+"_"+ItemsViewModel.folderName + Extention
                    )
                    val handler = Handler()
                    val downloadId = downloadmanager.enqueue(request)
                    val thread = Thread {
                        var downloading = true;


                        while (downloading) {
                            val query = DownloadManager.Query()
                            query.setFilterById(downloadId);
                            val cursor: Cursor = downloadmanager.query(query)
                            cursor.moveToFirst();
                            if (cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR) > 0) {
                                val index =
                                    cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                                val total =
                                    cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                                val status = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                                val bytesDownloaded = cursor.getInt(index)
                                val bytesTotal = cursor.getInt(total)

                                if (cursor.getInt(status) == DownloadManager.STATUS_SUCCESSFUL) {

                                    handler.post(object : Runnable {
                                        override fun run() {
                                            holder.progressField.visibility = View.GONE
                                            holder.downloadBtn.visibility = View.GONE
                                            holder.view_btn.visibility = View.VISIBLE
                                        }
                                    })
                                    downloading = false
                                }
                                val progress = (bytesDownloaded * 100) / bytesTotal
                                Log.e("progress", progress.toString())

                                holder.pBar.progress = progress
                                handler.post(object : Runnable {
                                    override fun run() {
                                        holder.Progress.text = "$progress %"

                                    }
                                })

                            }
                        }
                    }
                    thread.start()


                }.addOnFailureListener { e ->
                    Log.e("Error", e.toString())
                }
            } catch (e: java.lang.Exception) {
                Log.e("Error", e.message.toString())

            }


        }

        holder.view_btn.setOnClickListener {
            openFile(ItemsViewModel.categoryName+"_"+ItemsViewModel.folderName);
        }

    }

    private fun openFile(FileName: String) {
        val downloadedFile =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator + FileName + Extention)
        val downloadedFilepath = FileProvider.getUriForFile(
            context,
            BuildConfig.APPLICATION_ID + ".provider", downloadedFile
        )
        val pdfOpenIntent = Intent(Intent.ACTION_GET_CONTENT)

        pdfOpenIntent.action = Intent.ACTION_VIEW
        pdfOpenIntent.setDataAndType(downloadedFilepath, "application/*")
        pdfOpenIntent.flags =
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        Log.d(
            TAG, "data received:" +
                    "uri = $downloadedFilepath\n" +
                    "extension = zip" +
                    "path = ${downloadedFile.absolutePath}\n" +
                    "size = ${downloadedFile.totalSpace} "
        )
        CoroutineScope(Dispatchers.Main).launch {
            context.startActivity(pdfOpenIntent)
        }
    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val folderName: TextView = itemView.findViewById(R.id.folderName)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val Progress: TextView = itemView.findViewById(R.id.Progress)
        val favorite: ImageView = itemView.findViewById(R.id.favorite)
        val downloadBtn: LinearLayout = itemView.findViewById(R.id.download_btn)
        val view_btn: LinearLayout = itemView.findViewById(R.id.view_btn)
        val progressField: LinearLayout = itemView.findViewById(R.id.progress_field)
        val pBar: ProgressBar = itemView.findViewById(R.id.pBar)
        var favoriteFlag = false


    }
}
