package org.dma.services.at.proxy;

import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.handler.Handler;

import com.sun.xml.ws.developer.WSBindingProvider;

import org.dma.java.security.JKSCertificate;
import org.dma.services.at.SOAPMessageHandler;

import pt.gov.portaldasfinancas.servicos.faturas.Faturas;
import pt.gov.portaldasfinancas.servicos.faturas.FaturasService;
import pt.gov.portaldasfinancas.servicos.faturas.RegisterInvoiceResponseType;
import pt.gov.portaldasfinancas.servicos.faturas.RegisterInvoiceType;
/**
 * PROXY para ligacao ao endpoint do webservice
 *
 * @author ricardo (AT)
 * @author marcolopespt@gmail.com
 *
 */
public class FaturasServiceHandler extends SOAPMessageHandler {

	public enum A10_ENDPOINTS {

		//fews = Facturas Envio WebService?
		PRODUCAO ("https://servicos.portaldasfinancas.gov.pt:400/fews/faturas"),
		TESTES ("https://servicos.portaldasfinancas.gov.pt:700/fews/faturas");

		public final String url;

		private A10_ENDPOINTS(String url) {
			this.url = url;
		}

		public boolean isSecure() {
			return url.startsWith("https");
		}

	}

	private final A10_ENDPOINTS endpoint;

	public FaturasServiceHandler(String username, String password,
			JKSCertificate saCertificate, JKSCertificate swCertificate, A10_ENDPOINTS endpoint) {
		super(username, password, saCertificate, swCertificate);
		this.endpoint = endpoint;
	}


	/**
	 * Recebe o Bean e realiza um pedido ao webservice.
	 * Instancia a conexao, coloca o Handler e invoca o webservice
	 */
	public RegisterInvoiceResponseType register(RegisterInvoiceType request) throws Exception {

		// cria um novo servico
		FaturasService service = new FaturasService();
		// wsdlLocation esta' definido no servico
		System.out.println(service.getWSDLDocumentLocation());
		Faturas soapService = service.getFaturasSOAP();

		// inicializa bindings
		Binding binding = initializeBindings((WSBindingProvider)soapService,
				endpoint.url, endpoint.isSecure());

		// adiciona handler
		List<Handler> chain = binding.getHandlerChain();
		chain.add(this);
		binding.setHandlerChain(chain);

		return soapService.registerInvoice(request);

	}


}