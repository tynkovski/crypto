fun main() {
    val keyPairAlice = Crypto.generateKeyPair()
    val keyPairBob = Crypto.generateKeyPair()

    println("Alice's public key: ${String(Crypto.Converter.toBase64(keyPairAlice.public.encoded))}")
    println("Bob's public key: ${String(Crypto.Converter.toBase64(keyPairBob.public.encoded))}")

    val bobMessage = Crypto.encrypt("Hello, Alice!".toByteArray(), keyPairAlice.public)
    println("Message to Alice: ${String(bobMessage)}")
    println("Alice reads: ${String(Crypto.decrypt(bobMessage, keyPairAlice.private))}")

    val aliceMessage = Crypto.encrypt("Hello, Bob!".toByteArray(), keyPairBob.public)
    println("Message to Bob: ${String(aliceMessage)}")
    println("Bob reads: ${String(Crypto.decrypt(aliceMessage, keyPairBob.private))}")
}