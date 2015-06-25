package br.ufc.sghc.controller;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.ufc.sghc.database.dao.AtividadeDAO;
import br.ufc.sghc.database.dao.AtividadeJPADAO;
import br.ufc.sghc.database.modelo.Aluno;
import br.ufc.sghc.database.modelo.Atividade;

@ViewScoped
@ManagedBean(name = "arquivoBean")
public class ArquivoBean implements Serializable {

	private static final long serialVersionUID = 1740828786004358102L;
	private String arquivo;
	private Atividade atividade;
	private List<Atividade> listAtividades;
	private byte[] bytesArquivo;

	public ArquivoBean() {
		this.atividade = new Atividade();
		this.listAtividades = new ArrayList();
	}

	public List<Atividade> getListAtividades() {
		AtividadeDAO dao = new AtividadeJPADAO();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Aluno aluno = (Aluno) session.getAttribute("usuario");
		listAtividades = dao.getAtividadesComArquivo(aluno);
		return listAtividades;
	}

	public void setListAtividades(List<Atividade> listAtividades) {
		this.listAtividades = listAtividades;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public byte[] getBytesArquivo() {
		return bytesArquivo;
	}

	public void setBytesArquivo(byte[] bytesArquivo) {
		this.bytesArquivo = bytesArquivo;
	}

	public List<String> getCaminho() {

		List<String> list = new ArrayList<String>();
		String caminhoRaiz = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/");
		Path path = Paths.get(caminhoRaiz + "/app/arquivos");
		System.out.println(path);
		try {
			DirectoryStream<Path> caminhos = Files.newDirectoryStream(path);
			for (Path caminho : caminhos) {
				list.add(caminho.getFileName().toString());
				System.out.println(caminho);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;

	}

	public String prepararLeitura() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String nomeArquivo = request.getParameter("path");

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.put("nomeArquivo", nomeArquivo);

		return "/app/ver-arquivos";

	}

	public String prepararUpload() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		Long id = Long.parseLong(request.getParameter("idAtividade"));
		AtividadeDAO dao = new AtividadeJPADAO();
		Atividade atividade = dao.find(id);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.put("idAtividade", id);
		flash.put("descricaoAtividade", atividade.getDescricao());
		flash.put("certificadoExistente", atividade.getCertificado() == null ? false : true);

		return "/app/certificado?faces-redirect=true";

	}

	public void doUpload(FileUploadEvent fileUploadEvent) {
		UploadedFile uploadedFile = fileUploadEvent.getFile();
		String fileNameUploaded = uploadedFile.getFileName();
		String infoAboutFile = "<br/> Arquivo recebido: <b>" + fileNameUploaded
				+ "</b><br/>"
				+ "Clique em confirmar para encerrar a adição do arquivo";
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null,
				new FacesMessage("Sucesso", infoAboutFile));
		this.bytesArquivo = uploadedFile.getContents();
	}

	public String adicionarArquivo() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String idAtividade = request.getParameter("idAtividade");
		System.out.println(bytesArquivo);
		if (bytesArquivo != null && idAtividade != null
				&& (!idAtividade.equals(""))) {
			this.atividade.setId(Long.parseLong(idAtividade));
			this.atividade.setCertificado(bytesArquivo);
			AtividadeDAO dao = new AtividadeJPADAO();
			dao.adicionarArquivo(atividade);
			return "/app/home?faces-context=true";
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage("Aviso",
					"Faça o upload do arquivo"));
			return "";
		}
	}

	public String visualizarArquivo() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		Long id = Long.parseLong(request.getParameter("idAtividade"));
		AtividadeDAO dao = new AtividadeJPADAO();
		this.atividade = dao.find(id);
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.responseComplete();
			ServletContext scontext = (ServletContext) facesContext
					.getExternalContext().getContext();

			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();

			response.setContentType("application/pdf");

			response.setHeader(
					"Content-disposition",
					"inline; filename=\"certificado_"
							+ atividade.getDescricao() + ".pdf\"");

			response.setContentLength(atividade.getCertificado().length);

			ServletOutputStream outputStream;
			outputStream = response.getOutputStream();

			outputStream.write(atividade.getCertificado(), 0,
					atividade.getCertificado().length);

			outputStream.flush();

			outputStream.close();
		} catch (IOException e) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage("Aviso",
					"Erro ao visualizar Certificado, verifique se o leitor de pdf do seu navegador está habilitado!"));
			e.printStackTrace();
		}

		return "";
	}

}
