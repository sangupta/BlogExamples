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

import java.io.UnsupportedEncodingException;

/**
 * Java implementation for the encodeUriComponent() and decodeUriComponent() functions
 * commonly used when working with Web URIs.
 * 
 * @author Sandeep Gupta <a href="mailto:sangupta@gmail.com">[email]</a>
 * @version 1.0
 * @since 15 May 2010
 */
public class UriComponentUtils {

	/**
	 * Characters that are allowed in a URI.
	 */
	public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";
	
	/**
	 * Command line test.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String test = "some text to search for";
		String encoded = encodeURIComponent(test);
		System.out.println("Encoded component: " + encoded);
		String decoded = decodeURIComponent(encoded);
		if(test.equals(decoded)) {
			System.out.println("Successful converison.");
			return;
		}
		
		System.out.println("Failure in conversion.");
	}

	/**
	 * Function to convert a given string into URI encoded format.
	 * 
	 * @param input the source string
	 * @return the encoded string
	 */
	public static String encodeURIComponent(String input) {
		if (input == null || input.trim().length() == 0) {
			return input;
		}

		int l = input.length();
		StringBuilder o = new StringBuilder(l * 3);
		try {
			for (int i = 0; i < l; i++) {
				String e = input.substring(i, i + 1);
				if (ALLOWED_CHARS.indexOf(e) == -1) {
					byte[] b = e.getBytes("utf-8");
					o.append(getHex(b));
					continue;
				}
				o.append(e);
			}
			return o.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * Function to give a HEX representation of the byte array.
	 * 
	 * @param bytes the source byte array
	 * @return the HEX coded string representing the byte array
	 */
	private static String getHex(byte bytes[]) {
		StringBuilder o = new StringBuilder(bytes.length * 3);
		for (int i = 0; i < bytes.length; i++) {
			int n = (int) bytes[i] & 0xff;
			o.append("%");
			if (n < 0x10) {
				o.append("0");
			}
			o.append(Long.toString(n, 16).toUpperCase());
		}
		return o.toString();
	}

	/**
	 * Function to decode a given string from URI encoded format.
	 * 
	 * @param encodedURI the encoded string component
	 * @return the decoded string
	 */
	public static String decodeURIComponent(String encodedURI) {
		char actualChar;

		StringBuffer buffer = new StringBuffer();

		int bytePattern, sumb = 0;

		for (int i = 0, more = -1; i < encodedURI.length(); i++) {
			actualChar = encodedURI.charAt(i);

			switch (actualChar) {
				case '%': {
					actualChar = encodedURI.charAt(++i);
					int hb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
					actualChar = encodedURI.charAt(++i);
					int lb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
					bytePattern = (hb << 4) | lb;
					break;
				}

				case '+': {
					bytePattern = ' ';
					break;
				}

				default: {
					bytePattern = actualChar;
				}
			}

			if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
				sumb = (sumb << 6) | (bytePattern & 0x3f);
				if (--more == 0)
					buffer.append((char) sumb);
			} else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
				buffer.append((char) bytePattern);
			} else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
				sumb = bytePattern & 0x1f;
				more = 1;
			} else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
				sumb = bytePattern & 0x0f;
				more = 2;
			} else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
				sumb = bytePattern & 0x07;
				more = 3;
			} else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
				sumb = bytePattern & 0x03;
				more = 4;
			} else { // 1111110x
				sumb = bytePattern & 0x01;
				more = 5;
			}
		}
		return buffer.toString();
	}
}
