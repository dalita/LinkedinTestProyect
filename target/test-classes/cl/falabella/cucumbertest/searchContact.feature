Feature: Linkedin busqueda de contacto en mi red

   Scenario Outline: login y busqueda de contacto
  
    Given Se ejecutaran los escenarios usando el navegador <browser>
    And El usuario navega a la pagina de inicio
    And Se introduce el <usuario> y la <contrasena>
    When Procede a autenticar
    And Se busca un contacto y se visualiza perfil
    Then Se debe visualizar perfil del contacto

    Examples: 
      | usuario            		 	| contrasena     			|  	browser		 |
      | "dalifaceb@gmail.com" 	| "linkedintest88" 		| "iexplorer"  	 |
      | "dalifaceb@gmail.com" 	| "linkedintest88" 		| "chrome"  |
      


