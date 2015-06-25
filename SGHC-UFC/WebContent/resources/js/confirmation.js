$(document)
		.ready(
				function() {

					var atualizar_perfil = document
							.getElementById("atualizar_perfil");

					if (atualizar_perfil != null) {
						atualizar_perfil.onsubmit = function() {

							var result = confirm("Deseja realmente alterar seus dados?");
							if (result) {
								return true;
							} else {
								return false;
							}

						};
					}
					var form_atualizar_perfil = document
							.getElementById("form_alterar_senha");

					if (form_atualizar_perfil != null) {
						form_atualizar_perfil.onsubmit = function() {

							var result = confirm("Deseja realmente alterar sua senha?");
							if (result) {
								return true;
							} else {
								return false;
							}

						};
					}

					var form_editar_atividade = document
							.getElementById("form_editar_atividade");
					if (form_editar_atividade != null) {

						form_editar_atividade.onsubmit = function() {

							var result = confirm("Deseja realmente alterar esta atividade?");
							if (result) {
								return true;
							} else {
								return false;
							}

						};
					}

					/*var confirm_remover = function(evt) {

						var result = confirm("Deseja realmente excluir esta atividade?");
						if(result == false){
							    evt.preventDefault();
						}
						
						return result;
					};

					var btns_remover = document
							.getElementsByClassName("btn_remover");
					if (btns_remover != null) {
						for (i = 0; i < btns_remover.length; i++) {

							var remover = btns_remover[i];

							remover.addEventListener("click", confirm_remover);

						}
					}*/
				});