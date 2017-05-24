package ej6.adsw.dit.upm.es.imcandigcecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getName();
    private int chosen_sex=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText altura = (EditText) findViewById(R.id.height);
        final EditText peso = (EditText) findViewById(R.id.weight);
        Button calcular = (Button) findViewById(R.id.calculate);
        final TextView imcResult = (TextView) findViewById(R.id.resultIMC);
        final TextView imcClassification = (TextView) findViewById(R.id.classificationIMC);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        final RadioButton hombre = (RadioButton) findViewById(R.id.man);
        final RadioButton mujer = (RadioButton) findViewById(R.id.woman);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        final TextView igceResult = (TextView) findViewById(R.id.resultIGCE);
        final TextView igceClassification = (TextView) findViewById(R.id.classificationIGCE);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "Pulsado ");
                int id = group.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.man: {
                        Log.d(TAG, "hombre");
                        chosen_sex = 1;
                        break;
                    }
                    case R.id.woman: {
                        chosen_sex = 0;
                        Log.d(TAG, "mujer");
                        break;
                    }
                    default: {
                        chosen_sex = 2;
                        Log.e(TAG, "Inesperado " + id);
                    }
                }
            }
        });


        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Double a = Double.valueOf(altura.getText().toString()) / 100;     //altura en metros
                    Double b = Double.valueOf(peso.getText().toString());

                    if (a <= 0 || b <= 0) {
                        throw new Exception(getString(R.string.error));
                    }


                    Double r = b / (a * a);
                    Double imc = Math.rint(r * 100) / 100;
                    imcResult.setText(getString(R.string.imc) + imc);
                    imcResult.setVisibility(View.VISIBLE);
                    if (r < 18.5) {
                        imcClassification.setText(R.string.underweight);
                        imcClassification.setVisibility(View.VISIBLE);
                    } else if (r >= 18.5 && r < 25) {
                        imcClassification.setText(R.string.healthy);
                        imcClassification.setVisibility(View.VISIBLE);
                    } else if (r >= 25 && r < 30) {
                        imcClassification.setText(R.string.overweight);
                        imcClassification.setVisibility(View.VISIBLE);
                    } else if (r >= 30) {
                        imcClassification.setText(R.string.obese);
                        imcClassification.setVisibility(View.VISIBLE);
                    }

                    final int edad = calculaEdad(1, 1, datePicker.getYear());
                    if (edad > 0 && chosen_sex != 2) {
                        double igce;
                        if (edad <= 15) {
                            igce = 1.51 * imc - 0.7 * edad - 3.6 * chosen_sex + 1.4;

                        } else {
                            igce = 1.2 * imc + 0.23 * edad - 10.8 * chosen_sex - 5.4;

                        }
                        double resultado = Math.rint(igce*100) / 100;
                        igceResult.setText(getString(R.string.sbfi) + resultado+"%");
                        igceResult.setVisibility(View.VISIBLE);
                        double a1;
                        double a2;
                        double a3;
                        double a4;

                        if(chosen_sex==0) {
                            a1=12;
                            a2=20;
                            a3=24;
                            a4=32;

                        }else{
                            a1=5;
                            a2=13;
                            a3=17;
                            a4=24;
                        }
                        if(igce<=a1) {
                            igceClassification.setText(R.string.essential_fat);
                            igceClassification.setVisibility(View.VISIBLE);
                        }else if(a1<igce && igce <=a2){
                            igceClassification.setText(R.string.athletes);
                            igceClassification.setVisibility(View.VISIBLE);
                        }else if(a2<igce && igce <=a3){
                            igceClassification.setText(R.string.fitness);
                            igceClassification.setVisibility(View.VISIBLE);
                        }else if(a3<igce && igce<=a4){
                            igceClassification.setText(R.string.average);
                            igceClassification.setVisibility(View.VISIBLE);
                        }else{
                            igceClassification.setText(R.string.excess_fat);
                            igceClassification.setVisibility(View.VISIBLE);
                        }
                    }
                    if (edad < 0 && chosen_sex == 2) {
                        Toast.makeText(getApplication(), R.string.dateAndgenderError, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (edad < 0) {
                        Toast.makeText(getApplication(), R.string.dateError, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (chosen_sex == 2) {
                        Toast.makeText(getApplication(), R.string.genderError, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e(TAG, "Error" + e);
                    Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
   /*hombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int edad = calculaEdad(1,1,datePicker.getYear());
                if (edad > 0){
                    Double a = Double.valueOf(altura.getText().toString())/100;
                    Double b = Double.valueOf(peso.getText().toString());
                    Double r = b/(a*a);
                    Double imc = Math.rint(r*100)/100;
                    if (edad <= 15) {
                        double i = 1.51*imc - 0.7*edad - 3.6*sexo + 1.4;
                        double resultado = Math.rint(i*100)/100;
                        igceResult.setText(" " + resultado);
                        igceResult.setVisibility(View.VISIBLE);
                    } else {
                        double i = 1.2*imc + 0.23*edad - 10.8*sexo - 5.4;
                        double resultado = Math.rint(i*100)/100;
                        igceResult.setText(" " + resultado);
                        igceResult.setVisibility(View.VISIBLE);
                    }
                }
                if (edad < 0){
                    Toast.makeText(getApplication(), "Introduzca fecha correcta", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        mujer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int edad = calculaEdad(1,1,datePicker.getYear());
                if (edad > 0){
                    Double a = Double.valueOf(altura.getText().toString())/100;
                    Double b = Double.valueOf(peso.getText().toString());
                    Double r = b/(a*a);
                    Double imc = Math.rint(r*100)/100;
                    if (edad <= 15) {
                        double i = 1.51*imc - 0.7*edad - 3.6*sexo + 1.4;
                        double resultado = Math.rint(i*100)/100;
                        igceResult.setText(" " + resultado);
                        igceResult.setVisibility(View.VISIBLE);
                    } else {
                        double i = 1.2*imc + 0.23*edad - 10.8*sexo - 5.4;
                        double resultado = Math.rint(i*100)/100;
                        igceResult.setText(" " + resultado);
                        igceResult.setVisibility(View.VISIBLE);
                    }
                }
                if (edad < 0){
                    Toast.makeText(getApplication(), "Introduzca fecha correcta", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
    */
    private int calculaEdad(int dia, int mes, int anio) {
        java.util.Calendar nacimiento= java.util.Calendar.getInstance();
        nacimiento.set(anio, mes, dia);
        return java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - nacimiento.get(java.util.Calendar.YEAR);
    }
}
