/**
 * Copyright (C) 2010, Sandeep Gupta
 * http://www.sangupta.com
 * 
 * The file is licensed under the the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.keepwalking;

/**
 * A simple implementation to find the first occurrence of a string in another
 * given string. 
 * 
 * @author Sandeep Gupta <a href="mailto:sangupta@gmail.com">[email]</a>
 * @version 1.0
 * @since 24 Aug 2010
 */
public class FirstOccurrence {

	/**
	 * Command line function for testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(findStringInString("This is a simple line containing the is word.", "is"));
	}

	/**
	 * Method that looks to search for a given string in another string.
	 * 
	 * @param stringToSearchIn The source string
	 * @param stringToSearchFor String that needs to be searched for.
	 * @return the index of the first occurence of this string.
	 */
	public static int findStringInString(String stringToSearchIn, String stringToSearchFor) {
		int lengthIn = stringToSearchIn.length();
		int lengthFor = stringToSearchFor.length();

		if (lengthFor > lengthIn) {
			// Sub-string candidate is larger than original string
			return -1;
		}

		if (lengthFor == lengthIn) {
			for (int index = 0; index < lengthIn; index++) {
				if (stringToSearchIn.charAt(index) != stringToSearchFor.charAt(index)) {
					// no match found
					return -1;
				}
			}
			return 0;
		}

		// lengthFor < lengthIn
		for (int index = 0; index < (lengthIn - lengthFor); index++) {
			if (stringToSearchIn.charAt(index) == stringToSearchFor.charAt(0)) {
				boolean found = true;
				// first char match found
				// check if the string beyond this is equal
				for (int subIndex = 0; subIndex < lengthFor; subIndex++) {
					if (stringToSearchIn.charAt(index + subIndex) != stringToSearchFor.charAt(subIndex)) {
						// no match
						found = false;
						break;
					}
				}
				if (found) {
					// match found
					return index;
				}
			}
		}

		return -1;
	}
}
