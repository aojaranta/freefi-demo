package fi.free.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSNUtil {

	private static final Logger log = LoggerFactory.getLogger(SSNUtil.class);
	
	/**
	 * Validate if given SSN is authentic.
	 * @param ssn
	 * @return boolean True if SSN is valid
	 */
	public static boolean validateSSN(String ssn) {
		try {
			String dateOfBirth = getDateOfBirth(ssn);
			String century = getCentury(ssn);
			String individualNumber = getIndividualNumber(ssn);
			String controlCharacter = getControlCharacter(ssn);
			log.debug("{} {} {} {}", dateOfBirth, century, individualNumber, controlCharacter);
			Integer characterValue = calculateControlCharacterValue(dateOfBirth, individualNumber);
			String character = getControlCharacter(characterValue);
			log.info("calculated value {} -> matching character {} vs original character {}", characterValue, character, controlCharacter);
			if(character == null || !character.equals(controlCharacter))
				return false;
		} catch (IndexOutOfBoundsException | NumberFormatException | ArithmeticException e) {
			log.warn("SSN validation failed. Invalid data. {}", e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Get date of birth parsed from Social Security Number. e.g. [010101]-012T
	 * 
	 * @param SSN
	 * @return
	 */
	private static String getDateOfBirth(String SSN) throws IndexOutOfBoundsException {
		return SSN.substring(0, 6);
	}

	/**
	 * Get birth century parsed from Social Security Number. e.g. 010101[-]012T
	 * The sign after the date of birth tells us the century born. 
	 * 1800 -> '+'
	 * 1900 -> '-'
	 * 2000 -> 'A'
	 * 
	 * @param SSN
	 * @return
	 */
	private static String getCentury(String SSN) throws IndexOutOfBoundsException {
		return SSN.substring(6, 7);
	}

	/**
	 * Get individual number parsed from Social Security Number. e.g. 010101-[012]T
	 * Men have an odd number and women an even number.
	 * 
	 * @param SSN
	 * @return
	 */
	private static String getIndividualNumber(String SSN) throws IndexOutOfBoundsException {
		return SSN.substring(7, 10);
	}

	/**
	 * Get control character from Social Security Number. e.g. 010101-012[T]
	 * 
	 * @param SSN
	 * @return
	 */
	private static String getControlCharacter(String SSN) throws IndexOutOfBoundsException {
		return SSN.substring(10, 11);
	}
	
	/**
	 * Performs the calculation for SSN and returns the remaining value
	 * 
	 * @param dateOfbirth
	 * @param individualNumber
	 * @return
	 * @throws NumberFormatException
	 * @throws ArithmeticException
	 */
	private static Integer calculateControlCharacterValue(String dateOfbirth, String individualNumber) throws NumberFormatException, ArithmeticException {
		int combined = Integer.valueOf(dateOfbirth+individualNumber);
		BigDecimal result = new BigDecimal(combined).divide(new BigDecimal(31), MathContext.DECIMAL128) // Divide by 31
				.remainder(BigDecimal.ONE) // Get only remaining decimals
				.multiply(BigDecimal.valueOf(31)) // Multiply decimals by 31
				.setScale(0, RoundingMode.HALF_UP); // Round result up from half and set scale to zero
		return result.intValue();
	}

	/**
	 * Matching character to remainder value
	 * 
	 * @param controlCharacterValue
	 * @return
	 */
	private static String getControlCharacter(int controlCharacterValue) {
		switch (controlCharacterValue) {
		case 0:
			return "0";
		case 1:
			return "1";
		case 2:
			return "2";
		case 3:
			return "3";
		case 4:
			return "4";
		case 5:
			return "5";
		case 6:
			return "6";
		case 7:
			return "7";
		case 8:
			return "8";
		case 9:
			return "9";
		case 10:
			return "A";
		case 11:
			return "B";
		case 12:
			return "C";
		case 13:
			return "D";
		case 14:
			return "E";
		case 15:
			return "F";
		case 16:
			return "H";
		case 17:
			return "J";
		case 18:
			return "K";
		case 19:
			return "L";
		case 20:
			return "M";
		case 21:
			return "N";
		case 22:
			return "P";
		case 23:
			return "R";
		case 24:
			return "S";
		case 25:
			return "T";
		case 26:
			return "U";
		case 27:
			return "V";
		case 28:
			return "W";
		case 29:
			return "X";
		case 30:
			return "Y";
		}
		return null;
	}
}
