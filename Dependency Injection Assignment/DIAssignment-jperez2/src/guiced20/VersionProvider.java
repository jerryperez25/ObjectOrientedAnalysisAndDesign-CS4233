/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package guiced20;

/**
 * A class that can be used for identifyin which version of the game
 * will be used.
 * You may NOT modify this class.
 * @version Mar 24, 2020
 */
public class VersionProvider
{
    private static String versionName = "none";

    /**
     * @return the propertyFileName
     */
    public static String getVersionName()
    {
        return versionName;
    }

    /**
     * @param propertyFileName the propertyFileName to set
     */
    public static void setVersionName(String propertyFileName)
    {
        VersionProvider.versionName = propertyFileName;
    }
}
