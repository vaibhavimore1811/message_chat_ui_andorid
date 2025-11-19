package com.vaibhavi.message.data;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vaibhavi.message.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "chat.db";
    private static final int DB_VER = 1;
    private static final String TABLE_MSG = "messages";

    public ChatDbHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MSG + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "text TEXT, "
                + "type TEXT, "
                + "timestamp INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MSG);
        onCreate(db);
    }

    public long insertMessage(Message m) {
        ContentValues cv = new ContentValues();
        cv.put("text", m.getText());
        cv.put("type", m.getType());
        cv.put("timestamp", m.getTimestamp());
        return getWritableDatabase().insert(TABLE_MSG, null, cv);
    }

    public List<Message> getAllMessages() {
        List<Message> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_MSG + " ORDER BY timestamp ASC", null);
        while (c.moveToNext()) {
            Message m = new Message();
            m.setId(c.getLong(c.getColumnIndexOrThrow("id")));
            m.setText(c.getString(c.getColumnIndexOrThrow("text")));
            m.setType(c.getString(c.getColumnIndexOrThrow("type")));
            m.setTimestamp(c.getLong(c.getColumnIndexOrThrow("timestamp")));
            list.add(m);
        }
        c.close();
        return list;
    }
}
