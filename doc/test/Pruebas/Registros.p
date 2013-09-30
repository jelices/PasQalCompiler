program testB;
//Programa para comprobar registros
//Recordamos que no se pueden asignar Registros directamente, porque el enunciado lo aclara y que una variable por Referencia no se puede volver a pasar por Referencia

type
	tipopersona = record
		dni :integer; //no se pueden dar valores reales de dni
		casado: boolean;
		edad: integer;
	end;

var
	persona : tipoPersona; //correcto, no case sentitive


function getEdad (personaje: tipoPersona):integer;
begin
	getEdad:=personaje.edad;
end;

procedure setEdad(var personaje:tipoPersona; edad:integer);
begin
	personaje.edad:=edad;
end;

procedure cumplirAnos(var personaje:tipoPersona);
var
	persona: tipoPersona;
begin
	persona.edad:=personaje.edad;
	setEdad(persona, getEdad(persona)+1);
	personaje.edad:=persona.edad;
end;


procedure imprimirPersona(personaje:tipoPersona);
begin
	write("DNI: ");
	write(personaje.dni);
	if (personaje.casado=true) then
		write(" esta casado y tiene ");
	else
		write(" esta soltero y tiene ");
	write(personaje.edad);
	write(" anos.");
	writeln();
end;

begin
	persona.dni:=1;
	persona.casado :=true;
	persona.edad:=18;
	cumplirAnos(persona);
	imprimirPersona(persona);
end.