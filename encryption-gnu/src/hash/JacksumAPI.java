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

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;











// TODO: Auto-generated Javadoc
/**
 * This is the Jacksum Application Program Interface (API).
 * Use this API to get an instance of an algorithm and to
 * determine both the available algorithms and available encodings
 * for the checksum.
 */
public class JacksumAPI {

    /** The Constant NAME. */
    public final static String NAME = "Jacksum";
    
    /** The Constant VERSION. */
    public final static String VERSION = "1.7.0";





    /**
     * determines the Version of this API.
     * @return a String representing the Version of this API
     */
    public final static String getVersionString() {
        return VERSION;
    }


    /**
     * determines the Name of this API.
     * @return a String representing the Name of this API
     */
    public final static String getName() {
        return NAME;
    }


    
    /**
     * Gets all available encodings of a checksum.
     *
     * @return a Map with key and value pairs, both are Strings
     * (the key can be used to feed the method setEncoding(),
     * the value of the pair is a description of the encoding)
     */
    public static Map getAvailableEncodings() {
        Map map = new TreeMap();
        map.put(""             ,"Default");
        map.put("bin"          ,"Binary");
        map.put("dec"          ,"Decimal");
        map.put("oct"          ,"Octal");
        map.put("hex"          ,"Hexadecimal (lowercase)");
        map.put("hexup"        ,"Hexadecimal (uppercase)");
        map.put("base64"       ,"Base 64");
 
        return map;
    }


    /**
     * Gets all available algorithms.
     *
     * @return a Map with key and value pairs, both are Strings
     * (the key can be used to feed the method getChecksumInstance(),
     * the value of the pair is the name of the algorithm
     * which can be used in a GUI for example)
     */
    public static Map getAvailableAlgorithms() {
        Map map = new TreeMap();
        map.put("md5"             ,"MD5");
        map.put("sha256"          ,"SHA-2 (SHA-256)");
        map.put("sha512"          ,"SHA-2 (SHA-512)");

        return map;
    }


    /**
     * Gets an object of a checksum algorithm.
     * It always tries to use implementations from the Java API
     *
     * @param algorithm code for the checksum algorithm
     * @return a checksum algorithm object
     * @throws NoSuchAlgorithmException if algorithm is unknown
     */
    public static AbstractChecksum getChecksumInstance(String algorithm)
    throws NoSuchAlgorithmException {
        return getChecksumInstance(algorithm,false);
    }


    /**
     * Gets an object of a checksum algorithm.
     *
     * @param algorithm code for the checksum algorithm
     * @param alternate a pure Java implementation is preferred
     * @return a checksum algorithm object
     * @throws NoSuchAlgorithmException if algorithm is unknown
     */
    public static AbstractChecksum getChecksumInstance(String algorithm, boolean alternate)
    throws NoSuchAlgorithmException {
        AbstractChecksum checksum = null;

        // a combined hash algorithm (must be the first if clause)
        if (algorithm.equals("md5") || algorithm.equals("md5sum")) {
            if (alternate) checksum = new MDgnu(hash.Registry.MD5_HASH); else
            checksum = new MD("MD5");
        } else if (algorithm.equals("sha512") || algorithm.equals("sha-512")) {
            if (alternate) checksum = new MDgnu(Registry.SHA512_HASH); else
            if (GeneralProgram.isSupportFor("1.4.2"))
                checksum = new MD("SHA-512"); else
                checksum = new MDgnu(Registry.SHA512_HASH);
            
        }else if (algorithm.equals("sha256") || algorithm.equals("sha-256")) {
                if (alternate) checksum = new MDgnu(Registry.SHA256_HASH); else
                if (GeneralProgram.isSupportFor("1.4.2"))
                    checksum = new MD("SHA-256"); else
                    checksum = new MDgnu(Registry.SHA256_HASH);
                
        } else { // unknown
            throw new NoSuchAlgorithmException(algorithm+" is an unknown algorithm.");
        }
        checksum.setName(algorithm);
        return checksum;
    }

}
