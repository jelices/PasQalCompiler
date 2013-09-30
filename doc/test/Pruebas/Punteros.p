program testB;
//Programa para comprobar punteros
//Lo unico que no acepta es pasar un puntero por referencia, ya que seria una doble indireccion

var numero:integer;
    puntero:^integer;
    puntero2:^integer;

procedure doblarNumero(puntero: ^integer);
begin
	puntero^:=puntero^+puntero^;
end;

procedure imprimirDireccion( puntero: ^integer);
begin
	write(puntero);
	writeln();
end;

function copiarPuntero(puntero: ^integer): ^integer;
begin
	copiarPuntero:=puntero;
end;

begin	
	numero:=5;
	puntero:=@numero;
	puntero2:=copiarPuntero(puntero);
	puntero^:=puntero2^+1;
	write(numero); //Tiene que imprimir 6
	writeln();
	doblarNumero(puntero);
	write(numero); //Tiene que imprimir 12
	writeln();
	imprimirDireccion(puntero2);//Imprime -6 (la direccion de numero)
	write(@numero);//Imprime -6 (la direccion de numero)
	writeln();
end.