package com.example.wellness360;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Documents extends AppCompatActivity {

    private static final int PICK_DOCUMENT_REQUEST_CODE = 1;
    private List<Uri> documentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        FloatingActionButton addDocumentButton = findViewById(R.id.AddButton);
        addDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDocumentPicker();
            }
        });
        LinearLayout documentLayout = findViewById(R.id.documentLayout);
        for(Uri documentUri : documentList) {
            TextView textView = new TextView(this);
            textView.setText(getDocumentNameFromUri(documentUri));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDocumentOptionsDialog(documentUri);
                }
            });
            documentLayout.addView(textView);
        }
    }

    private void openDocumentPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_DOCUMENT_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_DOCUMENT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedDocument = data.getData();
            documentList.add(selectedDocument);
            updateDocumentUI(selectedDocument);
        }
    }
    private void updateDocumentUI(Uri documentUri) {
        LinearLayout documentLayout = findViewById(R.id.documentLayout);
        TextView textView = new TextView(this);
        String documentName = getTrimmedDocumentNameFromUri(documentUri);
        textView.setTextSize(20);
        textView.setText(getDocumentNameFromUri(documentUri));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDocumentOptionsDialog(documentUri);
            }
        });
        documentLayout.addView(textView);
    }
    private String getTrimmedDocumentNameFromUri(Uri uri) {
        String documentName = "";
        if (uri != null) {
            String uriString = uri.toString();
            int index = uriString.lastIndexOf('/');
            if (index >= 0 && index < uriString.length() - 1) {
                documentName = uriString.substring(index + 1);
            }
        }
        return documentName;
    }

    private String getDocumentNameFromUri(Uri uri) {
        String displayName = "";
        Cursor cursor = null;
        try {
            String[] projection = {OpenableColumns.DISPLAY_NAME};
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (displayNameIndex != -1) {
                    displayName = cursor.getString(displayNameIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return displayName;
    }
    private void showDocumentOptionsDialog(Uri documentUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Document Options");
        final CharSequence[] options = {"Open Document","Change Name", "Delete Document"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openSelectedDocument(documentUri);
                        break;
                    case 1:
                        changeDocumentName(documentUri);
                        break;
                    case 2:
                        deleteSelectedDocument(documentUri);
                        break;
                }
            }
        });

        builder.show();
    }

    private void changeDocumentName(final Uri documentUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Document Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = input.getText().toString();
                if (!newName.isEmpty()) {
                    updateDocumentName(documentUri, newName);
                } else {
                    Toast.makeText(Documents.this, "Please enter a new name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    private void updateDocumentName(Uri documentUri, String newName) {
        String uriString = documentUri.toString();
        for (int i = 0; i < documentList.size(); i++) {
            if (documentList.get(i).toString().equals(uriString)) {
                updateTextViewInUI(i, newName, documentUri.toString());
                break;
            }
        }
    }

    private void updateTextViewInUI(int index, String documentName, String documentUri) {
        LinearLayout documentLayout = findViewById(R.id.documentLayout);
        if (index >= 0 && index < documentLayout.getChildCount()) {
            LinearLayout containerLayout = new LinearLayout(this);
            containerLayout.setOrientation(LinearLayout.VERTICAL);

            TextView nameTextView = new TextView(this);
            nameTextView.setText(documentName);
            nameTextView.setTextSize(22);

            TextView uriTextView = new TextView(this);
            uriTextView.setText(formatUriForDisplay(documentUri));
            uriTextView.setTextSize(16);

            containerLayout.addView(nameTextView);
            containerLayout.addView(uriTextView);

            documentLayout.removeViewAt(index);
            documentLayout.addView(containerLayout, index);
        }
    }
    private String formatUriForDisplay(String documentUri) {
        String[] parts = documentUri.split("/");
        return parts[parts.length - 1];
    }


    private void openSelectedDocument(Uri documentUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(documentUri, "application/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    private void deleteSelectedDocument(Uri documentUri) {
        Iterator<Uri> iterator = documentList.iterator();
        while (iterator.hasNext()) {
            Uri uri = iterator.next();
            if (uri.equals(documentUri)) {
                iterator.remove();
                updateDocumentLayout();
                break;
            }
        }
    }

    private void updateDocumentLayout() {
        LinearLayout documentLayout = findViewById(R.id.documentLayout);
        documentLayout.removeAllViews();
        for (Uri documentUri : documentList) {
            TextView textView = new TextView(this);
            textView.setText(getDocumentNameFromUri(documentUri));
            textView.setTextSize(20);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDocumentOptionsDialog(documentUri);
                }
            });
            documentLayout.addView(textView);
        }
    }

}