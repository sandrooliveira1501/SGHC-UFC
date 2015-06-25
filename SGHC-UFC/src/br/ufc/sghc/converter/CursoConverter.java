package br.ufc.sghc.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.ufc.sghc.modelo.Categoria;
import br.ufc.sghc.modelo.Curso;
import br.ufc.sghc.util.JPAUtil;

@FacesConverter(value="cursoConverter",forClass = Curso.class)
public class CursoConverter implements Converter {

        public String getAsString(FacesContext context, UIComponent component,
                        Object object) {
                //System.err.println("Utilizando Converter\n\n" + object);
                Curso curso = (Curso) object;
                if (curso == null)
                        return null;
                return String.valueOf(curso.getId());
        }

        public Object getAsObject(FacesContext context, UIComponent component,
                        String string) {
                //System.err.println("Utilizando Convertern\n\n" + string);
                if (string == null || string.isEmpty())
                        return null;
                Long idCurso = Long.valueOf(string);
                Curso curso = JPAUtil.createEntityManager().find(Curso.class, idCurso);
                return curso;
        }
        
}
