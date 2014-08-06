package com.mycompany.myproject;

import java.net.InetSocketAddress;
import java.net.URI;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;

import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerFileUpload;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.http.HttpVersion;
import org.vertx.java.core.net.NetSocket;

public class EaraHttpServerRequest implements HttpServerRequest {

	@Override
	public HttpServerRequest endHandler(Handler<Void> endHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpServerRequest dataHandler(Handler<Buffer> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpServerRequest pause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpServerRequest resume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpServerRequest exceptionHandler(Handler<Throwable> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpVersion version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String method() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String path() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpServerResponse response() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiMap headers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiMap params() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InetSocketAddress remoteAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InetSocketAddress localAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public X509Certificate[] peerCertificateChain()
			throws SSLPeerUnverifiedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI absoluteURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpServerRequest bodyHandler(Handler<Buffer> bodyHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NetSocket netSocket() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpServerRequest expectMultiPart(boolean expect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpServerRequest uploadHandler(
			Handler<HttpServerFileUpload> uploadHandler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiMap formAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
