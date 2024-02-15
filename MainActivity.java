package com.example.bioritmus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> readBevitel;
    private ArrayList<String> readBevitelm;
    private ArrayList<String> readKiadas;
    private ArrayList<String> readKiadasm;
    private ArrayList<String> readAllapot;
    private ArrayList<String> readAllapotm;
    private ArrayList<String> readAdatok;
    private EditText editTextMegnevezes;
    private EditText editTextMennyiseg;
    private RadioGroup radioGroup;
    private RadioButton radioButtonBevitel;
    private RadioButton radioButtonKiadas;
    private RadioButton radioButtonAllapot;
    private RecyclerView Lista;
    private Button gomb;

    private ArrayList<String> loadFileFromAssets(String fileName) {
        ArrayList<String> result = new ArrayList<>();
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);
            String[] lines = text.split("\n");
            for (String line : lines) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    // mentés
    private void saveFileToInternalStorage(String fileName, String content) {
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // radioGroup példányai
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonBevitel = findViewById(R.id.radioButtonBevitel);
        radioButtonKiadas = findViewById(R.id.radioButtonKiadas);
        radioButtonAllapot = findViewById(R.id.radioButtonAllapot);
        // editText példányai
        editTextMegnevezes = findViewById(R.id.editTextText);
        editTextMennyiseg = findViewById(R.id.editTextText2);
        // gomb példánya
        gomb = findViewById(R.id.button);
        // recyclerView példánya
        Lista = findViewById(R.id.recyclerView);
        // beolvasás
        readBevitel = loadFileFromAssets("bevitel.txt");
        readBevitelm = loadFileFromAssets("bevitelm.txt");
        readKiadas = loadFileFromAssets("kiadas.txt");
        readKiadasm = loadFileFromAssets("kiadasm.txt");
        readAllapot = loadFileFromAssets("allapot.txt");
        readAllapotm = loadFileFromAssets("allapotm.txt");
        readAdatok = loadFileFromAssets("adatok.txt");
        // gomb eseménykezelője
        gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Egy string amiben összegyűjtjük a beolvasott adatokat
                String content = "";
                // A szting a mai dátum/ idő és a beírt adatok közöttük és a végén tab
                content += "2021-10-10 12:00:00\t" + editTextMegnevezes.getText().toString() + "\t";
                // végül a checked gomb alpján a string végéhez tűzzük a gomb textjét, tab nélkül,
                // hogy az adatok későbbi feldolgozásánál be tudjuk azonosítani.
                // Majd elmentjük az adatok.txt fileba.
                if (radioButtonBevitel.isChecked()) {
                    content += radioButtonBevitel.getText().toString();
                    saveFileToInternalStorage("adatok.txt", content);
                    // ellenőrizzük, hogy a readBevitel listában előfordul- e a beírt adat.
                    // Ha nem, akkor hozzáadjuk a listához és a listát elmentjük a bevitel.txt fileba.
                    if (!readBevitel.contains(editTextMegnevezes.getText().toString())) {
                        readBevitel.add(editTextMegnevezes.getText().toString());
                        content = "";
                        for (String s : readBevitel) {
                            content += s + "\n";
                        }
                        saveFileToInternalStorage("bevitel.txt", content);
                    }
                    // ellenőrizzük, hogy a readBevitelm listában előfordul- e a beírt adat.
                    // Ha nem, akkor hozzáadjuk a listához és a listát elmentjük a bevitelm.txt fileba.
                    if (!readBevitelm.contains(editTextMegnevezes.getText().toString())) {
                        readBevitelm.add(editTextMegnevezes.getText().toString());
                        content = "";
                        for (String s : readBevitelm) {
                            content += s + "\n";
                        }
                        saveFileToInternalStorage("bevitelm.txt", content);
                    }
                } else if (radioButtonKiadas.isChecked()) {
                    content += radioButtonKiadas.getText().toString();
                    saveFileToInternalStorage("adatok.txt", content);
                    // ellenőrizzük, hogy a readKiadas listában előfordul- e a beírt adat.
                    // Ha nem, akkor hozzáadjuk a listához és a listát elmentjük a kiadas.txt fileba.
                    if (!readKiadas.contains(editTextMennyiseg.getText().toString())) {
                        readKiadas.add(editTextMennyiseg.getText().toString());
                        content = "";
                        for (String s : readKiadas) {
                            content += s + "\n";
                        }
                        saveFileToInternalStorage("kiadas.txt", content);
                    }
                    // ellenőrizzük, hogy a readKiadasm listában előfordul- e a beírt adat.
                    // Ha nem, akkor hozzáadjuk a listához és a listát elmentjük a kiadasm.txt fileba.
                    if (!readKiadasm.contains(editTextMennyiseg.getText().toString())) {
                        readKiadasm.add(editTextMennyiseg.getText().toString());
                        content = "";
                        for (String s : readKiadasm) {
                            content += s + "\n";
                        }
                        saveFileToInternalStorage("kiadasm.txt", content);
                    }
                } else if (radioButtonAllapot.isChecked()) {
                    content += radioButtonAllapot.getText().toString();
                    saveFileToInternalStorage("adatok.txt", content);
                    // ellenőrizzük, hogy a readAllapot listában előfordul- e a beírt adat.
                    // Ha nem, akkor hozzáadjuk a listához és a listát elmentjük a allapot.txt fileba.
                    if (!readAllapot.contains(editTextMegnevezes.getText().toString())) {
                        readAllapot.add(editTextMegnevezes.getText().toString());
                        content = "";
                        for (String s : readAllapot) {
                            content += s + "\n";
                        }
                        saveFileToInternalStorage("allapot.txt", content);
                    }
                    // ellenőrizzük, hogy a readAllapotm listában előfordul- e a beírt adat.
                    // Ha nem, akkor hozzáadjuk a listához és a listát elmentjük a allapotm.txt fileba.
                    if (!readAllapotm.contains(editTextMegnevezes.getText().toString())) {
                        readAllapotm.add(editTextMegnevezes.getText().toString());
                        content = "";
                        for (String s : readAllapotm) {
                            content += s + "\n";
                        }
                        saveFileToInternalStorage("allapotm.txt", content);
                    }
                }
                // A beírt adatokat hozzáadjuk a readAdatok listához és a listát elmentjük az adatok.txt fileba.
                readAdatok.add(content);
                content = "";
                for (String s : readAdatok) {
                    content += s + "\n";
                }
                saveFileToInternalStorage("adatok.txt", content);
            }
        });

        class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

            private ArrayList<String> mDataset= readAdatok;

            class MyViewHolder extends RecyclerView.ViewHolder {
                // each data item is just a string in this case
                public TextView textView;
                public MyViewHolder(TextView v) {
                    super(v);
                    textView = v;
                }

            }

            public MyAdapter(ArrayList<String> myDataset) {
                mDataset = myDataset;
            }

            @Override
            public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                MyViewHolder vh = new MyViewHolder(v);
                return vh;
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                holder.textView.setText(mDataset.get(position));
            }

            @Override
            public int getItemCount() {
                return mDataset.size();
            }
        }
        Lista.setLayoutManager(new LinearLayoutManager(this));
        // a radioGroup checked változásának eseménykezelője
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Ha a bevitel gomb van kiválasztva, akkor a bevitel EditText üres és ő kapja a fókuszt,
                // valamint a listában az readBevitel adatai jelennek meg.
                if (checkedId == R.id.radioButtonBevitel) {
                    editTextMegnevezes.setText("");
                    editTextMegnevezes.requestFocus();
                    Lista.setAdapter(new MyAdapter(readBevitel));
                }
                else if (checkedId == R.id.radioButtonKiadas) {
                    editTextMegnevezes.setText("");
                    editTextMegnevezes.requestFocus();
                    Lista.setAdapter(new MyAdapter(readKiadas));
                }
                else if (checkedId == R.id.radioButtonAllapot) {
                    editTextMegnevezes.setText("");
                    editTextMegnevezes.requestFocus();
                    Lista.setAdapter(new MyAdapter(readAllapot));
                }

            }
        });

        // editTextMegnevezes fókuszba kerülésének eseménye
        editTextMegnevezes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (radioButtonBevitel.isChecked()) {
                        Lista.setAdapter(new MyAdapter(readBevitel));
                    } else if (radioButtonKiadas.isChecked()) {
                        Lista.setAdapter(new MyAdapter(readKiadas));
                    } else if (radioButtonAllapot.isChecked()) {
                        Lista.setAdapter(new MyAdapter(readAllapot));
                    }
                }
            }
        });

        // editTextMennyisega fókuszba kerül
        editTextMennyiseg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (radioButtonBevitel.isChecked()) {
                        Lista.setAdapter(new MyAdapter(readBevitelm));
                    } else if (radioButtonKiadas.isChecked()) {
                        Lista.setAdapter(new MyAdapter(readKiadas));
                    } else if (radioButtonAllapot.isChecked()) {
                        Lista.setAdapter(new MyAdapter(readAllapotm));
                    }
                }
            }
        });
        //A Lista elemének kijelölése
    }

}