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
 * Function to convert a given number on base unknown to a base 10 number. To convert
 * a basic assumption is made that the base of the given number, is the maximum value
 * of the occurence of any digit/letter in the number.
 * 
 * @author Sandeep Gupta <a href="mailto:sangupta@gmail.com">[email]</a>
 * @version 1.0
 * @since 23 Aug 2010
 */
public class NumberToBase10 {
	
	/**
	 * Command line test.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(convertUnknownBaseNumberToBase10("172"));
	}

	public static Double convertUnknownBaseNumberToBase10(String number) {
		// null check
		if (number == null || number.length() == 0) {
			return null;
		}

		// turn to upper case - so that our logic below is easy
		number = number.toUpperCase();

		// scan through the string to find out the maximum number or the
		// character
		int maxAscii = 0;
		for (int i = 0; i < number.length(); i++) {
			int ascii = number.charAt(i);
			if (!(((ascii >= '0') && (ascii <= '9')) || ((ascii >= 'A') && (ascii <= 'Z')))) {
				throw new IllegalArgumentException("Illegal number, can have only digits (0-9) and letters (A-Z)");
			}

			maxAscii = Math.max(ascii, maxAscii);
		}

		// check if the number has letters or not
		double finalNumber = 0;
		int length = number.length();
		if (maxAscii >= 'A') {
			int maxNumber = maxAscii - 'A' + 10 + 1;
			for (int i = 0; i < length; i++) {
				int charCode = number.charAt(i);
				if (charCode >= 'A') {
					charCode = charCode - 'A' + 10;
				}
				int num = charCode;
				finalNumber = finalNumber + (num * Math.pow(maxNumber, (length - i - 1)));
			}
		} else {
			int maxNumber = maxAscii - '0' + 1;
			// just iterate over a normal loop
			for (int i = 0; i < length; i++) {
				int num = number.charAt(i) - '0';
				finalNumber = finalNumber + (num * Math.pow(maxNumber, (length - i - 1)));
			}
		}

		return finalNumber;
	}
}
