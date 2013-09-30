program ejemploPasQal;
const
	cierto = true;
	falso = false;
type
	usuario = record
		dni : integer;
		edad : integer;
		casado : boolean;
	end;
	mayorEdad = set of 18..35;
var
	usuario1: usuario; // variable global
	rangoEdad : mayorEdad; // variable global

{Declaración de subprogramas }
	procedure imprimeUsuario(dni, edad:integer; casado:boolean);
		var debug : boolean; // variable local
		procedure imprimeCasado(valor:boolean);
		begin
			if (debug=true) // referencia no local
				if (valor=true) then // referencia local
					write(“casado”);
				else
					write(“soltero”);
		end;
	begin
	debug := true; // referencia local
	write(dni); write(“-”); // referencia local (parámetro)
	write(edad); write(“-”); // referencia local (parámetro)
	imprimeCasado(casado); // referencia local (parámetro)
	end;

	function mayorDeEdad (e : integer): boolean;
	var punt: ^integer; // variable local
	begin
	punt := @e; // referencia local
	if (punt^ IN rangoEdad) then
		mayorDeEdad := true; // referencia global
	else
		mayorDeEdad := false; // referencia global
	end;

begin
// Comienzo del programa principal
	usuario1.dni:=1234567; // referencia global
	usuario1.edad:=15; // referencia global
	usuario1.casado:=false; // referencia global
	if (mayorDeEdad(usuario1.edad))then
	begin
		write(“usuario:”);
		imprimeUsuario(usuario1.dni, usuario1.edad, usuario1.casado);
		write(“ es mayor de edad”);
	end;
// Fin del programa
end;