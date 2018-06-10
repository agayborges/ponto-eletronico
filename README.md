# ponto-eletronico
Servico REST de ponto eletronico

# AVISOS IMPORTANTES

- O SGBD usado foi o PostgreSQL 10.4
- O servidor usado para testar o deploy foi o apache-tomcat-8.5.31
- O desenvolvimento foi feito pela ide "Spring Tool Suite" versão sts-3.9.4.RELEASE
- O javadoc está na pasta apidocs
- O ponto-eletronico-0.0.1-SNAPSHOT.war para deploy esta na pasta war
- O application.propertie esta com banco configurado para rodar em localhost
- O arquivo ponto-eletronico.sql tem um versão inical do BD com um usuario já casdasrado
- O usuario inicial do ponto-eletronico.sql é : { pis = 83169153803, senha = 123 }
- O Basic AUTH está usuando usuários do BD para logar, logo o primeiro usuario tem que ser inserido direto no banco
- Os teste estão rodando com BD in memory

- Temos os seguintes controllers
	- UsuarioController (path="/usuarios")
		@GetMapping
		public Iterable<Usuario> retrieveAllUsuarios();
		
		@PostMapping
		public void saveUsuario(@RequestBody Usuario usuario);
	
		@DeleteMapping
		public void deleteUsaurio(@RequestBody Usuario usuario);
		service.deleteUsaurio(usuario);
		
		@GetMapping("/{pis}")
		public Optional<Usuario> retrieveUsuario(@PathVariable String pis);

	- JornadaController (path="/jornadas")
		@GetMapping
		List<Jornada> findJornada(JornadaRequestParam param);
		
		/*
			Metodo para realizar uma batida de ponto no horario atual e calcular as horas trabalhadas e intervalo
		*/
		@PostMapping("/usuarios/{pis}")
		Jornada realizarBatida(String pis);
		
		/*
			Metodo para realizar uma batida de ponto uma Hora especifica e calcular as horas trabalhadas e intervalo
		*/
		@PostMapping("/{id}")
		Jornada realizarBatidaExcepcional(String id, Hora hora);
		
		@DeleteMapping("/batidas/{batidaId}")
		Jornada removeBatida(long batidaId);
		
		/*
			Metodo para deletar uma batida de ponto e calcular as horas trabalhadas e intervalo
		*/
		@DeleteMapping("/{id}")
		void removeJornada(long id);
		
		@GetMapping("/{id}")
		Jornada obterJornada(long id);

