package com.silverkeytech.easyfunder.Utility

import android.os.AsyncTask
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
/**
 * Created by samuel on 9/11/2016.
 */

class Connection:  AsyncTask<String, Void, String>() {

    private val LOG_TAG = Connection::class.java!!.getSimpleName()

    override fun doInBackground(vararg params: String?): String {
        var connect: HttpURLConnection? = null

        var url: URL? = null
        var jasonString: String? = null
        var reader: BufferedReader? = null





        try
        {



            url = URL(params[0])
            connect=url.openConnection() as HttpURLConnection
            connect.requestMethod = params[1]
            connect.setRequestProperty("Content-Type","application/json; charset=utf-8;")
            if(params[1].equals("POST"))
            {
                connect.doOutput = true

                var os: OutputStream = connect.outputStream
                var write: BufferedWriter = BufferedWriter(OutputStreamWriter(os,"UTF-8"))
                write.write(params[2])

                write.flush()
                write.close()
                os.close()

            }

//            connect.setChunkedStreamingMode(0)
//            connect.doInput = true
//            connect.useCaches = false


//            var list:Map<String,String> = HashMap<String,String>()
//            list["email"].plus("ahmed@gmail.com")
//            list["password"].plus("123")






            connect.connect()
            var statusCode = connect.getResponseCode()

            Log.d(LOG_TAG,statusCode.toString());
            var inputStream = connect.inputStream
//            if (statusCode >= 200 && statusCode < 400) {
//                // Create an InputStream in order to extract the response object
//                inputStream = connect.inputStream as Nothing?
//           }
//            else
//            {
//                inputStream = connect.errorStream as Nothing?
//            }
            var buffer =StringBuffer()
            if (inputStream == null)
            {
                // Nothing to do.
                return null.toString()
            }
            reader = BufferedReader(InputStreamReader(inputStream))
            var line :String?
            line = reader.readLine()
            while (line!=null)
            {
                buffer.append(line)
                line = reader.readLine()
            }
            if(buffer.length==0)
            {
                return  "Null";
            }

            jasonString = buffer.toString()
            // return reader.toString()
        }
        catch (e : IOException)
        {
            Log.i(LOG_TAG , e.toString())
            return e.toString();
        }
        finally
        {
            if (connect != null)
            {
                connect.disconnect()
            }
            if (reader != null) {
                try
                {
                    reader.close()
                }
                catch (e: IOException)
                {
                    Log.e(LOG_TAG, "Error closing stream", e)
                }

            }
        }
        connect?.disconnect()
        return jasonString.toString()


    }

}