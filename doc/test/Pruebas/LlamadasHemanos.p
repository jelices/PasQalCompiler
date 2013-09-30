program testB;
//Programa para comprobar llamadas entre hermanos y llamadas que siguen los vinculos de acceso

procedure padre();
var a:integer;
	procedure hijo1();
	begin
		a:=a+1;
		write("Hijo1");
		writeln();
	end;
	procedure hijo2();
	begin
		hijo1();
		write("Hijo2");
		writeln();
	end;
	procedure hijo3();
	begin
		a:=10;
		hijo2();
		write("Hijo3");
		writeln();
	end;
	procedure hijo4();
	begin
		hijo3();
		write("Hijo4");
		writeln();
	end;
begin
	hijo4();
	write(a);
	writeln();
end;

begin
	padre();
end.