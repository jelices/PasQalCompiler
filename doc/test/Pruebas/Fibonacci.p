program testB;
//Programa para comprobar llamadas recursivas

function fibonacci(a:integer):integer;
begin
	if (a<2) then
		fibonacci:=a;
	else
		fibonacci:=fibonacci(a-1)+fibonacci(a-2);
end;

begin
	write(fibonacci(10)); //Debe dar 55
end.