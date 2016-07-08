/* Copyright (C) 2005-2011 Fabio Riccardi */

package com.lightcrafts.ui.export;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.prefs.Preferences;

import static com.lightcrafts.ui.export.Locale.LOCALE;

/**
 * A label with a collapse/expand icon, to show and hide export controls.
 * Its constructor takes an optional Window.  If this Window is not null,
 * then the ExportCtrlToggle will repack the Window when it collapses and
 * expands.
 */
class ExportCtrlToggle extends Box {

    private static Icon CollapsedIcon;
    private static Icon ExpandedIcon;
    private static Icon CollapsedHighlightIcon;
    private static Icon ExpandedHighlightIcon;

    static {
        URL url;
        Image image;
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        url = ExportCtrlToggle.class.getResource(
            "resources/RightArrow.png"
        );
        image = toolkit.createImage(url);
        CollapsedIcon = new ImageIcon(image);

        url = ExportCtrlToggle.class.getResource(
            "resources/DownArrow.png"
        );
        image = toolkit.createImage(url);
        ExpandedIcon = new ImageIcon(image);

        url = ExportCtrlToggle.class.getResource(
            "resources/RightArrowHighlight.png"
        );
        image = toolkit.createImage(url);
        CollapsedHighlightIcon = new ImageIcon(image);

        url = ExportCtrlToggle.class.getResource(
            "resources/DownArrowHighlight.png"
        );
        image = toolkit.createImage(url);
        ExpandedHighlightIcon = new ImageIcon(image);
    }

    // Remember our expanded/collapsed state globally:
    private final static Preferences Prefs =
        Preferences.userNodeForPackage(ExportDialog.class);
    private final static String AdvancedOptionsKey = "AdvancedOptions";
    private final static String WatermarkOptionsKey = "WatermarkOptions";

    private ExportControls ctrls;
    private WatermarkControls wmCtrls;
    private Window window;          // Pack the Window when toggling
    private JLabel label_AV;
    private JLabel label_WM;
    private int isExpanded = 0;         // Use an integer to carry isExpanded options for both advanced options(1st bit) and watermark options(2nd bit)
    final int OPTIONS_AV = 1;
    final int OPTIONS_WM = 2;

    ExportCtrlToggle(ExportControls ctrls, WatermarkControls wmCtrls, Window window) {
        super(BoxLayout.Y_AXIS);

        this.ctrls = ctrls;
        this.wmCtrls = wmCtrls;
        this.window = window;

        if(Prefs.getBoolean(AdvancedOptionsKey, true)) {
            isExpanded = isExpanded | OPTIONS_AV;
        }
        if(Prefs.getBoolean(WatermarkOptionsKey, true)) {
            isExpanded = isExpanded | OPTIONS_WM;
        }

        addOptions();
        addOptions2();
/*
        label = new JLabel(LOCALE.get("AdvancedOptionsLabel"));
        label.setAlignmentX(1f);
        Box labelBox = Box.createHorizontalBox();
        labelBox.add(label);
        labelBox.add(Box.createHorizontalGlue());
        labelBox.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        add(labelBox);

        isExpanded = Prefs.getBoolean(AdvancedOptionsKey, true);

        // Manually initialize the expanded/collapsed state, and don't call
        // doExpand() or doCollapse() because these pack the window.
        if (isExpanded) {
            label.setIcon(ExpandedIcon);
            add(ctrls);
        }
        else {
            label.setIcon(CollapsedIcon);
        }
        label.addMouseListener(
            new MouseAdapter() {
                public void mousePressed(MouseEvent event) {
                    if (isExpanded) {
                        doCollapse();
                    }
                    else {
                        doExpand();
                    }
                }
                public void mouseEntered(MouseEvent event) {
                    if (isExpanded) {
                        label.setIcon(ExpandedHighlightIcon);
                    }
                    else {
                        label.setIcon(CollapsedHighlightIcon);
                    }
                }
                public void mouseExited(MouseEvent event) {
                    if (isExpanded) {
                        label.setIcon(ExpandedIcon);
                    }
                    else {
                        label.setIcon(CollapsedIcon);
                    }
                }
            }
        );
*/
    }

    void addOptions() {
        label_AV = new JLabel(LOCALE.get("AdvancedOptionsLabel"));
        label_AV.setAlignmentX(1f);
        Box labelBox = Box.createHorizontalBox();
        labelBox.add(label_AV);
        labelBox.add(Box.createHorizontalGlue());
        labelBox.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        add(labelBox);

        // Manually initialize the expanded/collapsed state, and don't call
        // doExpand() or doCollapse() because these pack the window.
        if ((isExpanded & OPTIONS_AV) != 0) {
            label_AV.setIcon(ExpandedIcon);
            add(ctrls);
        }
        else {
            label_AV.setIcon(CollapsedIcon);
        }
        label_AV.addMouseListener(
            new MouseAdapter() {
                public void mousePressed(MouseEvent event) {
                    if ((isExpanded & OPTIONS_AV) != 0) {
                        doCollapse(OPTIONS_AV);
                    }
                    else {
                        doExpand(OPTIONS_AV);
                    }
                }
                public void mouseEntered(MouseEvent event) {
                    if ((isExpanded & OPTIONS_AV) != 0) {
                        label_AV.setIcon(ExpandedHighlightIcon);
                    }
                    else {
                        label_AV.setIcon(CollapsedHighlightIcon);
                    }
                }
                public void mouseExited(MouseEvent event) {
                    if ((isExpanded & OPTIONS_AV) != 0) {
                        label_AV.setIcon(ExpandedIcon);
                    }
                    else {
                        label_AV.setIcon(CollapsedIcon);
                    }
                }
            }
        );
    }

    void addOptions2() {
        label_WM = new JLabel(LOCALE.get("WatermarkOptionsLabel"));
        label_WM.setAlignmentX(1f);
        Box labelBox = Box.createHorizontalBox();
        labelBox.add(label_WM);
        labelBox.add(Box.createHorizontalGlue());
        labelBox.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        add(labelBox);

        // Manually initialize the expanded/collapsed state, and don't call
        // doExpand() or doCollapse() because these pack the window.
        if ((isExpanded & OPTIONS_WM) != 0) {
            label_WM.setIcon(ExpandedIcon);
            add(wmCtrls);
        }
        else {
            label_WM.setIcon(CollapsedIcon);
        }
        label_WM.addMouseListener(
            new MouseAdapter() {
                public void mousePressed(MouseEvent event) {
                    if ((isExpanded & OPTIONS_WM) != 0) {
                        doCollapse(OPTIONS_WM);
                    }
                    else {
                        doExpand(OPTIONS_WM);
                    }
                }
                public void mouseEntered(MouseEvent event) {
                    if ((isExpanded & OPTIONS_WM) != 0) {
                        label_WM.setIcon(ExpandedHighlightIcon);
                    }
                    else {
                        label_WM.setIcon(CollapsedHighlightIcon);
                    }
                }
                public void mouseExited(MouseEvent event) {
                    if ((isExpanded & OPTIONS_WM) != 0) {
                        label_WM.setIcon(ExpandedIcon);
                    }
                    else {
                        label_WM.setIcon(CollapsedIcon);
                    }
                }
            }
        );
    }

    int isExpanded() {
        return isExpanded;
    }

    void doExpand(int Options) {
        if(Options==OPTIONS_AV) {
            add(ctrls);
            label_AV.setIcon(ExpandedIcon);
            isExpanded = isExpanded | OPTIONS_AV;
            Prefs.putBoolean(AdvancedOptionsKey, ((isExpanded & OPTIONS_AV)!=0)?true:false);
        }
        else {
            add(wmCtrls);
            label_WM.setIcon(ExpandedIcon);
            isExpanded = isExpanded | OPTIONS_WM;
            Prefs.putBoolean(WatermarkOptionsKey, ((isExpanded & OPTIONS_WM)!=0)?true:false);
        }

        if (window != null) {
            window.pack();
        }
        else {
            revalidate();
            getParent().repaint();
        }
    }

    void doCollapse(int Options) {
        if(Options==OPTIONS_AV) {
            remove(ctrls);
            label_AV.setIcon(CollapsedIcon);
            isExpanded = isExpanded & ~OPTIONS_AV;
            Prefs.putBoolean(AdvancedOptionsKey, ((isExpanded & OPTIONS_AV) !=0)?true:false);
        }
        else {
            remove(wmCtrls);
            label_WM.setIcon(CollapsedIcon);
            isExpanded = isExpanded & ~OPTIONS_WM;
            Prefs.putBoolean(WatermarkOptionsKey, ((isExpanded & OPTIONS_WM) !=0)?true:false);
        }

        if (window != null) {
            window.pack();
        }
        else {
            revalidate();
            getParent().repaint();
        }
    }
}
