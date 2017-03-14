/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.core.util;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

/**
 * Borrowing concept from <a href="http://semver.org/">SemVer</a>, with following definition:
 * 
 * <ul>
 * <li><code>major</code> version when you make a incompatible change, where all related components
 * should be updated together.
 * <ul>
 * <li>E.g. add or remove required fields into existing messages.</li>
 * <li>E.g. add new message type.</li>
 * </ul>
 * </li>
 * 
 * <li><code>minor</code>version when you make a change, where all related components can be
 * upgraded in any order, but should be catching up before further changes can be made.
 * <ul>
 * <li>E.g. Change optional fields to required after both producer already updated to always produce
 * the field.</li>
 * <li>E.g. Change required fields to optional, with agreement that producer always produce field
 * before consumer updated to be aware that fields could be optional.</li>
 * </ul>
 * </ul>
 * </li>
 * <li><code>patch</code> version when you make a change, where related components can be upgraded
 * in any order. One component can skip more than one updates as far as major and minor versions are
 * same.
 * <ul>
 * <li>E.g. add or remove optional field.</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * <p>
 * Application will decide how to handle the version difference. E.g. difference on
 * <code>major</code> version will be logged as error, <code>minor</code> version differences will
 * be warning, while <code>patch</code> will be logged as information.
 * </p>
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
@Immutable
public final class Version {
    /**
     * Resolve version from <code>text</code>.
     * 
     * @param text
     *            version text.
     * @return version for text.
     */
    public static final Version ofString(String text) {
        Objects.requireNonNull(text);

        int firstPos = text.indexOf('.');
        int lastPos = text.lastIndexOf('.');

        if (firstPos == lastPos) {
            throw new IllegalArgumentException("Invalid version string: " + text);
        }

        short major = Short.parseShort(text.substring(0, firstPos));
        short minor = Short.parseShort(text.substring(firstPos + 1, lastPos));
        int patch = Integer.parseInt(text.substring(lastPos + 1));

        return new Version(major, minor, patch);
    }

    /**
     * Resolve version from<code>longValue</code>.
     * 
     * @param longValue
     *            long form of version
     * @return version for text.
     */
    public static final Version ofLong(long longValue) {
        if (longValue < 0) {
            throw new IllegalArgumentException();
        }

        int patch = (int) (0x00000000FFFFFFFF & longValue);
        short minor = (short) (0x0000FFFF & (int) (longValue >> 32));
        short major = (short) (0x0000FFFF & (int) (longValue >> 48));

        return new Version(major, minor, patch);
    }

    private final short major;
    private final short minor;
    private final int patch;

    /**
     * Initializes a new instance of <code>Version</code>.
     * 
     * @param major
     *            version when you make a incompatible change, where all related components should
     *            be updated together.
     * @param minor
     *            version when you make a change, where all related components can be upgraded in
     *            any order, but should be catching up before further changes can be made.
     * @param patch
     *            version when you make a change, where related components can be upgraded in any
     *            order. One component can skip more than one updates as far as major and minor
     *            versions are same.
     */
    public Version(short major, short minor, int patch) {
        if (major < 0) {
            throw new IllegalArgumentException("major should be greater or equals to 0.");
        }
        if (minor < 0) {
            throw new IllegalArgumentException("minor should be greater or equals to 0.");
        }
        if (patch < 0) {
            throw new IllegalArgumentException("patch should be greater or equals to 0.");
        }

        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * @return major version when you make a incompatible change, where all related components
     *         should be updated together.
     */
    public short getMajor() {
        return major;
    }

    /**
     * @return minor version when you make a change, where all related components can be upgraded in
     *         any order, but should be catching up before further changes can be made.
     */
    public short getMinor() {
        return minor;
    }

    /**
     * @return patch version when you make a change, where related components can be upgraded in any
     *         order. One component can skip more than one updates as far as major and minor
     *         versions are same.
     */
    public int getPatch() {
        return patch;
    }

    /**
     * Long format of version. Format as below:
     * 
     * <pre>
     * - - - - - - - - - - - - - - - - - - - - -
     * | 2 bytes | 2 bytes |      4 byte       |
     * + - - - - - - - - - - - - - - - - - - - +
     * |  major  |  minor  |       patch       |
     * - - - - - - - - - - - - - - - - - - - - -
     * </pre>
     * 
     * @return long value form of version.
     */
    public long toLong() {
        return major << 48 + minor << 32 + patch;
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + major;
        result = 31 * result + minor;
        result = 31 * result + patch;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Version)) {
            return false;
        }

        Version another = (Version) obj;

        return major == another.major && minor == another.minor && patch == another.patch;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + patch;
    }
}
