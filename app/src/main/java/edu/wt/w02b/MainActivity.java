package edu.wt.w02b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MainActivity extends AppCompatActivity {
    public final String FILENAME = "settings_file";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String savedString;

        try {
            FileInputStream fis = openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            savedString = (String)ois.readObject();
            ois.close();
        }
        catch(FileNotFoundException e) {
            Log.e("Pliki", "Brak pliku do odczytu");
            savedString="brak";
            e.printStackTrace();
        }
        catch(IOException e) {
            Log.e("Pliki", "IOException przy odczycie");
            savedString="brak";
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.e("Pliki", "Błąd rzutowania klasy");
            savedString="brak";
            e.printStackTrace();
        }
        TextView tv = (TextView)findViewById(R.id.savedString);
        tv.setText(savedString);

        displayFilesInfo();
    }

    private void displayFilesInfo() {
        File privFolder = getFilesDir();
        String info = "Prywatny folder:\n[" + privFolder.toString() + "]\n";
        String files[] = fileList();
        info += "Pliki\n";
        for(String f:files) {
            info += "[" + f + "]\n";
        }

        TextView tv=(TextView)findViewById(R.id.filesInfo);
        tv.setText(info);
    }

    @Override
    protected void onStop() {
        super.onStop();

        TextView tv = (TextView) findViewById(R.id.savedString);
        String str = tv.getText().toString();

        try {
            FileOutputStream fos = openFileOutput(FILENAME, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(str);
            oos.close();
        }
        catch(IOException e) {
            Log.e("Pliki", "IOException przy zapisie");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSave(View v) {
        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();

    }
}
