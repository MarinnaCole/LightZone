/* Copyright (C) 2016 Marinna Cole */

package com.lightcrafts.image.export;

/**
 * A <code>TransparencyOption</code> is-an {@link IntegerExportOption} for storing
 * an integer value representing watermark overlay transparency.
 *
 * @author Marinna Cole [Marinna.A.Cole@gmail.com]
 */
public final class TransparencyOption extends IntegerExportOption {

    public static final String NAME = TransparencyOption.class.getName();

    /**
     * Construct a <code>TransparencyOption</code>.
     *
     * @param defaultValue The default value.
     * @param options The {@link ImageExportOptions} of which this option is a
     * member.
     */
    public TransparencyOption( int defaultValue, ImageExportOptions options ) {
        super( NAME, defaultValue, options );
    }

    /**
     * {@inheritDoc}
     */
    public boolean isLegalValue( int value ) {
        return ((value > 0) || (value < 100));
    }

}
/* vim:set et sw=4 ts=4: */
