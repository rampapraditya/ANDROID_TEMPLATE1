package com.sttal;

import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.QuoteSpan;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.text.HtmlCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sttal.modul.ImageGetter;
import com.sttal.modul.QuoteSpanClass;

public class VisiMisiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_misi);

    }

    private void displayHtml(String html, TextView htmlViewer) {
        ImageGetter imageGetter = new ImageGetter(getResources(), htmlViewer);
        Spanned styledText = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY, imageGetter, null);
        replaceQuoteSpans((Spannable) styledText);
        htmlViewer.setMovementMethod(LinkMovementMethod.getInstance());
        htmlViewer.setText(styledText);
    }

    private void replaceQuoteSpans(Spannable spannable) {
        QuoteSpan[] quoteSpans = spannable.getSpans(0, spannable.length() - 1, QuoteSpan.class);

        for (QuoteSpan quoteSpan : quoteSpans) {
            int start = spannable.getSpanStart(quoteSpan);
            int end = spannable.getSpanEnd(quoteSpan);
            int flags = spannable.getSpanFlags(quoteSpan);
            spannable.removeSpan(quoteSpan);
            spannable.setSpan(
                    new QuoteSpanClass(
                            // background color
                            ContextCompat.getColor(this, R.color.colorPrimary),
                            // strip color
                            ContextCompat.getColor(this, R.color.colorAccent),
                            // strip width
                            10F, 50F
                    ),
                    start, end, flags
            );
        }
    }
}