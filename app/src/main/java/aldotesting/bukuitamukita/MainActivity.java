package aldotesting.bukuitamukita;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText message;
    EditText id_data;
    Button submit;
    Button viewAll;
    Button update;
    Button delete;

    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        message = (EditText) findViewById(R.id.message);
        id_data = (EditText) findViewById(R.id.id_data);
        submit = (Button) findViewById(R.id.submit);
        viewAll = (Button) findViewById(R.id.viewAll);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);

        mydb = new DatabaseHelper(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = mydb.insertdata(name.getText().toString(), email.getText().toString(), message.getText().toString());
                id_data.setText("");
                name.setText("");
                email.setText("");
                message.setText("");
                if (isInserted == true)
                {
                    Toast.makeText(MainActivity.this,"DATA INSERTED",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"ERROR INSERTING DATA",Toast.LENGTH_LONG).show();
                }
            }
        });
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = mydb.GetAllData();
                if(res.getCount() == 0)
                {
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext())
                {
                    buffer.append("Id : "+ res.getString(0)+"\n");
                    buffer.append("Name : "+ res.getString(1)+"\n");
                    buffer.append("Email : "+ res.getString(2)+"\n");
                    buffer.append("Message : "+ res.getString(3)+"\n\n");
                }

                showMessage("Data", buffer.toString());
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = mydb.updatedata(id_data.getText().toString(), name.getText().toString(), email.getText().toString(), message.getText().toString());
                id_data.setText("");
                name.setText("");
                email.setText("");
                message.setText("");
                if (isUpdate == true)
                {
                    Toast.makeText(MainActivity.this,"DATA UPDATED",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"ERROR UPDATING DATA",Toast.LENGTH_LONG).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedata = mydb.deletedata(id_data.getText().toString());
                id_data.setText("");
                name.setText("");
                email.setText("");
                message.setText("");
                if (deletedata > 0 )
                {
                    Toast.makeText(MainActivity.this,"DATA DELETED",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"ERROR DELETING DATA",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void  showMessage (String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
