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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// TODO: Auto-generated Javadoc
/**
 * A wrapper class that can be used to compute MD5, SHA-1, SHA-256, SHA-384 and SHA-512
 * (provided by your JVM vendor).
 */
public class MD extends AbstractChecksum {

    /** The md. */
    private MessageDigest md = null;
    
    /** The virgin. */
    private boolean virgin=true;
    
    /** The digest. */
    private byte[] digest = null;

    /**
     * Instantiates a new md.
     *
     * @param arg the arg
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public MD(String arg) throws NoSuchAlgorithmException {
        // value=0; we don't use value, we use md
        length=0;
        filename=null;
        separator=" ";
        encoding=HEX;
        virgin=true;
        md = MessageDigest.getInstance(arg);
    }

    /* (non-Javadoc)
     * @see hash.AbstractChecksum#reset()
     */
    public void reset() {
        md.reset();
        length=0;
        virgin=true;
    }

    /* (non-Javadoc)
     * @see hash.AbstractChecksum#update(byte[], int, int)
     */
    public void update(byte[] buffer, int offset, int len) {
        md.update(buffer,offset,len);
        length+=len;
    }

    /* (non-Javadoc)
     * @see hash.AbstractChecksum#update(byte)
     */
    public void update(byte b) {
        md.update(b);
        length++;
    }

    /* (non-Javadoc)
     * @see hash.AbstractChecksum#update(int)
     */
    public void update(int b) {
        update((byte)(b & 0xFF));
    }

    /* (non-Javadoc)
     * @see hash.AbstractChecksum#toString()
     */
    public String toString() {
        return getFormattedValue()+separator+
        (isTimestampWanted()? getTimestampFormatted()+separator:"")+
        getFilename();
    }

    /* (non-Javadoc)
     * @see hash.AbstractChecksum#getByteArray()
     */
    public byte[] getByteArray() {
        if (virgin) {
            digest=md.digest();
            virgin=false;
        }
        // we don't expose internal representations
        byte[] save = new byte[digest.length];
        System.arraycopy(digest,0,save,0,digest.length);
        return save;
    }


}
