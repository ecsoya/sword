package com.github.ecsoya.sword.common.utils.uuid;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.github.ecsoya.sword.common.exception.UtilException;

/**
 * The Class UUID.
 */
public final class UUID implements java.io.Serializable, Comparable<UUID> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1185015143654744140L;

	/**
	 * The Class Holder.
	 */
	private static class Holder {

		/** The Constant numberGenerator. */
		static final SecureRandom numberGenerator = getSecureRandom();
	}

	/** The most sig bits. */
	private final long mostSigBits;

	/** The least sig bits. */
	private final long leastSigBits;

	/**
	 * Instantiates a new uuid.
	 *
	 * @param data the data
	 */
	private UUID(byte[] data) {
		long msb = 0;
		long lsb = 0;
		assert data.length == 16 : "data must be 16 bytes in length";
		for (int i = 0; i < 8; i++) {
			msb = (msb << 8) | (data[i] & 0xff);
		}
		for (int i = 8; i < 16; i++) {
			lsb = (lsb << 8) | (data[i] & 0xff);
		}
		this.mostSigBits = msb;
		this.leastSigBits = lsb;
	}

	/**
	 * Instantiates a new uuid.
	 *
	 * @param mostSigBits  此UUID的最高64有效位.
	 * @param leastSigBits 此UUID的最低64有效位.
	 */
	public UUID(long mostSigBits, long leastSigBits) {
		this.mostSigBits = mostSigBits;
		this.leastSigBits = leastSigBits;
	}

	/**
	 * Fast UUID.
	 *
	 * @return the uuid
	 */
	public static UUID fastUUID() {
		return randomUUID(false);
	}

	/**
	 * Random UUID.
	 *
	 * @return the uuid
	 */
	public static UUID randomUUID() {
		return randomUUID(true);
	}

	/**
	 * Random UUID.
	 *
	 * @param isSecure the is secure
	 * @return the uuid
	 */
	public static UUID randomUUID(boolean isSecure) {
		final Random ng = isSecure ? Holder.numberGenerator : getRandom();

		final byte[] randomBytes = new byte[16];
		ng.nextBytes(randomBytes);
		randomBytes[6] &= 0x0f; /* clear version */
		randomBytes[6] |= 0x40; /* set to version 4 */
		randomBytes[8] &= 0x3f; /* clear variant */
		randomBytes[8] |= 0x80; /* set to IETF variant */
		return new UUID(randomBytes);
	}

	/**
	 * Name UUID from bytes.
	 *
	 * @param name the name
	 * @return the uuid
	 */
	public static UUID nameUUIDFromBytes(byte[] name) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (final NoSuchAlgorithmException nsae) {
			throw new InternalError("MD5 not supported");
		}
		final byte[] md5Bytes = md.digest(name);
		md5Bytes[6] &= 0x0f; /* clear version */
		md5Bytes[6] |= 0x30; /* set to version 3 */
		md5Bytes[8] &= 0x3f; /* clear variant */
		md5Bytes[8] |= 0x80; /* set to IETF variant */
		return new UUID(md5Bytes);
	}

	/**
	 * From string.
	 *
	 * @param name the name
	 * @return the uuid
	 */
	public static UUID fromString(String name) {
		final String[] components = name.split("-");
		if (components.length != 5) {
			throw new IllegalArgumentException("Invalid UUID string: " + name);
		}
		for (int i = 0; i < 5; i++) {
			components[i] = "0x" + components[i];
		}

		long mostSigBits = Long.decode(components[0]).longValue();
		mostSigBits <<= 16;
		mostSigBits |= Long.decode(components[1]).longValue();
		mostSigBits <<= 16;
		mostSigBits |= Long.decode(components[2]).longValue();

		long leastSigBits = Long.decode(components[3]).longValue();
		leastSigBits <<= 48;
		leastSigBits |= Long.decode(components[4]).longValue();

		return new UUID(mostSigBits, leastSigBits);
	}

	/**
	 * Gets the least significant bits.
	 *
	 * @return the least significant bits
	 */
	public long getLeastSignificantBits() {
		return leastSigBits;
	}

	/**
	 * Gets the most significant bits.
	 *
	 * @return the most significant bits
	 */
	public long getMostSignificantBits() {
		return mostSigBits;
	}

	/**
	 * Version.
	 *
	 * @return the int
	 */
	public int version() {
		// Version is bits masked by 0x000000000000F000 in MS long
		return (int) ((mostSigBits >> 12) & 0x0f);
	}

	/**
	 * Variant.
	 *
	 * @return the int
	 */
	public int variant() {
		// This field is composed of a varying number of bits.
		// 0 - - Reserved for NCS backward compatibility
		// 1 0 - The IETF aka Leach-Salz variant (used by this class)
		// 1 1 0 Reserved, Microsoft backward compatibility
		// 1 1 1 Reserved for future definition.
		return (int) ((leastSigBits >>> (64 - (leastSigBits >>> 62))) & (leastSigBits >> 63));
	}

	/**
	 * Timestamp.
	 *
	 * @return the long
	 * @throws UnsupportedOperationException the unsupported operation exception
	 */
	public long timestamp() throws UnsupportedOperationException {
		checkTimeBase();
		return (mostSigBits & 0x0FFFL) << 48//
				| ((mostSigBits >> 16) & 0x0FFFFL) << 32//
				| mostSigBits >>> 32;
	}

	/**
	 * Clock sequence.
	 *
	 * @return the int
	 * @throws UnsupportedOperationException the unsupported operation exception
	 */
	public int clockSequence() throws UnsupportedOperationException {
		checkTimeBase();
		return (int) ((leastSigBits & 0x3FFF000000000000L) >>> 48);
	}

	/**
	 * Node.
	 *
	 * @return the long
	 * @throws UnsupportedOperationException the unsupported operation exception
	 */
	public long node() throws UnsupportedOperationException {
		checkTimeBase();
		return leastSigBits & 0x0000FFFFFFFFFFFFL;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return toString(false);
	}

	/**
	 * To string.
	 *
	 * @param isSimple the is simple
	 * @return the string
	 */
	public String toString(boolean isSimple) {
		final StringBuilder builder = new StringBuilder(isSimple ? 32 : 36);
		// time_low
		builder.append(digits(mostSigBits >> 32, 8));
		if (false == isSimple) {
			builder.append('-');
		}
		// time_mid
		builder.append(digits(mostSigBits >> 16, 4));
		if (false == isSimple) {
			builder.append('-');
		}
		// time_high_and_version
		builder.append(digits(mostSigBits, 4));
		if (false == isSimple) {
			builder.append('-');
		}
		// variant_and_sequence
		builder.append(digits(leastSigBits >> 48, 4));
		if (false == isSimple) {
			builder.append('-');
		}
		// node
		builder.append(digits(leastSigBits, 12));

		return builder.toString();
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final long hilo = mostSigBits ^ leastSigBits;
		return ((int) (hilo >> 32)) ^ (int) hilo;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if ((null == obj) || (obj.getClass() != UUID.class)) {
			return false;
		}
		final UUID id = (UUID) obj;
		return (mostSigBits == id.mostSigBits && leastSigBits == id.leastSigBits);
	}

	// Comparison Operations

	/**
	 * Compare to.
	 *
	 * @param val the val
	 * @return the int
	 */
	@Override
	public int compareTo(UUID val) {
		// The ordering is intentionally set up so that the UUIDs
		// can simply be numerically compared as two numbers
		return (this.mostSigBits < val.mostSigBits ? -1 : //
				(this.mostSigBits > val.mostSigBits ? 1 : //
						(this.leastSigBits < val.leastSigBits ? -1 : //
								(this.leastSigBits > val.leastSigBits ? 1 : //
										0))));
	}

	// -------------------------------------------------------------------------------------------------------------------
	// Private method start
	/**
	 * Digits.
	 *
	 * @param val    the val
	 * @param digits the digits
	 * @return the string
	 */
	private static String digits(long val, int digits) {
		final long hi = 1L << (digits * 4);
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
	}

	/**
	 * Check time base.
	 */
	private void checkTimeBase() {
		if (version() != 1) {
			throw new UnsupportedOperationException("Not a time-based UUID");
		}
	}

	/**
	 * Gets the secure random.
	 *
	 * @return the secure random
	 */
	public static SecureRandom getSecureRandom() {
		try {
			return SecureRandom.getInstance("SHA1PRNG");
		} catch (final NoSuchAlgorithmException e) {
			throw new UtilException(e);
		}
	}

	/**
	 * Gets the random.
	 *
	 * @return the random
	 */
	public static ThreadLocalRandom getRandom() {
		return ThreadLocalRandom.current();
	}
}
