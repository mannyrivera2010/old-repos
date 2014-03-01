/******************************************************************************
 *
 * Jacksum version 1.7.0 - checksum utility in Java
 * Copyright (C) 2001-2006 Dipl.-Inf. (FH) Johann Nepomuk Loefflmann,
 * All Rights Reserved, http://www.jonelo.de
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * E-mail: jonelo@jonelo.de
 *
 *****************************************************************************/

package hash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.Checksum;



// TODO: Auto-generated Javadoc
/**
 * An abstract class that is actually the parent of all algorithms.
 */
abstract public class AbstractChecksum implements Checksum {

    /** The Constant BIN. */
    public final static String BIN = "bin";
    
    /** The Constant DEC. */
    public final static String DEC = "dec";
    
    /** The Constant OCT. */
    public final static String OCT = "oct";
    
    /** The Constant HEX. */
    public final static String HEX = "hex";
    
    /** The Constant HEX_UPPERCASE. */
    public final static String HEX_UPPERCASE = "hexup";
    
    /** The Constant BASE64. */
    public final static String BASE64 = "base64";
    
    /** The Constant BUBBLEBABBLE. */
    public final static String BUBBLEBABBLE = "bubblebabble";
    
    /** The Constant BUFFERSIZE. */
    public final static int BUFFERSIZE = 8192;

    /** The value. */
    protected long value;
    
    /** The length. */
    protected long length;
    
    /** The separator. */
    protected String separator;
    
    /** The filename. */
    protected String filename;
    
    /** The encoding. */
    protected String encoding;
    
    /** The group. */
    protected int group; // grouping of hex digits
    
    /** The group char. */
    protected char groupChar; // group char, blank by default

    /** The timestamp format. */
    protected String timestampFormat;
    
    /** The timestamp formatter. */
    protected Format timestampFormatter;
    
    /** The timestamp. */
    protected long timestamp;
    
    /** The name. */
    protected String name;


    /**
     * Creates an AbstractChecksum.
     */
    public AbstractChecksum() {
        value = 0;
        length = 0;
        separator = "\t";
        filename = null;
        encoding = "";
        timestampFormat = null;
        timestampFormatter = null;
        timestamp = 0;
        group = 0;
        groupChar = ' ';
        name = null;
    }

    /**
     * Set the name of the algorithm.
     *
     * @param name the name of the algorithm
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Resets the checksum to its initial value for further use.
     */
    // from the Checksum interface
    public void reset() {
        value = 0;
        length = 0;
    }

    /**
     * Updates the checksum with the specified byte.
     *
     * @param b the b
     */
    // from the Checksum interface
    public void update(int b) {
        length++;
    }

    /**
     * Updates the checksum with the specified byte.
     *
     * @param b the b
     */
    public void update(byte b) {
        update((int)(b & 0xFF));
    }

    /**
     * Updates the current checksum with the specified array of bytes.
     * @param bytes the byte array to update the checksum with
     * @param offset the start offset of the data
     * @param length the number of bytes to use for the update
     */
    // from the Checksum interface
    public void update(byte[] bytes, int offset, int length) {
        for (int i = offset; i < length + offset; i++) {
            update(bytes[i]);
        }
    }

    /**
     * Updates the current checksum with the specified array of bytes.
     *
     * @param bytes the bytes
     */
    public void update(byte[] bytes) {
        update(bytes, 0, bytes.length);
    }

    /**
     * Returns the value of the checksum.
     *
     * @return the value
     * @see #getByteArray()
     */
    public long getValue() {
        return value;
    }

    /**
     * Returns the length of the processed bytes.
     *
     * @return the length
     */
    public long getLength() {
        return length;
    }

    /**
     * Sets the separator for the tokens.
     *
     * @param separator the new separator
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * Gets the separator.
     *
     * @return the separator
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Returns the result of the computation as byte array.
     *
     * @return the byte array
     * @since Jacksum 1.6
     */
    public byte[] getByteArray() {
        return new byte[]{(byte)(value&0xff)};
    }

    /**
     * The toString() method.
     *
     * @return the string
     */
    public String toString() {
        return getFormattedValue() + separator +
        length + separator +
        (isTimestampWanted() ? getTimestampFormatted() + separator : "") +
        filename;
    }

    /**
     * Returns true only if the specified checksum is equal to this object.
     *
     * @param anObject the an object
     * @return true, if successful
     */
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof AbstractChecksum) {
            AbstractChecksum abstractChecksum = (AbstractChecksum)anObject;
            return Arrays.equals(getByteArray(), abstractChecksum.getByteArray());
        }
        return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        // let's do a very primitive hash rather than just a sum
        // let's also avoid circular dependencies among classes
        // let's also avoid casts, let's use shifts for performance
        // and prims for better security
        byte b[] = getByteArray();
        int s=0;
        for (int i = 0; i < b.length; i++) {
            s = ((s << 8) + b[i]) % 0x7FFFF1; // is prim
        }
        return s;
    }

    /**
     * Returns the checksum formatted.
     *
     * @return the formatted value
     * @since Jacksum 1.6
     */
    public String getFormattedValue() {
        if (encoding.equalsIgnoreCase(HEX)) {
            //return getHexValue();
            return Service.format(getByteArray(), false, group, groupChar);
        } else
        if (encoding.equalsIgnoreCase(HEX_UPPERCASE)) {
            return Service.format(getByteArray(), true, group, groupChar);
        } else
        if (encoding.equalsIgnoreCase(BASE64)) {
            return Base64.encodeBytes(getByteArray(),Base64.DONT_BREAK_LINES);
        } else
        if (encoding.equalsIgnoreCase(DEC)) {
            BigInteger big = new BigInteger(1, getByteArray());
            return big.toString();
        } else
        if (encoding.equalsIgnoreCase(BIN)) {
            return Service.formatAsBits(getByteArray());
        } else
        if (encoding.equalsIgnoreCase(OCT)) {
            BigInteger big = new BigInteger(1, getByteArray());
            return big.toString(8);
        } else
        // default
        return Long.toString(getValue()); // String.valueOf(checksum.getValue())
    }

    // with this method, the format() method can be customized
    /**
     * First format.
     *
     * @param format the format
     */
    public void firstFormat(StringBuffer format) {
        // checksum
        GeneralString.replaceAllStrings(format, "#FINGERPRINT", "#CHECKSUM");
    }

    // will be launched by the CLI option called -F
    /**
     * Format.
     *
     * @param format the format
     * @return the string
     */
    public String format(String format) {

        StringBuffer temp = new StringBuffer(format);
        firstFormat(temp);
        GeneralString.replaceAllStrings(temp, "#CHECKSUM{i}", "#CHECKSUM");
        GeneralString.replaceAllStrings(temp, "#ALGONAME{i}", "#ALGONAME");

        GeneralString.replaceAllStrings(temp, "#ALGONAME", getName());
        // counter
        // temp = GeneralString.replaceAllStrings(temp, "#COUNTER", getCounter() );
        GeneralString.replaceAllStrings(temp, "#CHECKSUM", getFormattedValue() );
        // filesize
        GeneralString.replaceAllStrings(temp, "#FILESIZE", Long.toString(length));
        // filename
        if (temp.toString().indexOf("#FILENAME{") > -1) { // comatibility to 1.3
            File filetemp=new File(filename);
            GeneralString.replaceAllStrings(temp, "#FILENAME{NAME}", filetemp.getName());
            String parent = filetemp.getParent();
            if (parent==null) parent=""; else
            if (!parent.endsWith(File.separator) &&
                // for files on a different drive where the working dir has changed
                (!parent.endsWith(":") && System.getProperty("os.name").toLowerCase().startsWith("windows"))
            ) parent+=File.separator;
            GeneralString.replaceAllStrings(temp, "#FILENAME{PATH}", parent);
        }
        GeneralString.replaceAllStrings(temp, "#FILENAME", filename);
        // timestamp
        if (isTimestampWanted())
        GeneralString.replaceAllStrings(temp, "#TIMESTAMP", getTimestampFormatted());
        // sepcial chars
        GeneralString.replaceAllStrings(temp, "#SEPARATOR", separator);
        GeneralString.replaceAllStrings(temp, "#QUOTE", "\"");
        return temp.toString();
    }

    /**
     * Sets the filename.
     *
     * @param filename the filename.
     */
    public void setFilename(String filename) {
        this.filename=filename;
    }

    /**
     * Gets the filename.
     *
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the encoding of the checksum.
     *
     * @param encoding the encoding of the checksum.
     * @throws EncodingException the encoding exception
     * @since 1.6
     */
    public void setEncoding(String encoding) throws EncodingException {
        if (encoding == null) {
            this.encoding = ""; // default
        } else
        if (encoding.equalsIgnoreCase("bb")) { // alias for BubbleBabble
           this.encoding = BUBBLEBABBLE;
        } else
        if((encoding.length() == 0) || // empty string
           encoding.equalsIgnoreCase(HEX) ||
           encoding.equalsIgnoreCase(HEX_UPPERCASE) ||
           encoding.equalsIgnoreCase(DEC) ||
           encoding.equalsIgnoreCase(BIN) ||
           encoding.equalsIgnoreCase(OCT) ||

           encoding.equalsIgnoreCase(BASE64) ||
           encoding.equalsIgnoreCase(BUBBLEBABBLE)) {
           this.encoding = encoding;
        } else

        throw new EncodingException("Encoding is not supported");
    }

    /**
     * Gets the encoding of the checksum.
     *
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Returns true if groups are wanted (make sense only if encoding is HEX or HEXUP).
     *
     * @return true, if is group wanted
     */
    public boolean isGroupWanted() {
       return (group > 0);
    }

    /**
     * Sets the number of groups (make sense only if encoding is HEX or HEXUP).
     *
     * @param group the new group
     */
    public void setGroup(int group) {
        this.group = group;
    }

    /**
     * Gets the number of groups (make sense only if encoding is HEX or HEXUP).
     *
     * @return the group
     */
    public int getGroup() {
        return group;
    }

    /**
     * Sets the group char (make sense only if encoding is HEX or HEXUP).
     *
     * @param groupChar the new group char
     */
    public void setGroupChar(char groupChar) {
        this.groupChar = groupChar;
    }

    /**
     * Gets the group char  (works only if encoding is HEX or HEXUP).
     *
     * @return the group char
     */
    public char getGroupChar() {
        return groupChar;
    }

    /**
     * Sets the number of groups and the group char.
     * (make sense only if encoding is HEX or HEXUP).
     *
     * @param group the group
     * @param groupChar the group char
     */
    public void setGrouping(int group, char groupChar) {
        setGroup(group);
        setGroupChar(groupChar);
    }

    /**
     * Sets the format of the checksum as hex or default.
     *
     * @param hex the new hex
     * @deprecated As of Jacksum version 1.6
     * replaced by <code>setEncoding()</code>.
     */
    public void setHex(boolean hex) {
        encoding = hex ? HEX : "";
    }

    /**
     * Sets the format of the checksum as uppercase hex or lowercase hex.
     *
     * @param uppercase the new upper case
     * @deprecated As of Jacksum version 1.6
     * replaced by <code>setEncoding()</code>.
     */
    public void setUpperCase(boolean uppercase) {
        encoding = uppercase ? HEX_UPPERCASE : HEX;
    }

    /**
     * Gets the format of the checksum as hex.
     *
     * @return the hex value
     * @deprecated As of Jacksum version 1.6
     * replaced by <code>getByteArray()</code>.
     */
    public String getHexValue() {
        return Service.format(getByteArray(), encoding.equalsIgnoreCase(HEX_UPPERCASE), group, groupChar);
    }

    /**
     * Sets the timestamp.
     *
     * @param filename the file from which the timestamp should be gathered.
     */
    public void setTimestamp(String filename) {
        File file = new File(filename);
        this.timestamp = file.lastModified();
    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestampFormat to force a timestamp output.
     *
     * @param timestampFormat the format of the timestamp.
     */
    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
    }

    /**
     * Gets the format of the timestamp.
     *
     * @return the timestamp format
     */
    public String getTimestampFormat() {
        return timestampFormat;
    }

    /**
     * Gets the timestamp, formatted.
     *
     * @return the timestamp formatted
     */
    public String getTimestampFormatted() {
        if (timestampFormatter == null)
            timestampFormatter = new SimpleDateFormat(timestampFormat);
        return timestampFormatter.format(new Date(timestamp));
    }

    /**
     * Determines if a timestamp is wanted.
     *
     * @return true, if is timestamp wanted
     */
    public boolean isTimestampWanted() {
        return (timestampFormat != null);
    }

    /**
     * Reads a file and calculates the checksum from it.
     *
     * @param filename - the file which should be read
     * @return the long
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public long readFile(String filename) throws IOException {
        return readFile(filename, true);
    }


    /**
     * Reads a file and calculates the checksum from it.
     *
     * @param filename the filename which should be read
     * @param reset if reset is true, reset() will be launched before the checksum gets updated
     * @return the long
     * @throws IOException Signals that an I/O exception has occurred.
     * @see reset()
     */
    public long readFile(String filename, boolean reset) throws IOException {
        this.filename = filename;
        if (isTimestampWanted()) setTimestamp(filename);

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        long lengthBackup = 0;

        // http://java.sun.com/developer/TechTips/1998/tt0915.html#tip2
        try {
          fis = new FileInputStream(filename);
          bis = new BufferedInputStream(fis);
          if (reset) reset();
          lengthBackup = length;
          int len = 0;
          byte[] buffer = new byte[BUFFERSIZE];
          while ((len = bis.read(buffer)) > -1) {
              update(buffer, 0, len);
          }
        } finally {
          if (bis != null) bis.close();
          if (fis != null) fis.close();
        }
        return length - lengthBackup;
    }

}
