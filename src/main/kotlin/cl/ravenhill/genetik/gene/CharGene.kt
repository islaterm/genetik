package genetikt.gene

import cl.ravenhill.genetik.gene.IGene
import java.util.*

/**
 * Gene that represents a character.
 *
 * @author  [Ignacio Slater Muñoz](mailto:ignacio.slater@ug.uchile.cl)
 * @since   1.1
 * @version 1.2
 */
class CharGene : IGene<Char> {

  /** Character set used by this gene. */
  internal var alphabet: String

  /** DNA of this gene. */
  override var dna: Char
    private set

  /** A character is valid if it's contained in the gene alphabet. */
  var isValid: Boolean
    private set

//region Constructors
  /**
   * Creates a new gene from a random character.
   *
   * @param anAlphabet  Set of valid characters.
   */
  constructor(anAlphabet: String) {
    alphabet = anAlphabet
    dna = alphabet[Random().nextInt(alphabet.length)]
    isValid = true
  }

  /**
   * Creates a new gene from a character.
   *
   * @param char
   *    The character this gene represents.
   * @param anAlphabet
   *    Set of valid characters.
   */
  constructor(char: Char, anAlphabet: String) {
    alphabet = anAlphabet
    dna = char
    isValid = alphabet.asSequence().contains(char)
  }
//endregion

//region Utility functions
  /**
   * Returns a copy of this gene.
   */
  fun copy() = CharGene(dna, alphabet)

  override fun copyTo(other: IGene<*>) {
    other.copyFromCharGene(this)
  }

  override fun copyFromCharGene(other: CharGene) {
    alphabet = other.alphabet
    dna = other.dna
    isValid = other.isValid
  }

  /**
   * Checks if this gene is equal to another.
   *
   * @param other
   *    Gene to be checked.
   * @return
   *    `true` if the two genes are equal, `false` if not.
   */
  override fun equals(other: Any?): Boolean {
    return other is CharGene && this.dna == other.dna && this.alphabet == other.alphabet
  }

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int {
    var result = alphabet.hashCode()
    result = 31 * result + dna.hashCode()
    result = 31 * result + isValid.hashCode()
    return result
  }

  /**
   * Returns a string representation of this gene.
   */
  override fun toString() = dna.toString()

  fun replaceWith(value: Char) {
    dna = value
  }
//endregion
}