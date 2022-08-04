fun main() {
    val kp = Crypto.generateKeyPair()

    val publicKey = kp.public
    val publicKeyString = String(Crypto.Converter.toBase64(publicKey.encoded))
    val secondPublicKey = Crypto.Builder.buildPublicKey(Crypto.Converter.fromBase64(publicKeyString.toByteArray()))
    val secondPublicKeyString = String(Crypto.Converter.toBase64(secondPublicKey.encoded))

    val privateKey = kp.private
    val privateKeyString = String(Crypto.Converter.toBase64(privateKey.encoded))
    val secondPrivateKey = Crypto.Builder.buildPrivateKey(Crypto.Converter.fromBase64(privateKeyString.toByteArray()))
    val secondPrivateKeyString = String(Crypto.Converter.toBase64(secondPrivateKey.encoded))

    println("Original: $publicKeyString")
    println("Built   : $secondPublicKeyString")

    println("Original: $privateKeyString")
    println("Built   : $secondPrivateKeyString")
}