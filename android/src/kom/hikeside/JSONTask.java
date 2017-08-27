package kom.hikeside;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Koma on 12.08.2017.
 */


    public class JSONTask extends AsyncTask<String, String, String>{
        String urlstr = "";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
        protected String doInBackground(String... params) {

            urlstr = "https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoList.txt";
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            StringBuffer buffer = new StringBuffer();

            try{
                URL url = new URL(urlstr);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));


                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                Log.d("Output:", buffer.toString());

            }catch(MalformedURLException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                if(connection != null)
                    connection.disconnect();
                try {
                    if(reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            String finalJson = buffer.toString();

            String firstStr = null;
            int secondStr = 0;
            StringBuffer finalBufferedData  = null;

            try {
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("movies");

                finalBufferedData = new StringBuffer();

                for(int i = 0; i <parentArray.length(); ++i) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    firstStr = finalObject.getString("movie");
                    secondStr = finalObject.getInt("year");
                    finalBufferedData.append(firstStr + " " + secondStr + "\n");
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Log.d("Output:", finalBufferedData.toString());

            return finalBufferedData != null ? finalBufferedData.toString() : null;
        }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}



