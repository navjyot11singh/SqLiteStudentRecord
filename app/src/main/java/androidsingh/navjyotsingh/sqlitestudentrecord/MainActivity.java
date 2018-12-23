package androidsingh.navjyotsingh.sqlitestudentrecord;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editId,editName,editEmail,editCC;
    Button buttonAdd,buttonGetData,buttonUpdate,buttonViewAll,buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB=new DatabaseHelper(this);

        editId=findViewById(R.id.editText_id);
        editName=findViewById(R.id.editText_name);
        editEmail=findViewById(R.id.editText_email);
        editCC=findViewById(R.id.editText_CC);

        buttonAdd=findViewById(R.id.button_add);
        buttonUpdate=findViewById(R.id.button_update);
        buttonDelete=findViewById(R.id.button_delete);
        buttonGetData=findViewById(R.id.button_view);
        buttonViewAll=findViewById(R.id.button_viewAll);

        AddData();
        getData();
        viewAll();
        updataData();
        databaseList();
        deleteData();

    }

    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              boolean isInserted=myDB.insertData(editName.getText().toString(),editEmail.getText().toString()
                      ,editCC.getText().toString());
                if (isInserted==true){
                      Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void getData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=editId.getText().toString();
                if (id.equals(String.valueOf(""))){
                    editId.setError("Enter Id");
                    return;
                }
                Cursor cursor=myDB.getData(id);
                String data=null;
                if (cursor.moveToNext()){
                    data="ID:"+cursor.getString(0)+ "\n"+
                            "Name:"+cursor.getString(1)+ "\n"+
                            "Email:"+cursor.getString(2)+ "\n"+
                            "Course Count:"+cursor.getString(3)+ "\n";
                }
                showMessage("Data: ", data);

            }
        });
    }
    public void viewAll(){

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=myDB.getAllData();

                if (cursor.getCount()==0){
                    showMessage("Error","Nothing found in database");
                    return;
                }

                StringBuffer stringBuffer=new StringBuffer();

                while (cursor.moveToNext()){
                    stringBuffer.append("ID: "+cursor.getString(0)+"\n");
                    stringBuffer.append("Name: "+cursor.getString(1)+"\n");
                    stringBuffer.append("Email: "+cursor.getString(2)+"\n");
                    stringBuffer.append("Course count: "+cursor.getString(3)+"\n \n");
                }
                showMessage("All Data",stringBuffer.toString());


            }
        });

    }
    public void updataData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=myDB.updateData(editId.getText().toString(),
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editCC.getText().toString());

                if (isUpdate==true){
                    Toast.makeText(MainActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"OOPSS!",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    public void deleteData(){

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=editId.getText().toString();
                if(id.equals(String.valueOf(""))){
                    editId.setError("Must provide ID");
                    return;
                }

                Integer deleteRow=myDB.deleteData(editId.getText().toString());
                if (deleteRow>0){
                    Toast.makeText(MainActivity.this,"Delete success",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"OOpSS!",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}
