package com.cs.http.http

import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.*

object HttpUtils {


    fun getTrusClient(certificate: InputStream): OkHttpClient {
        val trustManager: X509TrustManager
        val sslSocketFactory: SSLSocketFactory
        try {
            trustManager = trustManagerForCertificates(certificate)
            val sslContext = SSLContext.getInstance("TLS")
            //使用构建出的trustManger初始化SSLContext对象
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            //获得sslSocketFactory对象
            sslSocketFactory = sslContext.socketFactory
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        }

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustManager)
            .build()
    }


    /**
     * 获去信任自签证书的trustManager
     *
     * @param inputStream 自签证书输入流
     * @return 信任自签证书的trustManager
     * @throws GeneralSecurityException
     */
    @Throws(GeneralSecurityException::class)
    private fun trustManagerForCertificates(inputStream: InputStream): X509TrustManager {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        //通过证书工厂得到自签证书对象集合
        val certificates = certificateFactory.generateCertificates(inputStream)
        require(!certificates.isEmpty()) { "expected non-empty set of trusted certificates" }
        //为证书设置一个keyStore
        val password = "password".toCharArray() // Any password will work.
        val keyStore = newEmptyKeyStore(password)
        //将证书放入keystore中
        for ((index, certificate) in certificates.withIndex()) {
            val certificateAlias = index.toString()
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }
        // Use it to build an X509 trust manager.
        //使用包含自签证书信息的keyStore去构建一个X509TrustManager
        val keyManagerFactory = KeyManagerFactory.getInstance(
            KeyManagerFactory.getDefaultAlgorithm()
        )
        keyManagerFactory.init(keyStore, password)
        val trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        )
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            "Unexpected default trust managers:" + Arrays.toString(
                trustManagers
            )
        }
        return trustManagers[0] as X509TrustManager
    }

    @Throws(GeneralSecurityException::class)
    private fun newEmptyKeyStore(password: CharArray): KeyStore {
        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            val inputStream: InputStream? =
                null // By convention, 'null' creates an empty key store.
            keyStore.load(null, password)
            return keyStore
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }
}