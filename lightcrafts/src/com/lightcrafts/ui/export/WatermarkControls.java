/* Copyright (C) 2016 Marinna Cole */

package com.lightcrafts.ui.export;

import com.lightcrafts.image.export.*;
import com.lightcrafts.image.types.ImageType;
import com.lightcrafts.image.types.JPEGImageType;
import com.lightcrafts.image.types.TIFFImageType;
import com.lightcrafts.platform.Platform;
import com.lightcrafts.utils.ColorProfileInfo;

import static com.lightcrafts.ui.export.Locale.LOCALE;
import com.lightcrafts.ui.toolkit.WidePopupComboBox;
import com.lightcrafts.model.RenderingIntent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

/**
 * This class is used to configure watermark-related export options
 */

public class WatermarkControls extends JPanel {

    private ImageFileExportOptions options;

    private int ctrlCount;

    /**
     * Initialize the WatermarkControls according to the given ImageExportOptions.
     * If textResize is true, let the user specify arbitrary export resize
     * bounds.  If textResize is false, show a multiple-choice control instead.
     */
    public WatermarkControls(ImageExportOptions options) {
        if (! (options instanceof ImageFileExportOptions)) {
            // Someone handed us an LZNImageType.ExportOptions.
            options = null;
        }
        this.options = (ImageFileExportOptions) options;
        if (options == null) {
            // This means: show no controls.
            return;
        }
        setLayout(new GridBagLayout());

        addTransparencyControl();
    }

    private void addTransparencyControl() {
        int transparency = 
            ((JPEGImageType.ExportOptions) options).transparency.getValue();

        final JLabel text = new JLabel("100");
        Dimension textSize = text.getPreferredSize();
        text.setHorizontalAlignment(SwingConstants.RIGHT);
        text.setPreferredSize(textSize);

        final JSlider slider = new JSlider(0, 100);
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setValue(transparency);
        text.setText(Integer.toString(transparency));

        slider.addChangeListener(
            new ChangeListener() {
                public void stateChanged(ChangeEvent event) {
                    int value = slider.getValue();
                    TransparencyOption option =
                        ((JPEGImageType.ExportOptions) options).transparency;
                    option.setValue(value);
                    text.setText(Integer.toString(value));
                }
            }
        );
        Box textSlider = Box.createHorizontalBox();
        textSlider.add(slider);
        textSlider.add(Box.createHorizontalStrut(6));
        textSlider.add(text);

        addLabelledControl(LOCALE.get("TransparencyLabel"), textSlider);
    }

    void addLabelledControl(String name, JComponent control) {
        addLabelledControl(name, control, true);
    }

    private void addLabelledControl(
        String name, JComponent control, boolean enabled
    ) {
        JLabel label = new JLabel(name + ":");
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        Box ctrlBox = Box.createHorizontalBox();
        ctrlBox.add(control);
        ctrlBox.add(Box.createHorizontalGlue());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = ctrlCount++;
        constraints.ipadx = 2;
        constraints.ipady = 2;
        constraints.insets = new Insets(2, 2, 2, 2);

        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        add(label, constraints);

        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        add(ctrlBox, constraints);

        if (! enabled) {
            label.setEnabled(false);
            control.setEnabled(false);
        }
    }
}
