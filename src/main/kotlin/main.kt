fun main() {
    val publicKey       = Crypto.generateKeyPair().public
    val string          = String(Crypto.Converter.toBase64(publicKey.encoded))
    val secondPublicKey = Crypto.Builder.buildPublicKey(Crypto.Converter.fromBase64(string.toByteArray()))
    val secondString    = String(Crypto.Converter.toBase64(secondPublicKey.encoded))
    println("Original: $string")
    println("Built   : $secondString")
}