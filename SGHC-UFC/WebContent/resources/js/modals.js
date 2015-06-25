$(document).ready(function() {

	var alterar_senha = document.getElementById("alterar_senha");
		
	if(alterar_senha + null)
		alterar_senha.onclick = function(){
			dlg_alterar_senha.show();
			
		};
	
	
	var adicionar_atividade = document.getElementById("button_adicionar_atividade");
	
	if(adicionar_atividade != null)
		adicionar_atividade.onclick = function(){
			dlg_adicionar_atividade.show();
			
		};
	
	
});