Feature: Linkedin Login Test

  Scenario Outline: Escenario de login fallido
  
    Given Se determina el navegador <browser>
    And El usuario navega a la pagina de linkedin
    When Se introduce el <usuario> y la <contrasena> no registrados
    And Se procede a autenticar
    Then Se debe mostrar mensaje de error

    Examples: 
      | usuario | contrasena | 	browser		 |
      |    "1111" |     "112233" |  "iexplorer"  |
      |    "1111" |     "112233" |  "chrome"     |



 
      


