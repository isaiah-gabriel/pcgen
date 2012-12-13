/*
 * WeightToken.java
 * Copyright 2003 (C) Devon Jones <soulcatcher@evilsoft.org>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.     See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Created on December 15, 2003, 12:21 PM
 *
 * Current Ver: $Revision$
 * Last Editor: $Author$
 * Last Edited: $Date$
 *
 */
package pcgen.io.exporttoken;

import pcgen.cdom.enumeration.BiographyField;
import pcgen.core.Globals;
import pcgen.core.SettingsHandler;
import pcgen.core.display.CharacterDisplay;
import pcgen.io.ExportHandler;

/**
 * <code>WeightToken</code>.
 * 
 * Formats:	WEIGHT
 * 			WEIGHT.NOUNIT
 * 			WEIGHT.x
 * 
 * @author	Devon Jones
 * @version	$Revision$
 */
public class WeightToken extends AbstractExportToken
{
	/**
	 * Gets the token name
	 * 
	 * @return The token name.
	 * @see pcgen.io.exporttoken.Token#getTokenName()
	 */
	@Override
	public String getTokenName()
	{
		return "WEIGHT";
	}

	/**
	 * Get the value of the token.
	 *
	 * @param tokenSource The full source of the token
	 * @param pc The character to retrieve the value for.
	 * @param eh The ExportHandler that is managing the export.
	 * @return The value of the token.
	 * @see pcgen.io.exporttoken.Token#getToken(java.lang.String, pcgen.core.PlayerCharacter, pcgen.io.ExportHandler)
	 */
	@Override
	public String getToken(String tokenSource, CharacterDisplay display,
		ExportHandler eh)
	{
		String retString = "";

		if (!display.getSuppressBioField(BiographyField.WEIGHT))
		{
			if ("WEIGHT".equals(tokenSource))
			{
				retString = display.getWeightToken();
			}
			else if ("WEIGHT.NOUNIT".equals(tokenSource))
			{
				retString = display.getNoUnitToken();
			}
			else
			{
				String type =
						tokenSource.substring(tokenSource.lastIndexOf('.') + 1);
				retString =
						Globals.getGameModeUnitSet().displayWeightInUnitSet(
							getLoadToken(type, display));
			}
		}
		
		return retString;
	}

	/**
	 * Get the value of the weight token in format WEIGHT.X
	 * @param type Encumbrance type 
	 * @param pc The character to retrieve the value for.
	 * @return The value of the weight token.
	 */
	public static double getLoadToken(String type, CharacterDisplay display)
	{
		Float mult = SettingsHandler.getGame().getLoadInfo().getLoadMultiplier(
				type.toUpperCase());
		if (mult != null)
		{
			return display.getMaxLoad(mult).intValue();
		}
		return 0.0;
	}
}
