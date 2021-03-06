package br.ufc.sghc.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.PersistenceException;

import br.ufc.sghc.database.dao.AlunoDAO;
import br.ufc.sghc.database.dao.AlunoJPADAO;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator{

	@Override
	public void validate(FacesContext  context, UIComponent component, Object value)
			throws ValidatorException {

		try{
			AlunoDAO alunoDao = new AlunoJPADAO();
			
			if(alunoDao.findByEmail(value.toString()) != null){
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
		                "E-mail já cadastrado!", null));
			}
			
		}catch (PersistenceException e) {
			// Captura a exceção de nenhum usuário encontrado
		}
	}
	
}
