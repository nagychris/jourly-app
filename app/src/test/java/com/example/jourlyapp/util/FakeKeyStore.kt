/*
 * Copyright 2020 Appmattus Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jourlyapp.util

import java.io.InputStream
import java.io.OutputStream
import java.security.Key
import java.security.KeyStore
import java.security.KeyStoreSpi
import java.security.PrivateKey
import java.security.cert.Certificate
import java.util.Collections
import java.util.Date
import java.util.Enumeration
import javax.crypto.SecretKey

// See https://proandroiddev.com/testing-jetpack-security-with-robolectric-9f9cf2aa4f61
internal class FakeKeyStore : KeyStoreSpi() {
    companion object {
        private val keys = mutableMapOf<String, Key>()
        private val certs = mutableMapOf<String, Certificate>()
    }

    override fun engineIsKeyEntry(alias: String?): Boolean {
        alias ?: throw NullPointerException("alias == null")

        return keys.containsKey(alias)
    }

    override fun engineIsCertificateEntry(alias: String?): Boolean {
        alias ?: throw NullPointerException("alias == null")

        return certs.containsKey(alias)
    }

    override fun engineGetCertificate(alias: String?): Certificate {
        alias ?: throw NullPointerException("alias == null")

        return certs.getValue(alias)
    }

    override fun engineGetCreationDate(alias: String?): Date {
        alias ?: throw NullPointerException("alias == null")

        return Date()
    }

    override fun engineDeleteEntry(alias: String?) {
        alias ?: throw NullPointerException("alias == null")

        keys.remove(alias)
        certs.remove(alias)
    }

    override fun engineSetKeyEntry(
        alias: String?, key: Key?, password: CharArray?, chain: Array<out Certificate>?
    ) {
        alias ?: throw NullPointerException("alias == null")
        key ?: throw NullPointerException("key == null")

        keys[alias] = key
    }

    override fun engineGetEntry(
        alias: String?, protParam: KeyStore.ProtectionParameter?
    ): KeyStore.Entry {
        alias ?: throw NullPointerException("alias == null")

        val key = keys[alias]
        if (key != null) {
            return when (key) {
                is SecretKey -> KeyStore.SecretKeyEntry(key)
                is PrivateKey -> KeyStore.PrivateKeyEntry(key, null)
                else -> throw UnsupportedOperationException("Unsupported key type: $key")
            }
        }
        val cert = certs[alias]
        if (cert != null) {
            return KeyStore.TrustedCertificateEntry(cert)
        }
        throw UnsupportedOperationException("No alias found in keys or certs, alias=$alias")
    }

    override fun engineSetKeyEntry(
        alias: String?, key: ByteArray?, chain: Array<out Certificate>?
    ) {
        throw UnsupportedOperationException(
            "Operation not supported because key encoding is unknown"
        )
    }

    override fun engineStore(stream: OutputStream?, password: CharArray?) {
        throw UnsupportedOperationException("Can not serialize AndroidKeyStore to OutputStream")
    }

    override fun engineSize(): Int {
        val uniqueAlias = mutableSetOf<String>().apply {
            addAll(keys.keys)
            addAll(certs.keys)
        }
        return uniqueAlias.size
    }

    override fun engineAliases(): Enumeration<String> {
        val uniqueAlias = mutableSetOf<String>().apply {
            addAll(keys.keys)
            addAll(certs.keys)
        }
        return Collections.enumeration(uniqueAlias)
    }

    override fun engineContainsAlias(alias: String?): Boolean {
        alias ?: throw NullPointerException("alias == null")

        return keys.containsKey(alias) || certs.containsKey(alias)
    }

    override fun engineLoad(stream: InputStream?, password: CharArray?) {
        if (stream != null) {
            throw IllegalArgumentException("InputStream not supported")
        }
        if (password != null) {
            throw IllegalArgumentException("password not supported")
        }

        // Do nothing in this fake key store.
    }

    override fun engineGetCertificateChain(alias: String?): Array<Certificate> {
        alias ?: throw NullPointerException("alias == null")

        val cert = certs[alias] ?: return arrayOf()
        return arrayOf(cert)
    }

    override fun engineSetCertificateEntry(alias: String?, cert: Certificate?) {
        alias ?: throw NullPointerException("alias == null")
        cert ?: throw NullPointerException("cert == null")

        certs[alias] = cert
    }

    override fun engineGetCertificateAlias(cert: Certificate?): String? {
        cert ?: throw NullPointerException("cert == null")

        for (entry in certs.entries) {
            if (entry.value == cert) {
                return entry.key
            }
        }
        return null
    }

    override fun engineGetKey(alias: String?, password: CharArray?): Key? {
        alias ?: throw NullPointerException("alias == null")

        return keys[alias]
    }
}