package br.ufc.sghc.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.ufc.sghc.modelo.Categoria;
import br.ufc.sghc.util.JPAUtil;

@FacesConverter(value="categoriaConverter",forClass = Categoria.class)
public class CategoriaConverter implements Converter {

        public String getAsString(FacesContext context, UIComponent component,
                        Object object) {
                //System.err.println("Utilizando Converter\n\n" + object);
                Categoria categoria = (Categoria) object;
                if (categoria == null)
                        return null;
                return String.valueOf(categoria.getId());
        }

        public Object getAsObject(FacesContext context, UIComponent component,
                        String string) {
                //System.err.println("Utilizando Convertern\n\n" + string);
                if (string == null || string.isEmpty())
                        return null;
                Long idCategoria = Long.valueOf(string);
                Categoria categoria = JPAUtil.createEntityManager().find(Categoria.class, idCategoria);
                return categoria;
        }
        
}
