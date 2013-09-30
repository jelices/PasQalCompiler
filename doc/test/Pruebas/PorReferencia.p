program testB;
//Programa para comprobar llamadas por referencia
//Una variable por referencia no se puede pasar a otra funcion por referencia
var a:integer;

procedure incrementar (var valor: integer);
begin
	valor:=valor+1;
end;

procedure sumar(var valor:integer; cantidad:integer);
var i:integer;
    numero:integer;
begin
	numero:=valor;
	i:=cantidad;
	repeat
		incrementar(numero);
		i:=i-1;
	until (i=0);
	valor:=numero;
end;



begin
	a:=5;
	sumar(a,5);
	write(a);
end.
	
