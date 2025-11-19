package com.vaibhavi.message.activities;



import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.vaibhavi.message.R;
import com.vaibhavi.message.adapters.MessageAdapter;
import com.vaibhavi.message.data.ChatDbHelper;
import com.vaibhavi.message.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final int REQ_AUDIO_PERM = 101;
    private static final int REQ_CAMERA = 102;

    private EditText edtMessage;
    private ImageView btnAttach, btnMic, btnSend, btnCamera,imgAudio,imgVideo;
    private RecyclerView recycler;
    private MessageAdapter adapter;
    private List<Message> messages = new ArrayList<>();
    private ChatDbHelper db;
    private MediaRecorder recorder;
    private String audioPath;

    String[] quickReplies = {
            "Okay 👍", "I will call you later.", "Send me the details.", "Where are you?", "Thank you 😊"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        edtMessage = findViewById(R.id.edtMessage);
        btnAttach = findViewById(R.id.btnAttach);
        btnMic = findViewById(R.id.btnMic);
        btnSend = findViewById(R.id.btnSend);
        btnCamera = findViewById(R.id.btnCamera);
        recycler = findViewById(R.id.chatRecycler);
        imgVideo = findViewById(R.id.imgVideo);
        imgAudio = findViewById(R.id.imgAudio);

        db = new ChatDbHelper(this);
        messages = db.getAllMessages();
        adapter = new MessageAdapter(this, messages);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // quick replies on long press
        edtMessage.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(quickReplies, (dialog, which) -> edtMessage.setText(quickReplies[which]));
            builder.show();
            return true;
        });
        hideSystemBars();

        btnSend.setOnClickListener(v -> sendTextMessage());
        btnAttach.setOnClickListener(v -> openAttachmentOptions());
        btnCamera.setOnClickListener(v -> openCamera());

        btnMic.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!checkAudioPermission()) {
                        requestAudioPermission();
                        return true;
                    }
                    startRecording();
                    return true;
                case MotionEvent.ACTION_UP:
                    stopRecording();
                    return true;
            }
            return false;
        });
        imgAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, CallActivity.class);
                startActivity(intent);
            }
        });
        imgVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, VideoCallActivity.class);
                startActivity(intent);
            }
        });
    }
    private void hideSystemBars() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    private void sendTextMessage() {
        String txt = edtMessage.getText().toString().trim();
        if (txt.isEmpty()) return;
        Message m = new Message(txt, "sent", System.currentTimeMillis());
        long id = db.insertMessage(m);
        m.setId(id);
        messages.add(m);
        adapter.notifyItemInserted(messages.size()-1);
        recycler.scrollToPosition(messages.size()-1);
        edtMessage.setText("");
        Message m1 = new Message(txt, "received", System.currentTimeMillis());
        long id1 = db.insertMessage(m1);
        m1.setId(id1);
        messages.add(m1);
        adapter.notifyItemInserted(messages.size()-1);
        recycler.scrollToPosition(messages.size()-1);
        edtMessage.setText("");
    }

    private void openAttachmentOptions() {
        // simple options dialog - image or camera or file
        String[] opts = {"Image from camera"};
        new AlertDialog.Builder(this)
                .setItems(opts, (d, which) -> {
                    if (which == 0) openCamera();
                }).show();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQ_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CAMERA && resultCode == RESULT_OK && data != null) {
            // small thumbnail
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            // TODO: save bitmap to file and insert message with type "image"
            Message m = new Message("[Image]", "sent", System.currentTimeMillis());
            long id = db.insertMessage(m);
            m.setId(id);
            messages.add(m);
            adapter.notifyItemInserted(messages.size()-1);
            recycler.scrollToPosition(messages.size()-1);
        }
    }

    // Audio record helpers
    private boolean checkAudioPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestAudioPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQ_AUDIO_PERM);
    }

    private void startRecording() {
        audioPath = getExternalFilesDir(null) + "/audio_" + System.currentTimeMillis() + ".3gp";
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audioPath);
        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            try {
                recorder.stop();
            } catch (RuntimeException ex) {
                // ignore if stop called prematurely
            }
            recorder.release();
            recorder = null;

            Message m = new Message("[Audio]", "sent", System.currentTimeMillis());
            long id = db.insertMessage(m);
            m.setId(id);
            messages.add(m);
            adapter.notifyItemInserted(messages.size()-1);
            recycler.scrollToPosition(messages.size()-1);
            // TODO: attach audioPath to message object and send
        }
    }

    // handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] perms, @NonNull int[] results) {
        super.onRequestPermissionsResult(requestCode, perms, results);
        if (requestCode == REQ_AUDIO_PERM) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
            }
        }
    }
}
