program testB;
//Programa para comprobar registros
//Recordamos que no se pueden asignar Conjuntos directamente, porque el enunciado lo especifica y que una variable por Referencia no se puede volver a pasar por Referencia

type
	unoAdiez = set of 1..10;
	tresAcinco = set of 3..5;
var
	conjunto1, conjunto2: unoadiez;
	conjunto3, conjunto4: tresacinco;

procedure sumarConjuntos(var conjunto1: unoAdiez; conjunto2: unoAdiez);
begin
	conjunto1:=conjunto1+conjunto2;
end;

procedure imprimirConjunto(conjunto: unoAdiez);
var
	i:integer;
begin
	for (i:=1 to 10) do
	begin
		write(i);
		if (i in conjunto) then
			write(" esta en el conjunto");
		else
		begin
			write(" no esta en el conjunto");
		end;
		writeln();
	end;
end;

procedure anadirElementos1y10(var conjunto1: unoAdiez);
var
	conjunto2: unoAdiez;
begin
	conjunto2:=[1..1];
	conjunto1:=conjunto1+conjunto2;
	conjunto2:=[10..10];
	conjunto1:=conjunto1+conjunto2;
end;

begin
	conjunto1:=[3..5];
	sumarConjuntos(conjunto1, conjunto2);
	imprimirConjunto(conjunto1);
	writeln();
	anadirElementos1y10(conjunto1);
	imprimirConjunto(conjunto1);
end.