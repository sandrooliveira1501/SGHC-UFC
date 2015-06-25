$(document).ready(
		function() {

			var button_adicionar_curso = document
					.getElementById("button_adicionar_curso");

			if (button_adicionar_curso != null)
				button_adicionar_curso.onclick = function() {
					dlg_adicionar_curso.show();
				};

			var button_adicionar_categoria = document
					.getElementById("button_adicionar_categoria");

			if (button_adicionar_categoria != null)
				button_adicionar_categoria.onclick = function() {
					dlg_adicionar_categoria.show();
				};
		});