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
 * A simple Java implementation of the usual C/C++ atoi() function.
 * 
 * @author Sandeep Gupta <a href="http://www.sangupta.com">[email]</a>
 * @version 1.0
 * @since 02 Oct 2010
 */
public class AsciiToInteger {
	
	/**
	 * Command line test.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int x = atoi("-683");
		System.out.println("Conversion is: " + x);
	}

	/**
	 * Function to convert a string to Integer value. Supports the unary operators of +/-.
	 * 
	 * @param number
	 * @return
	 */
	private static int atoi(String number) {
		// check for NULL or empty
		if(number == null || number.trim().length() == 0) {
			throw new IllegalArgumentException("Number cannot be null/empty.");
		}

		// create a variable to store the result
		int result = 0;
		
		// trim the number
		number = number.trim();
		
		// check for sign as the first character
		boolean negate = false;
		char sign = number.charAt(0);
		
		if(sign == '+' || sign == '-') {
			if(sign == '-') {
				negate = true;
			}
			
			number = number.substring(1);
		}
		
		int length = number.length();
		for(int index = 0; index < length; index++) {
			char digit = number.charAt(index);
			
			// sanitize the digit
			if(!(digit >= '0' && digit <= '9')) {
				throw new IllegalArgumentException("Number contains characters other than digits at index " + index);
			}
			
			digit = (char) (digit - '0');
			
			result += (digit * Math.pow(10, (length - index - 1)));
		}
		
		// if negative, do it
		if(negate) {
			result = 0 - result;
		}
		
		// return the final result
		return result;
	}

}
