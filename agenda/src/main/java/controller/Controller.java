package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The contato. */
	JavaBeans contato = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			adicionarContato(request, response);

		} else if (action.equals("/select")) {
			listarContato(request, response);

		} else if (action.equals("/update")) {
			editarContato(request, response);

		} else if (action.equals("/delete")) {
			removerContato(request, response);

		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);

		} else {
			response.sendRedirect("index.html");

		}
		// teste de conexão
		// dao.testeConexao();
	}

	/**
	 * Contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.sendRedirect("agenda.jsp"); foi usado até a aula 11/3

		// Criando um objeto que irá receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();

		// teste de recebimento da lista
		/*
		 * for (int i = 0; i < lista.size(); i++) {
		 * System.out.println(lista.get(i).getIdcon());
		 * System.out.println(lista.get(i).getNome());
		 * System.out.println(lista.get(i).getFone());
		 * System.out.println(lista.get(i).getEmail()); }
		 */

		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	/**
	 * Adicionar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// adicionar contato
	protected void adicionarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// teste de recebimento dos dados do formulário
		/*
		 * System.out.println(request.getParameter("nome"));
		 * System.out.println(request.getParameter("fone"));
		 * System.out.println(request.getParameter("email"));
		 */

		// setar as variáveis JavaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// invocar o método inserirContato passando o objeto contato
		dao.inserirContato(contato);

		// Redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}

	/**
	 * Listar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Editar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recebimento do id do contato que será editado
		// String idcon = request.getParameter("idcon");
		// System.out.println(idcon); // para testar

		// Setar variável JavaBeans
		contato.setIdcon(request.getParameter("idcon"));

		// Executar o método selecionaraContato (DAO)
		dao.selecionarContato(contato);

		// teste de recebimento
		/*
		 * System.out.println(contato.getIdcon());
		 * System.out.println(contato.getNome()); System.out.println(contato.getFone());
		 * System.out.println(contato.getEmail());
		 */

		// Setar os atributos do formulário com o conteúdo JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());

		// Encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	/**
	 * Editar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// teste de recebimento
		/*
		 * System.out.println(request.getParameter("idcon"));
		 * System.out.println(request.getParameter("nome"));
		 * System.out.println(request.getParameter("fone"));
		 * System.out.println(request.getParameter("email"));
		 */

		// setar a variáveis JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// executar o método alterarContato
		dao.alterarContato(contato);

		// redirecionar para o documento agenda.jsp (atualizando as alterações)
		response.sendRedirect("main");
	}

	/**
	 * Remover contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// remover um contato
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Recebimento do id do contato a ser excluído (validador.js)
		//String idcon = request.getParameter("idcon");
		// System.out.println(idcon); // para testar

		// setar a variável idcon no JavaBeans
		contato.setIdcon(request.getParameter("idcon"));

		// executar o método deletarContato (DAO) passando o objeto contato
		dao.deletarContato(contato);

		// redirecionar para o documento agenda.jsp (atualizando as alterações)
		response.sendRedirect("main");

	}

	/**
	 * Gerar relatorio.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Gerar relatório em PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {

			// tipo de conteúdo
			response.setContentType("apllication/pdf");

			// nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");

			// criar o docuemnto
			PdfWriter.getInstance(documento, response.getOutputStream());

			// abrir documento -> conteúdo
			documento.open();
			documento.add(new Paragraph("Lista de contatos:"));
			documento.add(new Paragraph(" "));

			// criar uma tabela
			PdfPTable tabela = new PdfPTable(3);

			// cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);

			// popular a tabela com os contatos
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			documento.add(tabela);
			documento.close();

		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}

	}

}

