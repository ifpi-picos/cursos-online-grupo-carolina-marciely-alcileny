 

       CREATE TABLE professor( 
        id serial PRIMARY KEY,  
        nome VARCHAR(100) NOT NULL,   
        email VARCHAR(250) UNIQUE NOT NULL  
       ); 

      CREATE TABLE curso( 
        id serial PRIMARY KEY,  
        nome VARCHAR(100) NOT NULL,   
        carga_horaria VARCHAR(100) NOT NULL,   
        status VARCHAR(50) NOT NULL   
       ); 

       CREATE TABLE aluno( 
        id serial PRIMARY KEY,  
        nome VARCHAR (100) NOT NULL,   
        email VARCHAR (250) UNIQUE NOT NULL   
       ); 

       CREATE TABLE curso_e_professor( 
        id serial PRIMARY KEY,   
        curso_id INT REFERENCES curso(id) ON DELETE CASCADE ON UPDATE CASCADE,  
        professor_id INT REFERENCES professor(id) ON DELETE CASCADE ON UPDATE CASCADE
       ); 

       CREATE TABLE curso_e_aluno( 
        id serial PRIMARY KEY,   
        curso_id INT REFERENCES curso(id) ON DELETE CASCADE ON UPDATE CASCADE,  
        aluno_id INT REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,
        status VARCHAR (100) NOT NULL  
       ); 

      CREATE TABLE usuario( 
        id serial PRIMARY KEY,   
        nome VARCHAR(100) NOT NULL,  
        email VARCHAR(100) UNIQUE NOT NULL,  
        senha VARCHAR(100) NOT NULL,  
        tipo VARCHAR(50) NOT NULL  
       ); 

       CREATE TABLE nota( 
        id serial PRIMARY KEY,  
        nota VARCHAR (50) NOT NULL,   
        aluno_id INT REFERENCES aluno(id) ON DELETE CASCADE ON UPDATE CASCADE,   
        curso_id INT REFERENCES curso(id) ON DELETE CASCADE ON UPDATE CASCADE   
       );